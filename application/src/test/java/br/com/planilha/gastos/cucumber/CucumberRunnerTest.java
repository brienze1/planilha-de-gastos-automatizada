package br.com.planilha.gastos.cucumber;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = "src/test/resources/features", 
		tags = {"@RegistrarNovoUsuarioTeste or @LoginTeste or @AutoLoginTeste"},
		plugin = "pretty")
public class CucumberRunnerTest {

}
