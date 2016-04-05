package com.sainsburys.groceriesscraper;

import org.json.simple.JSONObject;

/**
 * A simple value object to hold the data to be scrapped  .
 * 
 * @author olat
 */
public class ProductVO {
    private String title;
    private float size;
    private float unitPrice;
    private String description;

    public ProductVO(String title, float size, float unitPrice, String description) {
        this.title = title;
        this.size = size;
        this.unitPrice = unitPrice;
        this.description = description;
    }
    
    /**
     * Use this method to get JSONObject out of the ProductVO object.
     * 
     * @return JSONObject containing all the fields of the ProductVO object.
     */
    public JSONObject toJSON() {
        JSONObject ret = new JSONObject();
        ret.put("title", title);
        ret.put("size", size);
        ret.put("unit_price", unitPrice);
        ret.put("description", description);
        
        return ret;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ProductVO{" + "title=" + title + ", size=" + size + ", unitPrice=" + unitPrice + ", description=" + description + '}';
    }
    
}
