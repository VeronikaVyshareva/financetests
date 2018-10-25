package com.levi9.finance;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FinanceCurrencyExchangeTest {

    private static WebDriver driver;

    @Test
    @Ignore
    public void firstTest() {
        //        System.setProperty("webdriver.gecko.driver", "/Users/veronika/Tools/geckodriver");
        System.setProperty("webdriver.chrome.driver", "/Users/veronika/Tools/chromedriver");

// Create a new instance of the Chrome driver
// Notice that the remainder of the code relies on the interface,
// not the implementation.
        WebDriver driver = new ChromeDriver();

// And now use this to visit Google
        driver.get("http://www.google.com");
// Alternatively the same thing can be done like this
// driver.navigate().to("http://www.google.com");

// Find the text input element by its name
        WebElement element = driver.findElement(By.name("q"));

// Enter something to search for
        element.sendKeys("Cheese!");

// Now submit the form. WebDriver will find the form for us from the element
        element.submit();

// Check the title of the page
// Google's search is rendered dynamically with JavaScript.
// Wait for the page to load, timeout after 10 seconds
        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.getTitle().toLowerCase().startsWith("cheese!");
            }
        });

// Should see: "cheese! - Google Search"
        Assert.assertEquals("Cheese! - Пошук Google", driver.getTitle());
//Close the browser
        driver.quit();
    }

    @BeforeClass
    public static void before() {
        System.setProperty("webdriver.chrome.driver", "/Users/veronika/Tools/chromedriver");
        driver = new ChromeDriver();
        driver.get("https://finance.i.ua/");
    }

    @AfterClass
    public static void after() {
        driver.quit();
    }

    @Test
    public void usdPurchaseShouldBeGreaterThanSale() {

        WebElement usdPurchaseElement = driver.findElement(By.xpath("//div[@class='widget-currency_bank']//tr[th='USD']//td[1]//span[not(@class)]"));
        double usdPurchase = Double.parseDouble(usdPurchaseElement.getText());

        WebElement usdSaleElement = driver.findElement(By.xpath("//div[@class='widget-currency_bank']//tr[th='USD']//td[2]//span[not(@class)]"));
        double usdSale = Double.parseDouble(usdSaleElement.getText());

        System.out.println("USD Purchase = " + usdPurchase);
        System.out.println("USD Sale = " + usdSale);
        Assert.assertTrue("USD Purchase is greater than sale", usdPurchase < usdSale);

    }

    @Test
    public void eurPurchaseShouldBeGreaterThanSale() {

        WebElement eurPurchaseElement = driver.findElement(By.xpath("//div[@class='widget-currency_bank']//tr[th='EUR']//td[1]//span[not(@class)]"));
        double eurPurchase = Double.parseDouble(eurPurchaseElement.getText());

        WebElement eurSaleElement = driver.findElement(By.xpath("//div[@class='widget-currency_bank']//tr[th='EUR']//td[2]//span[not(@class)]"));
        double eurSale = Double.parseDouble(eurSaleElement.getText());

        System.out.println("EUR Purchase = " + eurPurchase);
        System.out.println("EUR Sale = " + eurSale);
        Assert.assertTrue("EUR Purchase is greater than sale", eurPurchase < eurSale);

    }

    @Test
    public void rubPurchaseShouldBeGreaterThanSale() {

        WebElement rubPurchaseElement = driver.findElement(By.xpath("//div[@class='widget-currency_bank']//tr[th='RUB']//td[1]//span[not(@class)]"));
        double rubPurchase = Double.parseDouble(rubPurchaseElement.getText());

        WebElement rubSaleElement = driver.findElement(By.xpath("//div[@class='widget-currency_bank']//tr[th='RUB']//td[2]//span[not(@class)]"));
        double rubSale = Double.parseDouble(rubSaleElement.getText());

        System.out.println("RUB Purchase = " + rubPurchase);
        System.out.println("RUB Sale = " + rubSale);
        Assert.assertTrue("RUB Purchase is greater than sale", rubPurchase < rubSale);

    }

    @Test
    public void checkCurrencyConverter() {
        WebElement currencyAmountInput = driver.findElement(By.xpath("//input[@id='currency_amount']"));
        currencyAmountInput.sendKeys("1000");

        WebElement converterCurrencyElement = driver.findElement(By.xpath("//select[@id='converter_currency']"));
        Select select = new Select(converterCurrencyElement);
        select.selectByValue("USD");

        WebElement usdPurchaseElement = driver.findElement(By.xpath("//div[@class='widget-currency_bank']//tr[th='USD']//td[1]//span[not(@class)]"));
        double usdPurchase = Double.parseDouble(usdPurchaseElement.getText());

        WebElement uahCurrencyExchangeElement = driver.findElement(By.xpath("//p[@id='UAH']/input[@id='currency_exchange']"));
        String uahExhangeValue = uahCurrencyExchangeElement.getAttribute("value").replaceAll(" ", "");
        double uahExchangeResult = Double.parseDouble(uahExhangeValue);

        System.out.println("1000USD*usdPurchse: " + 1000*usdPurchase);
        System.out.println("uahExhangeResult: " + uahExchangeResult);

        Assert.assertEquals(1000*usdPurchase, uahExchangeResult, 0.0001);
    }

}
