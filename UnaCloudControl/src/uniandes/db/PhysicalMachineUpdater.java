package uniandes.db;

import java.sql.Connection;
import java.sql.PreparedStatement;

import unacloud.share.enums.VirtualMachineExecutionStateEnum;

public class PhysicalMachineUpdater {

	/**
	 * Update a physical machine entity on database based in hostname
	 * @param machine
	 * @return
	 */
	public static boolean updatePhysicalMachine(String host, String hostUser, Connection con){
		try {
			String query = "update physical_machine pm set pm.withUser= ?, pm.last_report = CURRENT_TIMESTAMP WHERE pm.name = ?"; 
			PreparedStatement ps = con.prepareStatement(query);
			ps.setBoolean(1, (hostUser!=null&&!hostUser.isEmpty()&&!(hostUser.replace(">","").replace(" ","")).equals("null")));
			ps.setString(2, host);
			System.out.println("Change "+ps.executeUpdate()+" lines");
			try{ps.close();}catch(Exception e){}
			return true;
		} catch (Exception e) {
			e.printStackTrace();			
		}		
		return false;
	}
	

	/**
	 * Update status from of virtual Machine
	 * @param id virtual machine 
	 * @param host unique in net	
	 * @param message description
	 * @param status in agent
	 * @param con connection to database
	 * @return
	 */
	public static boolean updateVirtualExecution(Long id, String host, String message, VirtualMachineExecutionStateEnum status, Connection con){
		try {
			String query = "update virtual_machine_execution vm set vm.message= ?, vm.last_report = CURRENT_TIMESTAMP, vm.status = ?  WHERE vm.id = ? and vm.execution_node_id = (SELECT pm.id FROM physical_machine pm WHERE pm.name = ?);"; 
			PreparedStatement ps = con.prepareStatement(query);			
			ps.setString(1, message);
			ps.setString(2, status.name());
			ps.setLong(3, id);
			ps.setString(4, host);
			System.out.println("Change "+ps.executeUpdate()+" lines");
			try{ps.close();}catch(Exception e){}
			return true;
		} catch (Exception e) {
			e.printStackTrace();			
		}		
		return false;
	}
	
	
	/**
	 * Update all virtual executions with id in array and host by name
	 * @param ids
	 * @param host
	 * @param con
	 * @return
	 */
	public static boolean updateVirtualMachinesExecutions(Long[]ids,String host, Connection con){
		if(ids==null||ids.length==0)return false;
		try {
			StringBuilder builder = new StringBuilder();
			for(@SuppressWarnings("unused") Long pm: ids){
				builder.append("?,");
			}
			String query = "update virtual_machine_execution vm set vm.last_report = CURRENT_TIMESTAMP where vm.id in ("+builder.deleteCharAt( builder.length() -1 ).toString()+") and vm.execution_node_id = (SELECT pm.id FROM physical_machine pm WHERE pm.name = ?)";
			PreparedStatement ps = con.prepareStatement(query);
			int index = 1;
			for(Long idvme: ids){
				ps.setLong(index++, idvme);
			}
			ps.setString(index, host);
			System.out.println("Change "+ps.executeUpdate()+" lines");
			try{ps.close();}catch(Exception e){}
			return true;
		} catch (Exception e) {
			e.printStackTrace();			
		}		
		return false;
	}

}