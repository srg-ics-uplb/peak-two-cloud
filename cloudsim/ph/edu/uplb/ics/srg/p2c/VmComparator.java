package ph.edu.uplb.ics.srg.p2c;

import java.util.Comparator;

import org.cloudbus.cloudsim.Vm;

public class VmComparator implements Comparator<Vm>{

	@Override
	public int compare(Vm vm1, Vm vm2) {
		double w1;
		double w2;
		
		int p1=vm1.getNumberOfPes();
		int p2=vm2.getNumberOfPes();
				
		int m1=vm1.getCurrentRequestedRam();
		int m2=vm2.getCurrentRequestedRam();
		
		
		double weight_pe=1.0;
		double weight_ram=0.0;
		
		w1=((p1/4.0)*weight_pe)+((m1/4096.0)*weight_ram);
		w2=((p2/4.0)*weight_pe)+((m2/4096.0)*weight_ram);
		
		System.out.println(w1+":"+w2);
		
		
		if (w1 > w2) {
			return 1;
	    } else if (w1 < w2){
	        return -1;
	    } else {
	        return 0;
	    }
	}

}
