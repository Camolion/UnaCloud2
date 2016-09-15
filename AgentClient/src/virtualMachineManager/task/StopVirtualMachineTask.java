package virtualMachineManager.task;

import communication.messages.vmo.VirtualMachineStopMessage;

import virtualMachineManager.PersistentExecutionManager;

/**
 * Task to stop a virtual machine execution
 * @author CesarF
 *
 */
public class StopVirtualMachineTask implements Runnable{
	
	VirtualMachineStopMessage stopMessage;
	
	/**
	 * class constructor
	 * @param stopMessage stop message with operation data
	 */
	public StopVirtualMachineTask(VirtualMachineStopMessage stopMessage) {
		this.stopMessage = stopMessage;
	}
	
	/**
	 * Executes stop message task
	 */
	@Override
	public void run() {
		PersistentExecutionManager.removeExecution((stopMessage).getVirtualMachineExecutionId(),false);
	}
}