package com.mkyong;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;

import com.mkyong.model.*;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component("jobProcessor")
@Scope(value="step")
public class JobProcessor implements ItemProcessor<Report, Report> {
	
	@Value("#{jobParameters['currentJobStartTime']}")
	private String jobStartTime;

	@Value("#{jobParameters['jobId']}")
	private String jobId;

	
	@Value("#{jobParameters['processingFile']}")
	private String processingFile;
	
	@Value("#{jobParameters['acquirer']}")
	private String acquirer;
	
	@Value("#{jobParameters['chunkCommitSize']}")
	private String chunkCommitSize;

	@Autowired
	private DaoOperationsImpl daoOperations;

	private Long initSequenceId;

	@PostConstruct
	public void initId() {
		initSequenceId = daoOperations.getAcquirerId();
	}

	@Override
	public Report process(Report inputItem) throws Exception {	
		System.out.println("Processing Item:" + inputItem );
		inputItem.setId(initSequenceId);
		initSequenceId++;
		inputItem.setJobId(Long.parseLong(jobId));
		inputItem.setLoadedDate(jobStartTime);
		inputItem.setProcessingFile(processingFile);
		inputItem.setAcquirer(acquirer);
		inputItem.setCommitSize(Long.parseLong(chunkCommitSize));

		Long blueToothMaxAmount = daoOperations.getBlueToothMaximumAmount();
		Long mobileMaxAmount = daoOperations.getMobileMaxAmount();

		if(inputItem.getModeOfPayment().equals(BatchConstants.PAYMENT_ACCEPTED_BLUETOOTH)) {
			if (Integer.parseInt(inputItem.getAmount()) > blueToothMaxAmount.intValue()) {
				inputItem.setSystemrulescore(10);
				inputItem.setUserrulesscore(5);
				inputItem.setTotalScore(15);
			} else {
				inputItem.setSystemrulescore(0);
				inputItem.setUserrulesscore(0);
				inputItem.setTotalScore(0);
			}
		} else if(inputItem.getModeOfPayment().equals(BatchConstants.PAYMENT_ACCEPTED_MOBILE)) {
			if (Integer.parseInt(inputItem.getAmount()) > mobileMaxAmount.intValue()) {
				inputItem.setSystemrulescore(11);
				inputItem.setUserrulesscore(6);
				inputItem.setTotalScore(17);
			} else {
				inputItem.setSystemrulescore(0);
				inputItem.setUserrulesscore(0);
				inputItem.setTotalScore(0);
			}
		} else {
			inputItem.setSystemrulescore(0);
			inputItem.setUserrulesscore(0);
			inputItem.setTotalScore(0);
		}

		return inputItem;
	}

	

}
