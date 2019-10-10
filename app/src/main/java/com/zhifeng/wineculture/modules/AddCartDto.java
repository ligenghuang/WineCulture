package com.zhifeng.wineculture.modules;

public class AddCartDto {

    /**
     * data : {"cart_id":3889,"inventory":65520}
     * msg : 成功！
     * status : 200
     */

    private DataBean data;
    private String msg;
    private int status;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class DataBean {
        /**
         * cart_id : 3889.0
         * inventory : 65520.0
         */

        private double cart_id;
        private double inventory;
        private double single_number;

        public double getSingle_number() {
            return single_number;
        }

        public void setSingle_number(double single_number) {
            this.single_number = single_number;
        }

        public double getCart_id() {
            return cart_id;
        }

        public void setCart_id(double cart_id) {
            this.cart_id = cart_id;
        }

        public double getInventory() {
            return inventory;
        }

        public void setInventory(double inventory) {
            this.inventory = inventory;
        }
    }
}
