package pageObjects;

public class scanLocators {
    /*
    This class contains a list of Strings which will be used to find various elements during testing,
    The different Strings are categorized by type, being URLS, IDS and Classes.
    Within each categorization there is further sorting, where Strings are sorted based on the page they are found in.
     */
    // URLS
    final String SCAN_HOME_URL = "https://www.scanmalta.com/newstore/";
    final String SCAN_LOGIN_URL = "https://www.scanmalta.com/newstore/customer/account/login/";
    final String SCAN_LOGOUT_URL = "https://www.scanmalta.com/newstore/customer/account/logout/";
    final String SCAN_SHOPPING_CART_URL = "https://www.scanmalta.com/newstore/checkout/cart/";
    final String SCAN_PRODUCT_DETAILS_URL = "https://www.scanmalta.com/newstore/microsoft-surface-pro-7-123-win10-pro-core-i3-128gb-ssd-4gb-platinum-tablet.html";
    final String SCAN_SEARCH_PRODUCT_URL = "https://www.scanmalta.com/newstore/catalogsearch/result/?q=apple";
    final String SCAN_CHECKOUT_URL = "https://www.scanmalta.com/newstore/checkout/onepage/";
    // IDS
    // LOGIN
    final String LOGIN_EMAIL_ID = "email";
    final String LOGIN_PASSWORD_ID = "pass";
    final String LOGIN_SUBMIT_BUTTON = "send2";
    final String MODAL_ID = "exitintent-popup";

    // SEARCH
    final String SEARCH_BAR_ID = "search";

    //PRODUCT DETAILS
    final String ADD_TO_CART_ID = "product-addtocart-button";

    // SHOPPING CART
    final String SHOPPING_CART_EMPTY_ID = "empty_cart_button";

    //CLASS
    //SEARCH
    final String PRODUICT_LI_SINGLE_CLASS = "product-image";
    final String PRODUCT_ADD_TO_CART_BUTTON_CLASS = "btn-cart";
    //PRODUCT PAGE
    final String PRODUCT_PAGE_CONTAINERR_CLASS = "product-essential";
    // MODAL
    final String MODAL_CLOSE_CLASS = "close-reveal-modal";
    // CART
    final String REMOVE_FROM_CART_BUTTON_CLASS = "btn-remove2";
}
