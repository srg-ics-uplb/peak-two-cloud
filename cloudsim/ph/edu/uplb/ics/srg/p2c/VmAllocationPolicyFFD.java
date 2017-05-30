package ph.edu.uplb.ics.srg.p2c;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.VmAllocationPolicy;

public class VmAllocationPolicyFFD extends VmAllocationPolicy {
	/** The host list. */
	private List<? extends Host> hostList;

	public VmAllocationPolicyFFD(List<? extends Host> list) {
		super(list);
		
	}

	public Host getHost(Vm vm){
		return null;		
	}

	@Override
	public boolean allocateHostForVm(Vm arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean allocateHostForVm(Vm arg0, Host arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void deallocateHostForVm(Vm arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Host getHost(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> optimizeAllocation(List<? extends Vm> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
