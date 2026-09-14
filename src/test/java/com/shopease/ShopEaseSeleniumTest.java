package com.shopease;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ShopEaseSeleniumTest {

    private WebDriver driver;

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--window-size=1920,1080");

        driver = new ChromeDriver(options);
    }

    @Test
    void verifyShopEaseHomeAndProducts() {

        // Open ShopEase
        driver.get("http://localhost:8084");

        // Verify homepage
        assertTrue(
                driver.getTitle().contains("ShopEase")
                        || driver.getPageSource().contains("ShopEase"),
                "ShopEase homepage was not loaded"
        );

        // Open Products page
        driver.get("http://localhost:8084/products");

        // Verify Products page
        assertTrue(
                driver.getPageSource().contains("Products"),
                "Products page was not loaded"
        );

        // Verify a product is displayed
        assertTrue(
                driver.getPageSource().contains("Premium Laptop"),
                "Premium Laptop was not displayed"
        );
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
