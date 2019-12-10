import Enums.scan_States;
import nz.ac.waikato.modeljunit.*;
import nz.ac.waikato.modeljunit.coverage.ActionCoverage;
import nz.ac.waikato.modeljunit.coverage.StateCoverage;
import nz.ac.waikato.modeljunit.coverage.TransitionPairCoverage;
import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pageObjects.scanPageObjects;

import java.io.FileNotFoundException;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class scanWebSystemModelTest implements FsmModel {

    private scanWebSystem sut;
    private scan_States modelState;
    private WebDriver webXDriver;


    private boolean loggingIn
            ,loggingOut
            ,addingItemToCart
            ,removingItemFromCart
            ,checkingOut;

    public Object getState() {
        return modelState;
    }

    public scanWebSystemModelTest(){
        System.setProperty("webdriver.chrome.driver","chromedriverMac");   // Tells the system where to find the chrome driver.
        webXDriver = new ChromeDriver();
        sut = new scanWebSystem(webXDriver);
    }

    public void reset(boolean b) {
        loggingIn = false;
        loggingOut = true;
        addingItemToCart = false;
        removingItemFromCart = false;
        checkingOut = false;

        modelState = scan_States.LOGGING_OUT;

        sut.logsOut();

        if (b) {
            sut = new scanWebSystem(webXDriver);
        }
    }

    public boolean loggingInGuard(){

        return (getState().equals(scan_States.LOGGING_OUT)
            || getState().equals(scan_States.ADDING_ITEM_TO_CART)
            || getState().equals(scan_States.CHECKING_OUT)
            || getState().equals(scan_States.REMOVING_ITEM_FROM_CART));
    }

    public @Action void loggingIn(){
        sut.logsIn();

        loggingIn = true;
        loggingOut = false;
        addingItemToCart = false;
        removingItemFromCart = false;
        checkingOut = false;
        modelState = scan_States.LOGGING_IN;

        assertEquals("LoggingIn", true, sut.isLoggingIn());
    }

    public boolean loggingOutGuard(){

        return (getState().equals(scan_States.LOGGING_IN)
            || getState().equals(scan_States.ADDING_ITEM_TO_CART)
            || getState().equals(scan_States.CHECKING_OUT)
            || getState().equals(scan_States.REMOVING_ITEM_FROM_CART));
    }

    public @Action void loggingOut(){
        sut.logsOut();

        loggingIn = false;
        loggingOut = true;
        addingItemToCart = false;
        removingItemFromCart = false;
        checkingOut = false;
        modelState = scan_States.LOGGING_OUT;

        assertEquals("LoggingOut", true, sut.isLoggingOut());
    }


    @After
    public void tearDown(){
        webXDriver.quit();
    }

    @Test
    public void webSystemModelRunner() {
        final Tester tester = new RandomTester(new scanWebSystemModelTest());
        tester.setRandom(new Random());
        final GraphListener graphListener = tester.buildGraph();
        tester.addListener(new StopOnFailureListener());
        tester.addListener("verbose");
        tester.addCoverageMetric(new TransitionPairCoverage());
        tester.addCoverageMetric(new StateCoverage());
        tester.addCoverageMetric(new ActionCoverage());
        tester.generate(5);
        tester.printCoverage();
    }
}