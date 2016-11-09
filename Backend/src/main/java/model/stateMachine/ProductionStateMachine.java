package model.stateMachine;

import com.github.oxo42.stateless4j.StateMachine;
import com.github.oxo42.stateless4j.StateMachineConfig;
import com.google.gson.Gson;

import app.Constants;
import consumer.MachineDataConsumer;
import consumer.SpectralAnalysisConsumer;

import consumer_listener.MachineDataListener;
import consumer_listener.SpectralAnalysisListener;
import db.DatabaseManager;

//import db.DatabaseManager;

import model.dataModels.ErpData;
import model.dataModels.MachineData;
import model.dataModels.ManufacturingData;
import model.dataModels.SpectralAnalysisData;
import producer.KafkaProducerSpark;

/**
 * Class ProductionStateMachine.
 * @author Daniel
 *
 */
public class ProductionStateMachine implements MachineDataListener, SpectralAnalysisListener {
	private StateMachine<State, Trigger> stateMachine;
	private ManufacturingData manufacturingData;
	private int serialNumber;
	
	//counter for serial number
	private static int counter = 0;
	
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
	
	/**
	 * Constructor ProductionStateMachine.
	 * Configures states and transitions of the state machine.
	 * @param erpData
	 */
	public ProductionStateMachine(ErpData erpData) {
		//listen to events
		MachineDataConsumer.setOnMachineDataListener(this);
		
		//configure state machine
		this.config();
		
		manufacturingData = new ManufacturingData();
		manufacturingData.setErpData(erpData);
		
		//send erp data back to kafka
		new KafkaProducerSpark().send(
			Constants.KAFKA_PROD_TOPIC_ERP_DATA, new Gson().toJson(erpData)
		);
		
		System.out.println("Received erp data");
		
		this.serialNumber = counter++;
	}
	
	/**
	 * Gets the serial number of the product.
	 * @return
	 */
	public int getSerialNumber() {
		return this.serialNumber;
	}
	
	/**
	 * Trigger method.
	 * Receives the production data and
	 * determins the next state.
	 * @param event
	 */
	private void trigger(MachineData event) {
		switch(event.getItemName()) {
		case "L1":
			if(event.getValue().equals("false"))
				stateMachine.fire(Trigger.L1Close);
			else
				stateMachine.fire(Trigger.L1Open);
			break;
		case "L2":
			if(event.getValue().equals("false")) 
				stateMachine.fire(Trigger.L2Close);
			else
				stateMachine.fire(Trigger.L2Open);
			break;
		case "L3":
			if(event.getValue().equals("false"))
				stateMachine.fire(Trigger.L3Close);
			else
				stateMachine.fire(Trigger.L3Open);
			break;
		case "L4":
			if(event.getValue().equals("false"))
				stateMachine.fire(Trigger.L4Close);
			else
				stateMachine.fire(Trigger.L4Open);
			break;
		case "L5":
			if(event.getValue().equals("false")) {
				stateMachine.fire(Trigger.L5Close);
				SpectralAnalysisConsumer.setOnSpectralAnalysisListener(this);
			}
			else {
				stateMachine.fire(Trigger.L5Open);
				MachineDataConsumer.removeMachineDataListener(this);
			}
			break;
		case "MILLING":
			if(event.getValue().equals("true"))
				stateMachine.fire(Trigger.MillingStart);
			else
				stateMachine.fire(Trigger.MillingStop);
			break;
		case "DRILLING":
			if(event.getValue().equals("true"))
				stateMachine.fire(Trigger.DrillingStart);
			else
				stateMachine.fire(Trigger.DrillingStop);
			break;
		}
		
		System.out.println(stateMachine.getState());
	}
	
	/**
	 * Configures and creates state machine.
	 */
	private void config() {
		StateMachineConfig<State, Trigger> machineConfig = new StateMachineConfig<>();
		
		//configure states and transitions of state machine
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
		
		//create state machine
		stateMachine = new StateMachine<>(State.EnterL1, machineConfig);
	}

	/**
	 * Actions to be executed
	 * as soon as spectral analysis data
	 * arrives.
	 * @param data
	 */
	@Override
	public void onSpectralAnalysisData(SpectralAnalysisData data) {
		//remove spectral analysis listener
		SpectralAnalysisConsumer.removeSpectralAnalysisListener(this);
		
		//save analysis data in manufacturingData
		manufacturingData.setAnalysisData(data);
		
		//put data to kafka broker
		new KafkaProducerSpark().send(
			Constants.KAFKA_PROD_TOPIC_MANUFACTURING_DATA, new Gson().toJson(manufacturingData)
		);
		
		//save manufacturing data in db
		DatabaseManager.getManager().insertManifacturingDocument(manufacturingData);
		System.out.println("Received spectral analysis data");
		System.out.println("Saved data from statemachine " + serialNumber + " in mongodb");
	}

	/**
	 * Actions to be executed
	 * as soon as machine data
	 * arrives.
	 * @param data
	 */
	@Override
	public void onMachineData(MachineData data) {
		trigger(data);
		
		//save machine data
		manufacturingData.appendMachineData(data);
	}
	
	/**
	 * Creates a string representation
	 * of this object.
	 * @return
	 */
	@Override
	public String toString() {
		return "Statemachine " + this.serialNumber;
	}
	
	/*
	private void adjustMillingSpeed() {}
	
	private void adjustDrillingSpeed() {}
	
	private void startTimer() {}
	
	private void stopTimer() {}
	
	private void trackTemperature() {}
	
	private void startGlobalTimer() {}
	
	private void stopGlobalTimer() {}
	
	private void saveSpectralAnalysis() {}
	*/
}