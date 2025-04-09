package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterStepdefs {

    WebDriver driver;

    @After
    public void tearDown()
    {
        driver.close();
    }

    @Given("That I am on the correct start page on {string}")
    public void thatIAmOnTheCorrectStartPage(String browser) {
        switch (browser.toLowerCase())
        {
            case "chrome":
                driver = new ChromeDriver();
                break;
            case "firefox":
                driver = new FirefoxDriver();
                break;
            case "edge":
                driver = new EdgeDriver();
                break;
            default:
                System.out.println("faulty name for browser picked");
                fail();

        }
        driver.get("https://membership.basketballengland.co.uk/NewSupporterAccount");
        assertEquals("CREATE AN ACCOUNT", driver.findElement(By.cssSelector("#titleText1")).getText());
    }

    @When("I fill in DOB as {string}")
    public void iFillInMyDOBAs(String DOB) {
        driver.findElement(By.cssSelector("#dp")).sendKeys(DOB);
    }

    @And("I fill in name as {string}")
    public void iFillInMyNameAs(String name) {
        if(name.contains(" ")){
            FillInFirstName(name.substring(0,name.indexOf(' ')));
            FillInLastName(name.substring(name.indexOf(' ')));
            return;
        }
        FillInFirstName(name);
    }

    private void FillInFirstName(String name){
        driver.findElement(By.cssSelector("#member_firstname")).sendKeys(name);
    }
    private void FillInLastName(String name){
        driver.findElement(By.cssSelector("#member_lastname")).sendKeys(name);
    }

    @And("I fill in emails as {string}")
    public void iFillInMyEmailsAs(String email) {
        String tmp = email.substring(0,email.indexOf('@'))+System.currentTimeMillis()+email.substring(email.indexOf('@'));

        driver.findElement(By.cssSelector("#member_emailaddress")).sendKeys(tmp);
        driver.findElement(By.cssSelector("#member_confirmemailaddress")).sendKeys(tmp);
    }


    @And("I fill in password as {string}")
    public void iFillInMyPasswordAs(String password) {

        driver.findElement(By.cssSelector("#signupunlicenced_password")).sendKeys(password);
        driver.findElement(By.cssSelector("#signupunlicenced_confirmpassword")).sendKeys(password);
    }

    @And("I fill in mismatching password as {string}")
    public void iFillInMyMismatchingPasswordAs(String password) {

        driver.findElement(By.cssSelector("#signupunlicenced_password")).sendKeys(password);
        driver.findElement(By.cssSelector("#signupunlicenced_confirmpassword")).sendKeys(password.repeat(2));
    }

    @And("I fill in terms&conditions as {string}")
    public void iFillInTermsConditionsAsClicked(String s) {
        if(s.equalsIgnoreCase("clicked"))
        {
            driver.findElement(By.cssSelector("label[for='sign_up_25']")).click();
            driver.findElement(By.cssSelector("label[for='sign_up_26']")).click();
            driver.findElement(By.cssSelector("label[for*='fanmembers']")).click();
            return;
        }
        driver.findElement(By.cssSelector("label[for='sign_up_26']")).click();
        driver.findElement(By.cssSelector("label[for*='fanmembers']")).click();
    }

    @Then("I Submit and verify")
    public void iSubmitAndVerify() {
        driver.findElement(By.cssSelector("input[name='join']")).click();
        waitFor(driver, By.cssSelector("a[href*='MembershipNumber']"));
        assertEquals("THANK YOU FOR CREATING AN ACCOUNT WITH BASKETBALL ENGLAND",
                driver.findElement(By.cssSelector("h2")).getText());
    }
    @Then("I Submit and verify missing last name")
    public void iSubmitAndVerifyMissingLastName() {
        driver.findElement(By.cssSelector("input[name='join']")).click();
        waitFor(driver,By.cssSelector("span[for='member_lastname']"));
        assertEquals("Last Name is required",
                driver.findElement(By.cssSelector("span[for='member_lastname']")).getText());
    }

    @Then("I Submit and verify passwords not matching")
    public void iSubmitAndVerifyPasswordsMismatch() {
        driver.findElement(By.cssSelector("input[name='join']")).click();
        waitFor(driver,By.cssSelector("span[for='signupunlicenced_confirmpassword']"));
        assertEquals("Password did not match",
                driver.findElement(By.cssSelector("span[for='signupunlicenced_confirmpassword']")).getText());
    }

    @Then("I Submit and verify terms&conditions not accepted")
    public void iSubmitAndVerifyTermsConditionsNotAccepted() {
        driver.findElement(By.cssSelector("input[name='join']")).click();
        waitFor(driver,By.cssSelector("span[for='TermsAccept']"));
        assertEquals("You must confirm that you have read and accepted our Terms and Conditions",
                driver.findElement(By.cssSelector("span[for='TermsAccept']")).getText());
    }

    private static void waitFor(WebDriver driver, By by){
        WebElement element = (new WebDriverWait(driver, Duration.ofSeconds(10))).until(ExpectedConditions.presenceOfElementLocated(by));
    }

}
