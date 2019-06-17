package com.mkyong.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ConfigurationMapper implements RowMapper {

	@Override
	public Configuration mapRow(ResultSet rs, int rowNum) throws SQLException {
		Configuration configuration = new Configuration();
		configuration.setKey(rs.getString("KEY"));
		configuration.setValue(rs.getString("VALUE"));
		return configuration;
	}

}
