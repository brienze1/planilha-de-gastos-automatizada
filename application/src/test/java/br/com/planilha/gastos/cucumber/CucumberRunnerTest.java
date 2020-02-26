package br.com.planilha.gastos.cucumber;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = "src/test/resources/features", 
		glue = {"br.com.planilha.gastos.cucumber.steps.busca", "br.com.planilha.gastos.cucumber.steps.cadastro"},
		plugin = "pretty")
public class CucumberRunnerTest {

}
