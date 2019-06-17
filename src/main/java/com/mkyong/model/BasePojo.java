package com.mkyong.model;

public class BasePojo {

	private  Long id;
	private String loadedDate;
	private Long jobId;
	private String processingFile;
	private String acquirer;
	private Long commitSize;
	private int userrulesscore;
	private int systemrulescore;
	private int totalScore;

	public int getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}

	public String getLoadedDate() {
		return loadedDate;
	}
	public void setLoadedDate(String loadedDate) {
		this.loadedDate = loadedDate;
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
	public Long getCommitSize() {
		return commitSize;
	}
	public void setCommitSize(Long commitSize) {
		this.commitSize = commitSize;
	}

	public int getUserrulesscore() {
		return userrulesscore;
	}

	public void setUserrulesscore(int userrulesscore) {
		this.userrulesscore = userrulesscore;
	}

	public int getSystemrulescore() {
		return systemrulescore;
	}

	public void setSystemrulescore(int systemrulescore) {
		this.systemrulescore = systemrulescore;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


}
