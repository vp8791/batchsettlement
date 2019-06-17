package com.mykyong.bdd.steps;

import com.mkyong.model.Report;
import com.mykyong.TestValidatorUtils;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertTrue;

//  mvn clean install  -Dcucumber.options=/C/github/Testing/Features/Batch
// >mvn clean install  -Dcucumber.options=D:\Vanitha\SourceCode\Batch\Batch\featureFiles\Amount.feature
@RunWith(Cucumber.class)
public class AmountValidatorStep {

	private TestValidatorUtils testValidatorUrils;
	private Report report;

	@Before
	private void init() {
	}

	@Given("^I have  a  amount which is valid or invalid$")
	public void i_have_a_orderdate_which_is_valid_or_invalid() throws Throwable {
		testValidatorUrils = new TestValidatorUtils();
	}

	@When("^I validate amount (.+)$")
	public void i_validate_order_date(String amount) throws Throwable {
		report = testValidatorUrils.getValidReport();
		report.setAmount(amount);
	}


	@Then("^the Amount validation result should be should be (.+)$")
	public void the_validation_result_should_be_should_be(String validorinvalid) throws Throwable {
		Boolean isValid = Boolean.valueOf(validorinvalid);
		List<String> validateObjectResult = testValidatorUrils.getBeanValidator().validateObject(report);
		System.out.println("==================" + validateObjectResult.isEmpty());
		if (isValid) {
			assertTrue((validateObjectResult.size() == 0));
		} else {
			assertTrue((validateObjectResult.size() != 0));
		}
	}

}

