package com.zhifeng.wineculture.utils.sku;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description:  sku数据存储Model类
 * autour: huang
 * date: 2019/9/19 21:30
 * update: 2019/9/19
 * version:
 */
public class ProductModel {

    //存储所有录入的库存情况
    private Map<String, BaseSkuModel> productStocks = new HashMap<>();

    //记录规格的种类
    private List<AttributesEntity> attributes = new ArrayList<>();

    public Map<String, BaseSkuModel> getProductStocks() {
        return productStocks;
    }

    public void setProductStocks(Map<String, BaseSkuModel> productStocks) {
        this.productStocks = productStocks;
    }


    public List<AttributesEntity> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<AttributesEntity> attributes) {
        this.attributes = attributes;
    }

    public static class AttributesEntity {

        private int id;
        private String name;
        private List<AttributeMembersEntity> attributeMembers = new ArrayList<>();

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<AttributeMembersEntity> getAttributeMembers() {
            return attributeMembers;
        }

        public void setAttributeMembers(List<AttributeMembersEntity> attributeMembers) {
            this.attributeMembers = attributeMembers;
        }

        public static class AttributeMembersEntity {

            private String Sku_attr1;
            private int attributeGroupId;//组ID
            private int attributeMemberId;//属性ID
            private String name;//属性名
            private String price;//按钮上的价格
            private int status;
            private boolean isClick;

            public boolean isClick() {
                return isClick;
            }

            public void setClick(boolean click) {
                isClick = click;
            }

            public String getSku_attr1() {
                return Sku_attr1 == null ? "" : Sku_attr1;
            }

            public void setSku_attr1(String sku_attr1) {
                Sku_attr1 = sku_attr1;
            }

            public AttributeMembersEntity(int attributeGroupId, int attributeMemberId, String name, String price, String Sku_attr1) {
                this.attributeGroupId = attributeGroupId;
                this.attributeMemberId = attributeMemberId;
                this.price = price;
                this.name = name;
                this.Sku_attr1 = Sku_attr1;
            }

            @Override
            public boolean equals(Object obj) {
                if (!(obj instanceof AttributeMembersEntity))
                    return false;
                AttributeMembersEntity entity = (AttributeMembersEntity) obj;
                return entity.getName().equals(name)
                        && entity.getPrice() == price
                        && entity.getAttributeGroupId() == attributeGroupId
                        && entity.getAttributeMemberId() == attributeMemberId;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getAttributeGroupId() {
                return attributeGroupId;
            }

            public void setAttributeGroupId(int attributeGroupId) {
                this.attributeGroupId = attributeGroupId;
            }

            public int getAttributeMemberId() {
                return attributeMemberId;
            }

            public void setAttributeMemberId(int attributeMemberId) {
                this.attributeMemberId = attributeMemberId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }
        }
    }

}
