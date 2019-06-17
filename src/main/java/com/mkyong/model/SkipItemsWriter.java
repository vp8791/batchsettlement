package com.mkyong.model;

public class SkipItemsWriter {
	private String creditCardNumber;
	private Long jobId;
	private String processingFile;
	private String acquirer;
	private String exception;
	private String skipPhase;
	
	public String getException() {
		return exception;
	}
	public void setException(String exception) {
		this.exception = exception;
	}
	public String getSkipPhase() {
		return skipPhase;
	}
	public void setSkipPhase(String skipPhase) {
		this.skipPhase = skipPhase;
	}
	public String getCreditCardNumber() {
		return creditCardNumber;
	}
	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}
	public Long getJobId() {
		return jobId;
	}
	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}
	public String getProcessingFile() {
		return processingFile;
	}
	public void setProcessingFile(String processingFile) {
		this.processingFile = processingFile;
	}
	public String getAcquirer() {
		return acquirer;
	}
	public void setAcquirer(String acquirer) {
		this.acquirer = acquirer;
	}

	
}
