package consumer_listener;

import model.dataModels.MachineData;

/**
 * Interface MachineDataListener.
 * @author Daniel
 *
 */
public interface MachineDataListener {
	public void onMachineData(MachineData data);
}