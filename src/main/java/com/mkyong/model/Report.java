package com.mkyong.model;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import com.mkyong.BatchConstants;
import org.springframework.format.annotation.DateTimeFormat;

import com.mkyong.validators.AcceptedValues;

public class Report extends BasePojo {

	 @NotNull
	 @Pattern(regexp = "^[0-9]{10,16}$", message = "Credit card number must be between 10 and 1f characters in Length and all fields must be between 0 and 9")
	private String creditCardNumber;
	
	
	@NotNull(message = "Date is required")
    @Past(message = "Order date should be in the past")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date orderDate; 
	
	//Amount between 1 and 1000
	 @NotNull
	@Pattern(regexp = "^[0-9]{1,4}$", message = "Amount must between 1 and 1000")
    private String amount; 
	
	//Mcc code Min 5 chars and Max 6 Chars can be numbers and alphabets
	@NotNull
	@Pattern(regexp = "^[a-zA-Z0-9]{5,6}$", message = "Mcc code Min 5 chars and Max 6 Chars can be numbers and alphabets")
	private String mccCode;
	
	
	//Terminal Id should be 5-6 characters and can contain alphabets and numbers
	 @NotNull
	 @Pattern(regexp = "^[a-zA-Z0-9]{5,6}$", message = "terminal Id should be 5-6 characters and can contain alphabets and numbers")
	private String terminalId;
	
	//Transaction refernce should be between 10 and 16 chars
	@NotNull
	@Pattern(regexp = "^[a-zA-Z0-9]{10,16}$", message = "Transaction refernce should be between 10 and 16 chars")
	private String transactionReference;
	
	//Values should be ChipAndPin, Signature or BlueTooth
	@AcceptedValues(acceptValues = { BatchConstants.PAYMENT_ACCEPTED_CHIPANDPIN, BatchConstants.PAYMENT_ACCEPTED_SIGNATURE, BatchConstants.PAYMENT_ACCEPTED_BLUETOOTH,
			BatchConstants.PAYMENT_ACCEPTED_MOBILE, BatchConstants.PAYMENT_ACCEPTED_ONLINE, BatchConstants.PAYMENT_ACCEPTED_CHEQUE}, message = "Payment mode must be ChipAndPin or  Signature or BlueTooth")
     private String modeOfPayment;


	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getMccCode() {
		return mccCode;
	}

	public void setMccCode(String mccCode) {
		this.mccCode = mccCode;
	}

	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	public String getTransactionReference() {
		return transactionReference;
	}

	public void setTransactionReference(String transactionReference) {
		this.transactionReference = transactionReference;
	}

	public String getModeOfPayment() {
		return modeOfPayment;
	}

	public void setModeOfPayment(String modeOfPayment) {
		this.modeOfPayment = modeOfPayment;
	}


	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("Report{");
		sb.append("Id='").append(getId()).append('\'');
		sb.append("creditCardNumber='").append(creditCardNumber).append('\'');
		sb.append(", orderDate=").append(orderDate);
		sb.append(", amount='").append(amount).append('\'');
		sb.append(", mccCode='").append(mccCode).append('\'');
		sb.append(", terminalId='").append(terminalId).append('\'');
		sb.append(", transactionReference='").append(transactionReference).append('\'');
		sb.append(", modeOfPayment='").append(modeOfPayment).append('\'');
		sb.append('}');
		return sb.toString();
	}
}