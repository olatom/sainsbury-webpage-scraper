package com.sainsburys.groceriesscraper;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sainsburys.groceriesscraper.GroceriesSiteScraper;
import com.sainsburys.groceriesscraper.ProductVO;

import static org.junit.Assert.*;

/**
 *
 * @author olat
 */
public class GroceriesScraperTest {
    GroceriesSiteScraper gscraper;

    public GroceriesScraperTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        URL url;
        try {
            url = new URL("http://hiring-tests.s3-website-eu-west-1.amazonaws.com/2015_Developer_Scrape/5_products.html");
            gscraper = new GroceriesSiteScraper(url);
        } catch (MalformedURLException ex) {
            Logger.getLogger(GroceriesScraperTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @After
    public void tearDown() {
        gscraper = null;
    }

    @Test
    public void testScrapeNotEmpty() {
        String json = gscraper.scrape();
        assertTrue(!json.isEmpty());
    }

    @Test
    public void testScrapeHasTotalAndResults() {
        String json = gscraper.scrape();
        assertTrue(json.contains("total") && json.contains("results"));
    }

    @Test
    public void testGetProductReturnsNull() {
        String adr = "http://www.apache.org/";
        ProductVO product = gscraper.getProductData(adr);
        assertNull(product);
    }

    @Test
    public void testGetProductReturnsObject() {
        String adr = "http://hiring-tests.s3-website-eu-west-1.amazonaws.com/2015_Developer_Scrape/sainsburys-avocado-xl-pinkerton-loose-300g.html";
        ProductVO product = gscraper.getProductData(adr);
        
        assertNotNull(product);
        assertNotNull(product.getTitle());
        assertTrue(product.getSize() > 0);
        assertTrue(product.getUnitPrice() == 1.5f);
        assertNotNull(product.getDescription());
        assertTrue(product.getDescription().length() > 0);
    }

    @Test
    public void testScrapeReturnsProduct() {
        String json = gscraper.scrape();
        assertTrue(json.contains("Avocado"));
    }

}
