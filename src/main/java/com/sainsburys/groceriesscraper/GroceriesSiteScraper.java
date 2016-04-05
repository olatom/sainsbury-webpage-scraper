package com.sainsburys.groceriesscraper;

import java.io.IOException;

import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.*;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * This is the main business logic implementation class.
 * The scrape() method is responsible for:
 * 1. fetching the product page
 * 2. following links to all product pages
 * 3. grabbing information from each page and 
 * 4. producing a JSON output.
 * 
 * @author olat
 */
public class GroceriesSiteScraper {
    private URL url;
    private Connection con;
    
    public GroceriesSiteScraper(URL argUrl) {
        url = argUrl;
    }
    
    /**
     * Opens a HTTP(S) connection to the given URL argUrl, and returns a ProductVO object populated with scraped
     * information (title, size, unit price and product description).
     * 
     * @param argUrl A String object holding a page address.
     * @return ProductVO object with data, or null if something is obtainable.
     */
    public ProductVO getProductData(String argUrl) {
        String title = "";
        float size = 0.0f;
        float unitPrice = 0.0f;
        String description = "";
        
        try {
            Document doc = Jsoup.connect(argUrl).get();
            Element el = doc.select("div.productTitleDescriptionContainer").first();
            if (el == null) {
                return null;
            } else {
                // Let's get the product title
                Element titleElement = el.getElementsByTag("h1").first();
                title = titleElement.text();
                
                // size of the web-page (in kb)
                size = doc.toString().length() / 1024;
            }
            
            // let's get price per unit
            el = doc.select("p.pricePerUnit").first();
            if (el == null) {
                return null;
            } else {
                String ptxt = el.text();
                ptxt = ptxt.replace("/unit", "");
                ptxt = ptxt.replace("£", "");
                float ppunit = Float.parseFloat(ptxt);
                unitPrice = ppunit;
            }
            
            // Let's get the description.
            // NOTE: Its assumed that the description part comes always first...
            el = doc.select("div.productText").first();
            if (el == null) {
                return null;
            } else {
                description = el.text();
            }
        } catch (IOException ex) {
            Logger.getLogger(GroceriesSiteScraper.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return new ProductVO(title, size, unitPrice, description);
    }  
    
    /**
     * Starts the scraping process, and produces JSON output.
     * 
     * @return String containing the JSON code.
     */
    public String scrape() {
        JSONObject json = new JSONObject();
        JSONArray results = new JSONArray();
        json.put("results", results);
        float total = 0.0f; // total unit price.
        
        Connection con = Jsoup.connect(url.toString());
        if (con == null) {
            // If we can't connect, we return an empty JSON document.
            return "{}";
        }

        try {
            Element el = con.get().select("ul.productLister").first();
            if (el == null) {
                // There is no list of products so there is no need to continue...
                return "{}";
            }
            
            Elements els = el.getElementsByTag("li");
            for (Element element: els) {
                Element pinfoel = element.select("div.productInfo").first();
                Element linkel = pinfoel.getElementsByTag("a").first();
                
                // System.out.println(linkel.attr("abs:href")); // if we need absolute URL
                String infoUrl = linkel.attr("href");
                ProductVO pinfo = getProductData(infoUrl);
                
                // Add JSON representation of the ProductVO object to the array.
                results.add(pinfo.toJSON());
                total += pinfo.getUnitPrice();
            }
        } catch (IOException ex) {
            Logger.getLogger(GroceriesSiteScraper.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Set the total price /unit for all products.
        json.put("total", total);
        
        return json.toJSONString();
    }  
    
 
    public URL getUrl() {
        return url;
    }

    public void setUrl(URL argUrl) {
        url = argUrl;
    }
    
}  
