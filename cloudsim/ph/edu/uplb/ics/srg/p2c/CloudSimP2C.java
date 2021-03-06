/*
 * @author jachermocilla@gmail.com
 */

package ph.edu.uplb.ics.srg.p2c;


import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.CloudletSchedulerTimeShared;
import org.cloudbus.cloudsim.Datacenter;
import org.cloudbus.cloudsim.DatacenterBroker;
import org.cloudbus.cloudsim.DatacenterCharacteristics;
import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Pe;
import org.cloudbus.cloudsim.Storage;
import org.cloudbus.cloudsim.UtilizationModel;
import org.cloudbus.cloudsim.UtilizationModelFull;
import org.cloudbus.cloudsim.UtilizationModelStochastic;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.VmAllocationPolicySimple;
import org.cloudbus.cloudsim.VmSchedulerTimeShared;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.provisioners.BwProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple;
import org.cloudbus.cloudsim.util.WorkloadFileReader;


/**
 * Simulation of the Peak-Two Cloud(P2C) (http://srg.ics.uplb.edu.ph/projects/peak-two-cloud) 
 * in CloudSim
 */
public class CloudSimP2C {
	
	/** 
	 * The cloudlet list.
	 * The application that must run on VMs  
	 */
	private static List<Cloudlet> cloudletList;

	/**
	 * The list of VMs that will run on the P2C data center 
	 */
	private static List<Vm> vmlist;

	/**
	 * Reads workload
	 */
	private static WorkloadFileReader workloadFileReader;
	
	/**
	 * Broker
	 */
	private static DatacenterBroker broker;
	
		
	private static List<Cloudlet> createCloudlets() throws FileNotFoundException{
		cloudletList = new ArrayList<Cloudlet>();

    	//Cloudlet properties
    	int id = 0;					//cloudlet id
    	int pesNumber=1;				//number of cores required
    	long length = 250000;		//MIPS
    	long fileSize = 300;
    	long outputSize = 300;

    	int brokerId=broker.getId();

    	
    	UtilizationModel utilizationModel = new UtilizationModelFull();
    	//UtilizationModel utilizationModel = new UtilizationModelStochastic();
    	//Cloudlet cloudlet1 = new Cloudlet(id, length, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);

/*    	
    	Cloudlet cloudlet1 = new Cloudlet(0, length, 1, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
    	cloudlet1.setUserId(brokerId);

    	Cloudlet cloudlet2 = new Cloudlet(1, length, 2, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
    	cloudlet2.setUserId(brokerId);
    	
    	Cloudlet cloudlet3 = new Cloudlet(2, length, 1, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
    	cloudlet3.setUserId(brokerId);

    	Cloudlet cloudlet4 = new Cloudlet(3, length, 2, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
    	cloudlet4.setUserId(brokerId);
*/  
    	workloadFileReader = new WorkloadFileReader("/home/jachermocilla/Sources/github/peak-two-cloud-github/cloudsim/test-input-100.swf", 1);
    	cloudletList = workloadFileReader.generateWorkload();
    	for (Cloudlet c: cloudletList){
    		//System.out.println(c.getCloudletLength()+":"+c.getCloudletId());
    		c.setUserId(brokerId);
    	}
    	
    	return cloudletList;
	}
	
	private static List<Vm> createVmList(){
		vmlist = new ArrayList<Vm>();

    	//VM description
    	int vmid = 0;
    	int mips = 250; 		//processing capability millions of instructions per second
    	long size = 10000; 		//image size (MB)
    	int ram = 512; 			//vm memory (MB)
    	long bw = 1000; 		//bandwidth
    	int pesNumber = 1; 		//number of cpus
    	String vmm = "Xen"; 	//VMM name

    	int brokerId=broker.getId();
    	
    	//create four VMs
    	//Vm vm1 = new Vm(vmid, brokerId, mips, pesNumber, ram, bw, size, vmm, new CloudletSchedulerTimeShared());

    	Vm vm1 = new Vm(0, brokerId, mips, 1, 1024, bw, size, vmm, new CloudletSchedulerTimeShared());

    	Vm vm2 = new Vm(1, brokerId, mips, 2, 1024, bw, size, vmm, new CloudletSchedulerTimeShared());

    	Vm vm3 = new Vm(2, brokerId, mips, 1, 2048, bw, size, vmm, new CloudletSchedulerTimeShared());

    	Vm vm4 = new Vm(3, brokerId, mips, 2, 2048, bw, size, vmm, new CloudletSchedulerTimeShared());
    	
    	//add the VMs to the vmList
    	vmlist.add(vm1);
    	vmlist.add(vm2);
    	vmlist.add(vm3);
    	vmlist.add(vm4);
    	
    	return vmlist;
		
	}
	
	
	/** 
	 * Creates the P2C datacenter
	 */
	
