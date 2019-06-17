package com.mkyong.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class ReportFieldSetMapper implements FieldSetMapper<Report> {

	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

	public Report mapFieldSet(FieldSet fieldSet) throws BindException {

		Report report = new Report();
		report.setCreditCardNumber(fieldSet.readString(0));		
		String reportDate = fieldSet.readString(1);
		try {
			report.setOrderDate(dateFormat.parse(reportDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String amount = fieldSet.readString(2);
		report.setAmount(amount);
		report.setMccCode(fieldSet.readString(3));
		report.setTerminalId(fieldSet.readString(4));
		report.setTransactionReference(fieldSet.readString(5));
		String modeofpayment = fieldSet.readString(6);
		report.setModeOfPayment(modeofpayment);
		System.out.println("Processing Report: " + report);
		return report;

	}

}