package com.csis3175.fleamart;

public class ItemDetail {

    private Long id;
    private String name;
    private String category;
    private String description;
    private Boolean forShare;
    private Double itemPrice;
    private String location;

    public ItemDetail(){

    }

    public ItemDetail(String n, String d, String c, Boolean fs, Double p, String l){
        name = n;
        description = d;
        category = c;
        forShare = fs;
        itemPrice = p;
        location = l;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getForShare() {
        return forShare;
    }

    public void setForShare(Boolean forShare) {
        this.forShare = forShare;
    }

    public Double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
