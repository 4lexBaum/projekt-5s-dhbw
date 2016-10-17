package consumer;

import model.dataModels.MachineData;

/**
 * Interface MachineDataListener.
 * This interface is used for the listener conecept.
 * The MachineDataConsumer notifies all ohter consumers as soon as
 * an event occures.
 * @author Daniel
 *
 */
public interface MachineDataListener {
	public void onMachineData(MachineData data);
}
