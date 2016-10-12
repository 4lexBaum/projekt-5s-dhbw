package model.stateMachine;

import com.github.oxo42.stateless4j.StateMachine;
import com.github.oxo42.stateless4j.StateMachineConfig;

import db.DatabaseManager;
import model.dataModels.MachineData;

/**
 * Class ProductionStateMachine.
 * Virtual representation of the production line.
 * @author Daniel
 *
 */
@SuppressWarnings("all")
public class ProductionStateMachine {
	private StateMachine<State, Trigger> productionLine;
	private int counter = 0;
	
	private static ProductionStateMachine stateMachine;
	
	/**
	 * Constructor ProductionStateMachine.
	 * Singleton-Pattern! => private constructor.
	 * Configures states and transitions of the state machine.
	 */
	private ProductionStateMachine() {
		StateMachineConfig<State, Trigger> machineConfig = new StateMachineConfig<>();
		
		//configure states and transitions
		machineConfig.configure(State.EnterL1)
			.permit(Trigger.L1Open, State.ExitL1)
			.permitReentry(Trigger.L1Close);
		
		machineConfig.configure(State.ExitL1)
			.permit(Trigger.L2Close, State.EnterL2);
		
		machineConfig.configure(State.EnterL2)
			.permit(Trigger.L2Open, State.ExitL2);
	
		machineConfig.configure(State.ExitL2)
			.permit(Trigger.L3Close, State.EnterL3);
		
		machineConfig.configure(State.EnterL3)
			.permit(Trigger.MillingStart, State.Milling);
		
		machineConfig.configure(State.Milling)
			.permit(Trigger.MillingStop, State.MillingCompleted);
		
		machineConfig.configure(State.MillingCompleted)
			.permit(Trigger.L3Open, State.ExitL3);
		
		machineConfig.configure(State.ExitL3)
			.permit(Trigger.L4Close, State.EnterL4);
		
		machineConfig.configure(State.EnterL4)
			.permit(Trigger.DrillingStart, State.Drilling);
		
		machineConfig.configure(State.Drilling)
			.permit(Trigger.DrillingStop, State.DrillingCompleted);
		
		machineConfig.configure(State.DrillingCompleted)
			.permit(Trigger.L4Open, State.ExitL4);
		
		machineConfig.configure(State.ExitL4)
			.permit(Trigger.L5Close, State.EnterL5);
		
		machineConfig.configure(State.EnterL5)
			.permit(Trigger.L5Open, State.ExitL5);
		
		//restart production, fired when L1 is closed
		machineConfig.configure(State.ExitL5)
			.permit(Trigger.L1Close, State.EnterL1);
		
		//create state machine
		productionLine = new StateMachine<>(State.EnterL1, machineConfig);
	}
	
	/**
	 * getStateMachine method.
	 * Is used to obtain an instance of the ProductionStateMachine.
	 * @return
	 */
	public static ProductionStateMachine getStateMachine() {
		if(stateMachine == null) {
			stateMachine = new ProductionStateMachine();
		}
		return stateMachine;
	}
	
	/**
	 * Trigger method.
	 * Receives the production data and
	 * determins the next state.
	 * @param data
	 */
	public void trigger(MachineData event) {
		switch(event.getItemName()) {
		case "L1":
			if(event.getValue().equals("false") && counter > 0 ){
				productionLine.fire(Trigger.L1Close);
			}else if(event.getValue().equals("false")){
				counter++;
				productionLine.fire(Trigger.L1Close);
			}else
				productionLine.fire(Trigger.L1Open);
			break;
		case "L2":
			if(event.getValue().equals("false")) 
				productionLine.fire(Trigger.L2Close);
			else
				productionLine.fire(Trigger.L2Open);
			break;
		case "L3":
			if(event.getValue().equals("false"))
				productionLine.fire(Trigger.L3Close);
			else
				productionLine.fire(Trigger.L3Open);
			break;
		case "L4":
			if(event.getValue().equals("false"))
				productionLine.fire(Trigger.L4Close);
			else
				productionLine.fire(Trigger.L4Open);
			break;
		case "L5":
			if(event.getValue().equals("false"))
				productionLine.fire(Trigger.L5Close);
			else
				productionLine.fire(Trigger.L5Open);
			break;
		case "MILLING":
			if(event.getValue().equals("true"))
				productionLine.fire(Trigger.MillingStart);
			else
				productionLine.fire(Trigger.MillingStop);
			break;
		case "DRILLING":
			if(event.getValue().equals("true"))
				productionLine.fire(Trigger.DrillingStart);
			else
				productionLine.fire(Trigger.DrillingStop);
			break;
		}
		
		System.out.println(productionLine.getState());
	}
	

	
	private void adjustMillingSpeed() {}
	
	private void adjustDrillingSpeed() {}
	
	private void startTimer() {}
	
	private void stopTimer() {}
	
	private void trackTemperature() {}
	
	private void startGlobalTimer() {}
	
	private void stopGlobalTimer() {}
	
	private void saveSpectralAnalysis() {}
	
	/**
	 * Enum State.
	 * Possible states for the state machine.
	 * @author Daniel
	 *
	 */
	private enum State {
		EnterL1, ExitL1, EnterL2, ExitL2, EnterL3, ExitL3,
		EnterL4, ExitL4, EnterL5, ExitL5, Milling, Drilling,
		MillingCompleted, DrillingCompleted
	}
	
	/**
	 * Enum Trigger.
	 * Triggers for transitions.
	 * @author Daniel
	 *
	 */
	private enum Trigger {
		L1Close, L1Open, L2Open, L2Close, L3Open, L3Close,
		L4Open, L4Close, L5Open, L5Close,
		MillingStart, DrillingStart, MillingStop, DrillingStop
	}
}