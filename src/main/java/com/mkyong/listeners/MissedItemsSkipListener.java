package com.mkyong.listeners;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.management.RuntimeErrorException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.SkipListener;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.mkyong.BatchConstants;
import com.mkyong.model.Report;
import com.mkyong.model.SkipItemsWriter;

@Component
@Scope(value = "step")
public class MissedItemsSkipListener implements SkipListener<Report, Report> {

	@Value("#{jobParameters['skipCount']}")
	private String skipCount;

	@Value("#{jobParameters['skipDirectory']}")
	private String skipDirectory;

	@Value("#{jobParameters['acquirer']}")
	private String acquirer;
	
	@Value("#{jobParameters['processingFile']}")
	private String processingFile;
	
	@Value("#{jobParameters['jobId']}")
	private String jobId;
	
	private String FILED_SEPERATOR = ":";
	
	

	@Override
	public void onSkipInRead(Throwable t) {
		if (t instanceof FlatFileParseException) {
			FlatFileParseException ffpe = (FlatFileParseException) t;
			StringBuilder errorMessage = new StringBuilder();
			errorMessage.append("An error occured while processing the " + ffpe.getLineNumber()
					+ " line of the file.  Below was the faulty " + "input.\n");
			errorMessage.append(ffpe.getInput() + "\n");
			System.out.println(
					"=============================MissedItemsSkipListener - onSkipInRead:(skipCount, skipDirectory, errorMessage):("
							+ skipCount + "," + skipDirectory + "," + errorMessage + ")");
			//writeFile(item, t, "R");
			
		} else {
			t.printStackTrace();
		}

	}

	@Override
	public void onSkipInWrite(Report item, Throwable t) {
		System.out.println(
				"=============================MissedItemsSkipListener - onSkipInWrite:(skipCount, skipDirectory, creditCardNumber, acquirer):("
						+ skipCount + "," + skipDirectory + "," + item.getCreditCardNumber() + "," + acquirer   + ")");
		writeFile(item, t, "W");
	}

	@Override
	public void onSkipInProcess(Report item, Throwable t) {
		System.out.println(
				"=============================MissedItemsSkipListener - onSkipInProcess:(skipCount, skipDirectory, creditCardNumber, acquirer):("
						+ skipCount + "," + skipDirectory + "," + item.getCreditCardNumber() + "," + acquirer   + ")");			
		writeFile(item, t, "P");
	}

	
	private void writeFile(Report item, Throwable t, String phase) {
		List<String> lines = new ArrayList<String>();
		lines.add(getSkippedItem(item, t, phase));
		
		String skipFileName = (new File(processingFile)).getName() + ".skip";
		File file_to_write = new File(skipDirectory + "/" + skipFileName);
		try {
            if(!file_to_write.exists()) {
                List<String> header = new ArrayList<String>();
                header.add(getHeader());        
                FileUtils.writeLines(file_to_write, header, false);
            }
            FileUtils.writeLines(file_to_write,lines, true);
        } catch (IOException e) {
            throw new RuntimeException("Unable to write skip file to " + file_to_write.getAbsolutePath() , e);
        }		
		
	}
	
	
	
	private String getHeader() {
		return "File Record" + FILED_SEPERATOR + "Acquirer" + FILED_SEPERATOR + "JobId" + FILED_SEPERATOR + "Processed File" + FILED_SEPERATOR + 
        		"Skipped Phase" + FILED_SEPERATOR + "Exception";
	}
	
	private String getSkippedItem(Report item, Throwable t, String skipPhase) {
		SkipItemsWriter skipItem = new SkipItemsWriter();
		skipItem.setCreditCardNumber(item.getCreditCardNumber());
		skipItem.setAcquirer(acquirer);
		skipItem.setProcessingFile(processingFile);
		skipItem.setJobId(Long.valueOf(jobId));
		skipItem.setSkipPhase(skipPhase);
		skipItem.setException(t.getMessage());
		
		StringBuilder itemToWrite = new StringBuilder();
		itemToWrite.append(skipItem.getCreditCardNumber() + FILED_SEPERATOR + skipItem.getAcquirer() + FILED_SEPERATOR + jobId +  FILED_SEPERATOR + 
				skipItem.getProcessingFile() + FILED_SEPERATOR +
				skipItem.getSkipPhase() + FILED_SEPERATOR + skipItem.getException());
		t.printStackTrace();
		return itemToWrite.toString();
		
	}
	

}
