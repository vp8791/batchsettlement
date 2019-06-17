package com;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
        format = { "pretty", "html:target/cucumber-html" , "json:target/cucumber-report-json.json"},
        glue = {"com.sky.content.shopping.shoppingitems.steps", "com.mykyong.bdd.steps",
                "com.eating.steps", "com.calculator.steps" }
)
public class RunAllCucumberTest {
}
