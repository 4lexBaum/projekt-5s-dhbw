package statemachine;

import com.github.oxo42.stateless4j.StateMachine;
import com.github.oxo42.stateless4j.StateMachineConfig;

/**
 * Class ProductionStateMachine.
 * Virtual representation of the production line.
 * @author Daniel
 *
 */
public class ProductionStateMachine {
	private static StateMachine<State, Trigger> productionLine;
	
	/**
	 * Main method.
	 * @param args
	 */
	public static void main(String[] args) {
		new ProductionStateMachine();
		System.out.println(productionLine.getState());
		productionLine.fire(Trigger.MillingCompleted);
		System.out.println(productionLine.getState());
	}
	
	/**
	 * Constructor ProductionStateMachine.
	 * Configures states and transitions of the state machine.
	 */
	public ProductionStateMachine() {
		StateMachineConfig<State, Trigger> machineConfig = new StateMachineConfig<>();
		
		//configure states and transitions
		machineConfig.configure(State.Milling)
			.onEntry(this::onStartMilling)
			.permit(Trigger.MillingCompleted, State.Drilling);
		
		machineConfig.configure(State.Drilling)
			.onEntry(this::onStartDrilling)
			.permit(Trigger.DrillingCompleted, State.Testing);
		
		machineConfig.configure(State.Testing)
			.onEntry(this::onStartTesting);
		
		//create state machine
		productionLine = new StateMachine<>(State.Milling, machineConfig);
	}
	
	/**
	 * Trigger method.
	 * Receives the production data and
	 * determins the next state.
	 * @param data
	 */
	public void trigger(String event) {
		//TODO: implement method
	}
	
	/**
	 * onStartMilling.
	 * Entry method for state milling.
	 */
	private void onStartMilling() {
		System.out.println("Started milling");
	}
	
	/**
	 * onStartDrilling.
	 * Entry method for state drilling.
	 */
	private void onStartDrilling() {
		System.out.println("Started drilling");
	}
	
	/**
	 * onStartTesting.
	 * Entry method for state testing.
	 */
	private void onStartTesting() {
		System.out.println("Started testing");
	}
	
	/**
	 * Enum State.
	 * Possible states for the state machine.
	 * @author Daniel
	 *
	 */
	private enum State {
		Milling, Drilling, Testing
	}
	
	/**
	 * Enum Trigger.
	 * Triggers for transitions.
	 * @author Daniel
	 *
	 */
	private enum Trigger {
		MillingCompleted, DrillingCompleted
	}
}
