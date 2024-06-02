package com.example.tugasfinal;

public class Favorite {
    private int productId;
    private String productTitle;
    private double productPrice;
    private String productDescription;
    private String productImage;
    private double productRate;
    private int productCount;
    private String productCategory;

    public Favorite(Product product){
        this.productId = product.getId();
        this.productTitle = product.getTitle();
        this.productPrice = product.getPrice();
        this.productDescription = product.getDescription();
        this.productImage = product.getImage();
        this.productRate = product.getRate();
        this.productCount = product.getCount();
        this.productCategory = product.getCategory();
    }

    public String getProductTitle(){return productTitle;}

    public double getProductPrice(){return productPrice;}
    public String getProductImage(){return productImage;}

    public Product toProduct(){
        Product product = new Product();
        product.setId(productId);
        product.setTitle(productTitle);
        product.setPrice(productPrice);
        product.setDescription(productDescription);
        product.setImage(productImage);
        product.setRate(productRate);
        product.setCount(productCount);
        product.setCategory(productCategory);

        return product;
    }

}
