package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
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

    public scanPageObjects(WebDriver webXDriver){
        actions = new Actions(webXDriver);
        this.webXDriver = webXDriver;
    }

    public void userLogsIn(String email, String password) {
        webXDriver.navigate().to(scanLocators.SCAN_LOGIN_URL);
        try{    // Try catch is used here as it was noted that when the webDriver access the login page, the driver is blocked by a modal.
            WebElement loginField = webXDriver.findElement(By.id(scanLocators.LOGIN_EMAIL_ID)); // Searches the page for a webElement with ID of the email entry field and saves it to a variable WebElement.
            WebElement passwordField = webXDriver.findElement(By.id(scanLocators.LOGIN_PASSWORD_ID)); // Searches the page for a webElement with ID of the password entry field and saves it to a variable WebElement.

            loginField.sendKeys(email); // Sends the webElement containing the login field, the email passed to the method.
            passwordField.sendKeys(password); // Sends the webElement containing the password field, the password passed to the method.

            webXDriver.findElement(By.id(scanLocators.LOGIN_SUBMIT_BUTTON)).click(); // Finds the element with ID of the submit button and clicks it.

        }catch (NoSuchElementException e){ //  If for some reason driver fails, (typically because a field can't be found due to being blocked by the modal)
            checkIfPopUpExists(); // Method which checks if the modal is there and closes it.
            userLogsIn(email,password); // Recursively call the login method again.
        }
    }

    private void checkIfPopUpExists(){ // The website in question has a popUp which appears on the login page, this can interfere with the webDriver.
        if(!webXDriver.findElements(By.id(scanLocators.MODAL_ID)).isEmpty()){ // Checks the page if there exist any elements with the ID of the popup.
            WebElement popupElement = webXDriver.findElement(By.id(scanLocators.MODAL_ID)); // Searches if a webElement with the modal ID is found, and saves to a variable webELement.
            popupElement.findElement(By.className(scanLocators.MODAL_CLOSE_CLASS)).click(); // Searches within the previously found webElement for an element with classname and clicks it, closing the modal.
        }
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

    public void quit(){
        webXDriver.quit();
    }


}
