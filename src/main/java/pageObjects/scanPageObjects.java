package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class scanPageObjects {

    private WebDriver webXDriver;
    private scanLocators scanLocators = new scanLocators();
    private Actions actions;
    private Random random;

    public scanPageObjects(){
        System.setProperty("webdriver.chrome.driver","chromedriverMac");
        webXDriver = new ChromeDriver();
        actions = new Actions(webXDriver);
    }

    public void userLogsIn(String email, String password) {
        webXDriver.navigate().to(scanLocators.SCAN_LOGIN_URL);

        WebElement loginField = webXDriver.findElement(By.id(scanLocators.LOGIN_EMAIL_ID));
        WebElement passwordField = webXDriver.findElement(By.id(scanLocators.LOGIN_PASSWORD_ID));

        loginField.sendKeys(email);
        passwordField.sendKeys(password);

        webXDriver.findElement(By.id(scanLocators.LOGIN_SUBMIT_BUTTON)).click();
    }

    public void userLogsOut(){
        webXDriver.navigate().to(scanLocators.SCAN_LOGOUT_URL);
    }

    public void userSearchesForProduct(String product){
        WebElement searchInputField = webXDriver.findElement(By.id(scanLocators.SEARCH_BAR_ID));
        searchInputField.sendKeys(product);
        searchInputField.sendKeys(Keys.RETURN);
    }

    public void userAddsItem(){
        random = new Random();
        List<WebElement> webElementsAtag = getListOfAddToCartBtn();
        WebElement currencyTag = webElementsAtag.get(random.nextInt(25 - 1 + 1) + 1);
        actions.moveToElement(currencyTag);
        actions.perform();
        ((JavascriptExecutor) webXDriver)
                .executeScript("window.scrollBy(0, 250)", "");
        currencyTag.click();
    }

    private List<WebElement> getListOfAddToCartBtn(){
        return webXDriver.findElements(By.className(scanLocators.PRODUCT_ADD_TO_CART_BUTTON_CLASS));
    }

    public void userRemovesItem(){
        webXDriver.navigate().to(scanLocators.SCAN_SHOPPING_CART_URL);
        if(!webXDriver.getPageSource().contains("You have no items in your shopping cart.")){
            List<WebElement> webElementRemoveButton = getListOfRemoveFromCartButtons();
            WebElement removeButton = webElementRemoveButton.get(0);
            removeButton.click();
        }
    }

    public List<WebElement> getListOfRemoveFromCartButtons(){
        return webXDriver.findElements(By.className(scanLocators.REMOVE_FROM_CART_BUTTON_CLASS));
    }

    public void userChecksOut(){
        webXDriver.navigate().to(scanLocators.SCAN_CHECKOUT_URL);
    }



}
