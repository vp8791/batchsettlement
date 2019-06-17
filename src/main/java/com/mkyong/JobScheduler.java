package com.mkyong;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import com.mkyong.*;
import com.mkyong.utils.*;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component("jobScheduler")
public class JobScheduler {

	@Autowired
	@Qualifier("LoadVisaSettlementsJob")
	private Job loadVisaSettlementsJob;
	
	
	@Autowired
	@Qualifier("LoadMasterCardSettlementsJob")
	private Job loadMasterCardSettlementsJob;
	
	@Autowired
	@Qualifier("LoadJcbSettlementsJob")
	private Job loadJcbSettlementsJob;
	
	@Autowired
	@Qualifier("LoadDinersSettlementsJob")
	private Job loadDinersSettlementsJob;
	
	@Autowired
	@Qualifier("LoadAmexSettlementsJob")
	private Job loadAmexSettlementsJob;
	
	@Autowired
	@Qualifier("LoadDiscoverSettlementsJob")
	private Job loadDiscoverSettlementsJob;
	
	@Autowired
	@Qualifier("LoadEnrouteSettlementsJob")
	private Job loadEnrouteSettlementsJob;
	
	@Autowired
	@Qualifier("LoadVoyagerSettlementsJob")
	private Job loadVoyagerSettlementsJob;


	@Autowired
	private DaoOperationsImpl daoOperations;

	@Autowired
	private JobLauncher jobLauncher;

	public static List<String> SUPPORTED_ACQUIRERS = Arrays.asList(BatchConstants.VISA_ACQUIRER, BatchConstants.MASTERCARD_ACQUIRER, 
			BatchConstants.AMEX_ACQUIRER,BatchConstants.DINERS_ACQUIRER,BatchConstants.DISCOVER_ACQUIRER,BatchConstants.ENROUTE_ACQUIRER,
			BatchConstants.JCB_ACQUIRER,BatchConstants.VOYAGER_ACQUIRER);
	// public static List<String> SUPPORTED_ACQUIRERS = Arrays.asList("visa");
	private static String LINE_NO_SEQUENCE =  "TEMP_LINE_NO_SEQ";

	
	public void runDeceptiveDomainsJob() {
		
		try {						
			Date currentJobStartTime = new Date(System.currentTimeMillis());
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
			String reportDate = df.format(currentJobStartTime);
			
			
			System.out.println("==========About to Start Job============" +  new Date(System.currentTimeMillis()));
			for (String acquirer : SUPPORTED_ACQUIRERS) {
			
				String landingDirectory = daoOperations.getLandingDirectory();
				String processingDirectry = daoOperations.getProcessingDirectory();
				String successDirectory = daoOperations.getSuccessfullyProcessedDirectory();
				String errorDirectory = daoOperations.getErrorDirectory();
				String skipDirectory = daoOperations.getSkippableDirectory();
				Long commitCount = daoOperations.getChunkCommitCount();
				Long skipCount = daoOperations.getSkipCount();
									
				List<String> filenamestoProcess = BatchFileUtils.getFileNamesToBeProcessed(landingDirectory,
						processingDirectry, acquirer);
				
				for (String processingFileName : filenamestoProcess) {
					File prcFile = new File(processingFileName);
					Long jobId = daoOperations.getJobId();
					System.out.println("About to Process Job with following parameters");
					System.out.println("jobId(" + jobId + ")");
					System.out.println("landingDirectory(" + landingDirectory + ")");
					System.out.println("processingDirectry(" + processingDirectry + ")");
					System.out.println("successDirectory(" + successDirectory + ")");
					System.out.println("errorDirectory(" + errorDirectory + ")");
					System.out.println("commitSize(" + commitCount + ")");
					System.out.println("skipCount(" + skipCount + ")");
					System.out.println("skipDirectory(" + skipDirectory + ")");
					System.out.println("jobId(" + jobId + ")");
					
					System.out.println("Processing  File by JobId(" +  jobId + ")>" + processingFileName + "<  at " + new Date(System.currentTimeMillis()));
										
					JobParameters jobParams = new JobParametersBuilder().addString("currentJobStartTime", reportDate)
							.addString("landingDirectory", landingDirectory)
							.addString("processingDirectry", processingDirectry)
							.addString("successDirectory", successDirectory)
							.addString("errorDirectory", errorDirectory)
							.addString("skipDirectory", skipDirectory)
							.addString("processingFile", processingFileName)
							.addString("acquirer", acquirer)
							.addString("chunkCommitSize", Long.toString(commitCount))
							.addLong("commitSize", commitCount)
							.addString("skipCount",  Long.toString(skipCount))
							.addString("jobId", Long.toString(jobId))
							.toJobParameters();
				
					try {
						JobExecution execution = jobLauncher.run(getJob(acquirer), jobParams);
						System.out.println("===============Exit Status========== : " + execution.getStatus());			
						if (execution.getExitStatus().getExitCode().equals(ExitStatus.FAILED.getExitCode())) {			
							File processFx = new File(processingFileName);	
							String errorFilePath = errorDirectory + "/" + processFx.getName() + "." + Long.toString(jobId) + ".error";
							BatchFileUtils.moveFile(processingFileName,  errorFilePath);
							System.out.println("=======Job Failed .. Moved file(" +  processingFileName + ") to (" + errorFilePath + ")" );
						}  
						
						if (execution.getExitStatus().getExitCode().equals(ExitStatus.COMPLETED .getExitCode())) {			
							File processFx = new File(processingFileName);	
							String successFilePath = successDirectory + "/" + processFx.getName()  + "." + Long.toString(jobId) + ".success";
							BatchFileUtils.moveFile(processingFileName,  successFilePath);
							System.out.println("=======Job Completed .. Moved file(" +  processingFileName + ") to (" + successFilePath + ")" );					
						}  
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
					}
									
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}

		System.out.println("Done");

	}
	
	public Job getJob(String acquirer) {
		if (acquirer.equals(BatchConstants.VISA_ACQUIRER)) {
			return loadVisaSettlementsJob;
		} else if (acquirer.equals(BatchConstants.MASTERCARD_ACQUIRER)) {
			return loadMasterCardSettlementsJob;
		} else if (acquirer.equals(BatchConstants.AMEX_ACQUIRER)) {
			return loadAmexSettlementsJob;
		} else if (acquirer.equals(BatchConstants.DINERS_ACQUIRER)) {
			return loadDinersSettlementsJob;
		} else if (acquirer.equals(BatchConstants.DISCOVER_ACQUIRER)) {
			return loadDiscoverSettlementsJob;
		} else if (acquirer.equals(BatchConstants.ENROUTE_ACQUIRER)) {
			return loadEnrouteSettlementsJob;
		} else if (acquirer.equals(BatchConstants.JCB_ACQUIRER)) {
			return loadJcbSettlementsJob;
		} else if (acquirer.equals(BatchConstants.VOYAGER_ACQUIRER)) {
			return loadVoyagerSettlementsJob;
		} else {
			throw new RuntimeException("Acquirer(" + acquirer + ") Job not not supported ");
		}
	}

}
