package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
class CreateProductFunctionalTest {

    @LocalServerPort
    private int serverPort;

    @Value("${app.baseUrl:http://localhost}")
    private String baseUrl;

    private String url;

    @BeforeEach
    void setupTest() {
        url = String.format("%s:%d", baseUrl, serverPort);
    }

    @Test
    void createProduct_isSuccessful(ChromeDriver driver) throws Exception {
        // 1. Open the Homepage and navigate to Product List
        driver.get(url);
        driver.findElement(By.linkText("Go to Product List")).click();

        // 2. Click the 'Create Product' button
        driver.findElement(By.linkText("Create Product")).click();

        // 3. Fill in the product details
        // Note: Ensure your HTML has id="nameInput" and id="quantityInput"
        driver.findElement(By.id("nameInput")).sendKeys("Sampo Cap Bambang");
        driver.findElement(By.id("quantityInput")).clear();
        driver.findElement(By.id("quantityInput")).sendKeys("10");

        // 4. Submit the form
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // 5. Verify we are back at the List page
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/product/list"));

        // 6. Verify the new product exists in the table
        List<WebElement> tableCells = driver.findElements(By.tagName("td"));
        boolean isProductFound = tableCells.stream()
                .anyMatch(cell -> cell.getText().equals("Sampo Cap Bambang"));

        assertTrue(isProductFound, "The created product should be visible in the list table.");
    }
}