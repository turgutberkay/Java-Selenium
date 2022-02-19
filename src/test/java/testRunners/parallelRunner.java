package testRunners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

public class parallelRunner {
    @CucumberOptions(
            features = {"src/test/resources/parallel"},
            glue = {"parallel"},
            plugin = {
                    "summary", "pretty", "html:Reports/CucumberReport/Reports.html",
                    "json:Reports/CucumberReport/Report",
                    "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
            }
    )
    public class ParallelRunner extends AbstractTestNGCucumberTests {
        @Override
        @DataProvider(parallel = true)
        public Object[][] scenarios() {
            return super.scenarios();
        }

    }
}
