import Enums.scan_States;
import nz.ac.waikato.modeljunit.*;
import nz.ac.waikato.modeljunit.coverage.ActionCoverage;
import nz.ac.waikato.modeljunit.coverage.StateCoverage;
import nz.ac.waikato.modeljunit.coverage.TransitionPairCoverage;
import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
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
    private boolean canCheckOut,loggedIn;

    public Object getState() {
        return modelState;
    }

    public scanWebSystemModelTest(){
//        System.setProperty("webdriver.chrome.driver","chromedriverMac");   // Tells the system where to find the chrome driver.
        System.setProperty("webdriver.chrome.driver","chromedriver.exe");   // Tells the system where to find the chrome driver.
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-fullscreen");
        webXDriver = new ChromeDriver(options);
        sut = new scanWebSystem(webXDriver);
    }

    public void reset(boolean b) {
        loggingIn = false;
        loggingOut = true;
        addingItemToCart = false;
        removingItemFromCart = false;
        checkingOut = false;
        canCheckOut = false;
        loggedIn = false;

        modelState = scan_States.LOGGING_OUT;

        sut.logsOut();

        if (b) {
            sut = new scanWebSystem(webXDriver);
        }
    }

    public boolean loggingInGuard(){

        return (!loggedIn) && (getState().equals(scan_States.LOGGING_OUT)
            || getState().equals(scan_States.ADDING_ITEM_TO_CART)
//            || getState().equals(scan_States.CHECKING_OUT)
            || getState().equals(scan_States.REMOVING_ITEM_FROM_CART));
    }

    public @Action void loggingIn(){
        sut.logsIn();

        loggingIn = true;
        loggedIn = true;
        loggingOut = false;
        addingItemToCart = false;
        removingItemFromCart = false;
        checkingOut = false;
        modelState = scan_States.LOGGING_IN;

        assertEquals("LoggingIn", true, sut.isLoggingIn());
    }

    public boolean loggingOutGuard(){

        return (loggedIn) && (getState().equals(scan_States.LOGGING_IN)
            || getState().equals(scan_States.ADDING_ITEM_TO_CART)
//            || getState().equals(scan_States.CHECKING_OUT)
            || getState().equals(scan_States.REMOVING_ITEM_FROM_CART));
    }

    public @Action void loggingOut(){
        sut.logsOut();

        loggingIn = false;
        loggedIn = false;
        loggingOut = true;
        addingItemToCart = false;
        removingItemFromCart = false;
        checkingOut = false;
        modelState = scan_States.LOGGING_OUT;

        assertEquals("LoggingOut", true, sut.isLoggingOut());
    }

    public boolean addProductGuard(){
        return (getState().equals(scan_States.LOGGING_IN)
                || getState().equals(scan_States.LOGGING_OUT)
//                || getState().equals(scan_States.CHECKING_OUT)
                || getState().equals(scan_States.REMOVING_ITEM_FROM_CART));
    }

    public @Action void addProduct(){
        sut.addingProduct();

        loggingIn = false;
        loggingOut = false;
        addingItemToCart = true;
        removingItemFromCart = false;
        checkingOut = false;

        canCheckOut = true;

        modelState = scan_States.ADDING_ITEM_TO_CART;

        assertEquals("Adding Product", true, sut.isAddingItemToCart());
    }

    public boolean removeProductGuard(){
        return (getState().equals(scan_States.LOGGING_IN)
                || getState().equals(scan_States.LOGGING_OUT)
//                || getState().equals(scan_States.CHECKING_OUT)
                || getState().equals(scan_States.REMOVING_ITEM_FROM_CART)
                || getState().equals(scan_States.ADDING_ITEM_TO_CART));
    }

    public @Action void removeProduct(){
        if(sut.checkCartItems() > 0){
            sut.removingProduct();

            loggingIn = false;
            loggingOut = false;
            addingItemToCart = false;
            removingItemFromCart = true;
            checkingOut = false;
            modelState = scan_States.REMOVING_ITEM_FROM_CART;
            canCheckOut = sut.checkCartItems() > 0;

            assertEquals("Removing Product", true, sut.isRemovingItemFromCart());
        }else {
            canCheckOut = false;
        }

    }

    public boolean checkoutGuard(){
        return ((getState().equals(scan_States.LOGGING_IN)
                || getState().equals(scan_States.LOGGING_OUT)
                || getState().equals(scan_States.REMOVING_ITEM_FROM_CART)
                || getState().equals(scan_States.ADDING_ITEM_TO_CART))
                && canCheckOut);
    }

    public @Action void checkout(){
        sut.checkingOut();

        loggingIn = false;
        loggingOut = false;
        addingItemToCart = false;
        removingItemFromCart = false;
        checkingOut = true;
        modelState = scan_States.CHECKING_OUT;

        assertEquals("Removing Product", true, sut.isCheckingOut());
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
        tester.generate(250);
        tester.printCoverage();
    }
}