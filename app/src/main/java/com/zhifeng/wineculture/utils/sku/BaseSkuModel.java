package com.zhifeng.wineculture.utils.sku;

/**
 * description: Sku基本模型数据
 * autour: huang
 * date: 2019/9/19 21:27
 * update: 2019/9/19
 * version:
 */
public class BaseSkuModel {

    //base 属性
    private double price;//价格  我这没用到
    private double groupon_price;//原价
    private long stock;//库存
    private long virtual_sales;//销量
    private int sku_id;
    private String name;

    public BaseSkuModel(double price, double groupon_price, long stock, long virtual_sales, int sku_id, String name) {
        this.price = price;
        this.groupon_price = groupon_price;
        this.stock = stock;
        this.virtual_sales = virtual_sales;
        this.sku_id = sku_id;
        this.name = name;
    }

    public double getGroupon_price() {
        return groupon_price;
    }

    public void setGroupon_price(double groupon_price) {
        this.groupon_price = groupon_price;
    }

    public long getVirtual_sales() {
        return virtual_sales;
    }

    public void setVirtual_sales(long virtual_sales) {
        this.virtual_sales = virtual_sales;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getStock() {
        return stock;
    }

    public void setStock(long stock) {
        this.stock = stock;
    }

    public int getSku_id() {
        return sku_id;
    }

    public void setSku_id(int sku_id) {
        this.sku_id = sku_id;
    }
}
