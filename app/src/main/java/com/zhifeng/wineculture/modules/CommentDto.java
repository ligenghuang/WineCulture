package com.zhifeng.wineculture.modules;

import java.util.List;

public class CommentDto {
    private String order_id;
    private String goods_id;
    private String sku_id;
    private String content;
    private List<String> img;

    public CommentDto(String order_id, String goods_id, String sku_id, String content, List<String> img) {
        this.order_id = order_id;
        this.goods_id = goods_id;
        this.sku_id = sku_id;
        this.content = content;
        this.img = img;
    }

    public String getOrder_id() {
        return order_id == null ? "" : order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id == null ? "" : order_id;
    }

    public String getGoods_id() {
        return goods_id == null ? "" : goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id == null ? "" : goods_id;
    }

    public String getSku_id() {
        return sku_id == null ? "" : sku_id;
    }

    public void setSku_id(String sku_id) {
        this.sku_id = sku_id == null ? "" : sku_id;
    }

    public String getContent() {
        return content == null ? "" : content;
    }

    public void setContent(String content) {
        this.content = content == null ? "" : content;
    }

    public List<String> getImg() {
        return img;
    }

    public void setImg(List<String> img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "{" +
                "\"order_id\":\'" + order_id + "\'" +
                ", \"goods_id\":\'" + goods_id + "\'" +
                ", \"sku_id\":\'" + sku_id + "\'" +
                ", \"content\":\'" + content + "\'" +
                ", \"img\":" + img +
                '}';
    }
}
