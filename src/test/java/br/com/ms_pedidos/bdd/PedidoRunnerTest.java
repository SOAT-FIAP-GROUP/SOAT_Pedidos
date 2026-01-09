package br.com.ms_pedidos.bdd;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"br.com.ms_pedidos"},
        plugin = {"pretty", "html:target/cucumber-reports/cucumber-html-report.html", "json:target/cucumber-reports/cucumber-json-report.json"},
        tags = "@Pedido",
        monochrome = true
)
public class PedidoRunnerTest {
}
