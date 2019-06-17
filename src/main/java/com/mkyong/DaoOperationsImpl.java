package com.mkyong;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mkyong.mappers.Configuration;
import com.mkyong.mappers.ConfigurationMapper;

@Repository
public class DaoOperationsImpl implements DaoOperations {
	
	@Autowired
	 private JdbcTemplate jdbcTemplate;
	 
	@Override
	public Long getChunkCommitCount() {
		String sql = "SELECT * FROM CONFIGURATION_PARAMETERS WHERE KEY = 'COMMIT_SIZE'";
		Configuration configuration = (Configuration)jdbcTemplate.queryForObject(
				sql, new ConfigurationMapper());
		return Long.parseLong(configuration.getValue());
	}

	@Override
	public Long getJobId() {
		String sql = "select JOB_ID_SEQUENCE.nextval from dual";
		Long queryForObject = (Long) jdbcTemplate.queryForObject(sql, Long.class);
		return queryForObject.longValue();
	}

	@Override
	public String getLandingDirectory() {
		String sql = "SELECT * FROM CONFIGURATION_PARAMETERS WHERE KEY = 'LANDING_DIRECTORY'";
		Configuration configuration = (Configuration)jdbcTemplate.queryForObject(
				sql, new ConfigurationMapper());	
		return configuration.getValue();
	}

	@Override
	public String getProcessingDirectory() {
		String sql = "SELECT * FROM CONFIGURATION_PARAMETERS WHERE KEY = 'PROCESSING_DIRECTORY'";
		Configuration configuration = (Configuration)jdbcTemplate.queryForObject(
				sql, new ConfigurationMapper());	
		return configuration.getValue();
	}

	@Override
	public String getSuccessfullyProcessedDirectory() {
		String sql = "SELECT * FROM CONFIGURATION_PARAMETERS WHERE KEY = 'SUCCESS_DIRECTORY'";
		Configuration configuration = (Configuration)jdbcTemplate.queryForObject(
				sql, new ConfigurationMapper());	
		return configuration.getValue();
	}

	@Override
	public String getErrorDirectory() {
		String sql = "SELECT * FROM CONFIGURATION_PARAMETERS WHERE KEY = 'ERROR_DIRECTORY'";
		Configuration configuration = (Configuration)jdbcTemplate.queryForObject(
				sql, new ConfigurationMapper());	
		return configuration.getValue();
	}

	@Override
	public String getSkippableDirectory() {
		String sql = "SELECT * FROM CONFIGURATION_PARAMETERS WHERE KEY = 'SKIPPED_ITEMS_DIRECTORY'";
		Configuration configuration = (Configuration)jdbcTemplate.queryForObject(
				sql, new ConfigurationMapper());	
		return configuration.getValue();
	}

	@Override
	public Long getSkipCount() {
		String sql = "SELECT * FROM CONFIGURATION_PARAMETERS WHERE KEY = 'SKIP_LIMIT'";
		Configuration configuration = (Configuration)jdbcTemplate.queryForObject(
				sql, new ConfigurationMapper());
		return Long.parseLong(configuration.getValue());
	}

	@Override
	public Long getBlueToothMaximumAmount() {
		String sql = "SELECT * FROM CONFIGURATION_PARAMETERS WHERE KEY = 'BLUE_TOOTH_TRANSACTION_EXCEEDS_AMOUNT'";
		Configuration configuration = (Configuration)jdbcTemplate.queryForObject(
				sql, new ConfigurationMapper());
		return Long.parseLong(configuration.getValue());
	}

	@Override
	public Long getMobileMaxAmount() {
		String sql = "SELECT * FROM CONFIGURATION_PARAMETERS WHERE KEY = 'MOBILE_TRANSACTION_EXCEEDS_AMOUNT'";
		Configuration configuration = (Configuration)jdbcTemplate.queryForObject(
				sql, new ConfigurationMapper());
		return Long.parseLong(configuration.getValue());
	}

	@Override
	public Long getAcquirerId() {
		String sql = "select '1' CONFIGURATION_ID, 'KEY' key, nvl2(max(id),max(id) + 1, 1) value from ACQUIRER_SETTLEMENTS";
		Configuration configuration = (Configuration)jdbcTemplate.queryForObject(
				sql, new ConfigurationMapper());
		return Long.parseLong(configuration.getValue());
	}

}
