import org.openqa.selenium.WebDriver;
import pageObjects.scanPageObjects;

public class scanWebSystem {

    private scanPageObjects pageObjects;

    private final String PRODUCT_2_SEARCH = "apple";

    public scanWebSystem(WebDriver webXDriver){
        pageObjects = new scanPageObjects(webXDriver);
    }

    private boolean loggingIn = false,
            loggingOut = false,
            addingItemToCart = false,
            removingItemFromCart = false,
            checkingOut = false;

    public boolean isLoggingIn() {
        return loggingIn;
    }

    public boolean isLoggingOut() {
        return loggingOut;
    }


    public boolean isAddingItemToCart() {
        return addingItemToCart;
    }

    public boolean isRemovingItemFromCart() {
        return removingItemFromCart;
    }

    public boolean isCheckingOut() {
        return checkingOut;
    }

    public void logsIn(){
        loggingIn = true;
        loggingOut = false;
        addingItemToCart = false;
        removingItemFromCart = false;
        checkingOut = false;

        String EMAIL_VALID = "uomscanwebtesting@yopmail.com";
        String PASSWORD_VALID = "thisPassword1234";
        pageObjects.userLogsIn(EMAIL_VALID, PASSWORD_VALID);
    }

    public void logsOut(){
        loggingIn = false;
        loggingOut = true;
        addingItemToCart = false;
        removingItemFromCart = false;
        checkingOut = false;

        pageObjects.userLogsOut();
    }

    public void searchingProduct(){
        loggingIn = false;
        loggingOut = false;
        addingItemToCart = false;
        removingItemFromCart = false;
        checkingOut = false;

        pageObjects.userSearchesForProduct(PRODUCT_2_SEARCH);
    }

    public void addingProduct(){
        if(loggingIn || loggingOut){
            pageObjects.navigateToHome();
        }
        loggingIn = false;
        loggingOut = false;
        addingItemToCart = true;
        removingItemFromCart = false;
        checkingOut = false;

        //pageObjects.userSearchesForProduct(PRODUCT_2_SEARCH);
        pageObjects.userAddsItem();
    }

    public void removingProduct(){
        loggingIn = false;
        loggingOut = false;
        addingItemToCart = false;
        removingItemFromCart = true;
        checkingOut = false;

        pageObjects.userRemovesItem();
    }

    public void checkingOut(){
        loggingIn = false;
        loggingOut = false;
        addingItemToCart = false;
        removingItemFromCart = false;
        checkingOut = true;

        pageObjects.userChecksOut();
    }

    public int checkCartItems(){
        return pageObjects.checkCartItems();
    }

    public void quit(){
        pageObjects.quit();
    }
}
