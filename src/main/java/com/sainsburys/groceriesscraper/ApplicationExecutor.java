package com.sainsburys.groceriesscraper;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * This class executes the groceries site scraper
 * @author olat
 */
public class ApplicationExecutor {
    public static final void main(String[] args) {
        String urlStr = "http://hiring-tests.s3-website-eu-west-1.amazonaws.com/2015_Developer_Scrape/5_products.html";
        
        if (args.length == 1) {
            // If user enters a URL as a command line argument to the application.
            urlStr = args[0];
        }
        
        try {
            URL url = new URL(urlStr);
            GroceriesSiteScraper scraper = new GroceriesSiteScraper(url);
            System.out.println(scraper.scrape());
        } catch (MalformedURLException ex) {
            System.out.println("The url supplied :  '" + urlStr + "' is invalid .....");
        }
    }
}