	private static Datacenter createDatacenter(String name){

        //Here are the steps needed to create a PowerDatacenter:
        //1. We need to create a list to store
    	//    our machine
    	List<Host> hostList = new ArrayList<Host>();

        //2. A Machine contains one or more PEs or CPUs/Cores.
    	//In this example, it will have only four cores.
    	List<Pe> peList = new ArrayList<Pe>();

    	//obtained from bogomips of hosts in P2C
    	int mips = 6168;

        //3. Create PEs and add these into a list.
    	//Hosts in P2C have four cores thus 4 Pe's here
    	peList.add(new Pe(0, new PeProvisionerSimple(mips))); // need to store Pe id and MIPS Rating
    	peList.add(new Pe(1, new PeProvisionerSimple(mips)));
    	peList.add(new Pe(2, new PeProvisionerSimple(mips)));
    	peList.add(new Pe(3, new PeProvisionerSimple(mips)));
    	
        //4. Create Host with its id and list of PEs and add them to the list of machines in the datacenter
        int hostId = 0;
        int ram = 4096; //host memory (MB)
        long storage = 1000000; //host storage
        int bw = 10000;

        //P2C has 12 physical nodes
        for (int hid=0;hid<12;hid++){
        	hostList.add(
    			new Host(
    				hid,
    				new RamProvisionerSimple(ram),
    				new BwProvisionerSimple(bw),
    				storage,
    				peList,
    				new VmSchedulerTimeShared(peList)
    			)
    		); 
        }


        //5. Create a DatacenterCharacteristics object that stores the
        //    properties of a data center: architecture, OS, list of
        //    Machines, allocation policy: time- or space-shared, time zone
        //    and its price (G$/Pe time unit).
        String arch = "x86";      // system architecture
        String os = "Linux";          // operating system
        String vmm = "KVM";
        double time_zone = 10.0;        // time zone this resource located
        double cost = 3.0;              // the cost of using processing in this resource
        double costPerMem = 0.05;		// the cost of using memory in this resource
        double costPerStorage = 0.001;	// the cost of using storage in this resource
        double costPerBw = 0.0;			// the cost of using bw in this resource
        LinkedList<Storage> storageList = new LinkedList<Storage>();	//we are not adding SAN devices by now

        DatacenterCharacteristics characteristics = new DatacenterCharacteristics(
                arch, os, vmm, hostList, time_zone, cost, costPerMem, costPerStorage, costPerBw);

        // 6. Finally, we need to create a PowerDatacenter object.
        Datacenter datacenter = null;
        try {
            //datacenter = new Datacenter(name, characteristics, new VmAllocationPolicySimple(hostList), storageList, 0);
            datacenter = new Datacenter(name, characteristics, new VmAllocationPolicyFFD(hostList), storageList, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return datacenter;
    }

	
    //We strongly encourage users to develop their own broker policies, to submit vms and cloudlets according
    //to the specific rules of the simulated scenario
    private static DatacenterBroker createBroker(){
    	DatacenterBroker broker = null;
        try {
		broker = new DatacenterBroker("Broker");
	} catch (Exception e) {
		e.printStackTrace();
		return null;
	}
    	return broker;
    }	

    /**
     * Prints the Cloudlet objects
     * @param list  list of Cloudlets
     */
    private static void printCloudletList(List<Cloudlet> list) {
        int size = list.size();
        Cloudlet cloudlet;

        String indent = "     ";
        Log.printLine();
        Log.printLine("========== OUTPUT ==========");
        Log.printLine("Cloudlet ID" + indent + "STATUS" + indent + 
        		"Data center ID" + indent + "VM ID" + indent + "Time" + indent + "Start Time" + indent + "Finish Time");

        DecimalFormat dft = new DecimalFormat("###.##");
        for (int i = 0; i < size; i++) {
            cloudlet = list.get(i);
            Log.print(indent + cloudlet.getCloudletId() + indent + indent);

            if (cloudlet.getCloudletStatus() == Cloudlet.SUCCESS){
                Log.print("SUCCESS");

                
            	Log.printLine( indent + indent + cloudlet.getResourceId() + indent + indent + indent + cloudlet.getVmId() +
                     indent + indent + dft.format(cloudlet.getActualCPUTime()) + indent + indent + dft.format(cloudlet.getExecStartTime())+
                         indent + indent + dft.format(cloudlet.getFinishTime()));
               
            }
        }
    }    
    

    /**
	 * Creates main() to run this example
	 */
	public static void main(String[] args) {
			Log.printLine("Starting CloudSimP2C...");
	        try {
	            	int num_user = 1;   // number of cloud users
	            	Calendar calendar = Calendar.getInstance();
	            	boolean trace_flag = false;  // mean trace events

	        	    // First step: Initialize the CloudSim package. It should be called
	            	// before creating any entities.
	            	// Initialize the CloudSim library
	            	CloudSim.init(num_user, calendar, trace_flag);

	            		            	
	            	//Second step: Create Datacenters
	            	//Datacenters are the resource providers in CloudSim. We need at list one of them to run a CloudSim simulation
	            	@SuppressWarnings("unused")
					Datacenter datacenter0 = createDatacenter("Peak-Two_Cloud");

	            	//Third step: Create Broker
	            	broker = createBroker();
	            	
	            	//Fourth step: Create the virtual machines
	            	vmlist=createVmList();
	            	
	            	//For FFD we sort the VMs based on the number of Pes for FFD
	            	vmlist.sort(new VmComparator());
	            	Collections.reverse(vmlist);

	            	//submit vm list to the broker
	            	broker.submitVmList(vmlist);

	            	//Fifth step: Create Cloudlets
	            	cloudletList = createCloudlets();
	            	
	            	broker.submitCloudletList(cloudletList);

	            	//bind the cloudlets to the vms. This way, the broker
	            	// will submit the bound cloudlets only to the specific VM
	            	//broker.bindCloudletToVm(cloudlet1.getCloudletId(),vm1.getId());

	            	
	            	//Sixth step: Starts the simulation
	            	CloudSim.startSimulation();


	            	// Final step: Print results when simulation is over
	            	List<Cloudlet> newList = broker.getCloudletReceivedList();

	            	CloudSim.stopSimulation();

	            	printCloudletList(newList);

	            	Log.printLine("Simulation finished!");
	        }
	        catch (Exception e) {
	            e.printStackTrace();
	            Log.printLine("The simulation has been terminated due to an unexpected error");
	        }
	    }

}
