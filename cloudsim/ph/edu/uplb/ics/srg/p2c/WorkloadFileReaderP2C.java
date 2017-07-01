package ph.edu.uplb.ics.srg.p2c;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.UtilizationModel;
import org.cloudbus.cloudsim.UtilizationModelFull;
import org.cloudbus.cloudsim.util.WorkloadModel;

public class WorkloadFileReaderP2C implements WorkloadModel {

    /**
     * Trace file name.
     */
    private final File file;
    private ArrayList<Cloudlet> jobs = null;
    private String[] fieldArray = null; 
    private int brokerId=0;
    
    
    public WorkloadFileReaderP2C(final String fileName, final int rating) throws FileNotFoundException {
        if (fileName == null || fileName.length() == 0) {
                throw new IllegalArgumentException("Invalid trace file name.");
        } else if (rating <= 0) {
                throw new IllegalArgumentException("Resource PE rating must be > 0.");
        }

        file = new File(fileName);
        if (!file.exists()) {
                throw new FileNotFoundException("Workload trace " + fileName + " does not exist");
        }

        }
	
    public void setBrokerId(int id){
    	this.brokerId=id;
    }
    
	@Override
	public List<Cloudlet> generateWorkload() {
		if (jobs == null) {
            jobs = new ArrayList<Cloudlet>();

            // create a temp array
            fieldArray = new String[5];

            try {
            	readFile(file);
            } catch (final FileNotFoundException e) {
            } catch (final IOException e) {
            }
		}
		for(Cloudlet job: jobs){
			job.setUserId(this.brokerId);
		}
		
		return jobs;
	}

	private void parseValue(final String line, final int lineNum) {
        // skip a comment line
        if (line.startsWith(";")) {
                return;
        }

        final String[] sp = line.split(","); // split the fields based on a
        // space
        int len = 0; // length of a string
        int index = 0; // the index of an array

        // check for each field in the array
        for (final String elem : sp) {
                len = elem.length(); // get the length of a string

                // if it is empty then ignore
                if (len == 0) {
                        continue;
                }
                fieldArray[index] = elem;
                index++;
        }
        
        UtilizationModel utilizationModel = new UtilizationModelFull();
        final Cloudlet wgl = new Cloudlet(
                        Integer.parseInt(fieldArray[0]),
                        Integer.parseInt(fieldArray[1]),
                        Integer.parseInt(fieldArray[2]),
                        Integer.parseInt(fieldArray[2]),
                        Integer.parseInt(fieldArray[2]),
                        utilizationModel,
                        utilizationModel,
                        utilizationModel);
        
        jobs.add(wgl);
    }
	
	
	private boolean readFile(final File fl) throws IOException, FileNotFoundException {
        boolean success = false;
        BufferedReader reader = null;
        try {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(fl)));

                // read one line at the time
                int line = 1;
                String readLine = null;
                while (reader.ready() && (readLine = reader.readLine()) != null) {
                        parseValue(readLine, line);
                        line++;
                }

                reader.close();
                success = true;
        } finally {
                if (reader != null) {
                        reader.close();
                }
        }

        return success;
}
	
}
