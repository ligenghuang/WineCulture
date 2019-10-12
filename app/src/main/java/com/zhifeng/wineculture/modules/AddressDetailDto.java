package com.zhifeng.wineculture.modules;

/**
  *
  * @ClassName:     收货地址详情实体类
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/9/16 10:12
  * @Version:        1.0
 */

public class AddressDetailDto {

    /**
     * status : 200
     * msg : success
     * data : {"address_id":1227,"user_id":27893,"consignee":"李","email":"","country":0,"province":28240,"city":28241,"district":28308,"twon":0,"address":"哈哈","zipcode":"","mobile":"15817046397","is_default":1,"longitude":"0.0000000","latitude":"0.0000000","poiaddress":null,"poiname":null,"districtcode":"440106","countryname":"中国","provincename":"广东省","cityname":"广州市","districtname":"天河区","twonname":null}
     */

    private int status;
    private String msg;
    private DataBean data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * address_id : 1227
         * user_id : 27893
         * consignee : 李
         * email :
         * country : 0
         * province : 28240
         * city : 28241
         * district : 28308
         * twon : 0
         * address : 哈哈
         * zipcode :
         * mobile : 15817046397
         * is_default : 1
         * longitude : 0.0000000
         * latitude : 0.0000000
         * poiaddress : null
         * poiname : null
         * districtcode : 440106
         * countryname : 中国
         * provincename : 广东省
         * cityname : 广州市
         * districtname : 天河区
         * twonname : null
         */

        private int address_id;
        private int user_id;
        private String consignee;
        private String email;
        private int country;
        private int province;
        private int city;
        private int district;
        private int twon;
        private String address;
        private String zipcode;
        private String mobile;
        private int is_default;
        private String longitude;
        private String latitude;
        private Object poiaddress;
        private Object poiname;
        private String districtcode;
        private String countryname;
        private String provincename;
        private String cityname;
        private String districtname;
        private Object twonname;


        public int getAddress_id() {
            return address_id;
        }

        public void setAddress_id(int address_id) {
            this.address_id = address_id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getConsignee() {
            return consignee == null ? "" : consignee;
        }

        public void setConsignee(String consignee) {
            this.consignee = consignee == null ? "" : consignee;
        }

        public String getEmail() {
            return email == null ? "" : email;
        }

        public void setEmail(String email) {
            this.email = email == null ? "" : email;
        }

        public int getCountry() {
            return country;
        }

        public void setCountry(int country) {
            this.country = country;
        }

        public int getProvince() {
            return province;
        }

        public void setProvince(int province) {
            this.province = province;
        }

        public int getCity() {
            return city;
        }

        public void setCity(int city) {
            this.city = city;
        }

        public int getDistrict() {
            return district;
        }

        public void setDistrict(int district) {
            this.district = district;
        }

        public int getTwon() {
            return twon;
        }

        public void setTwon(int twon) {
            this.twon = twon;
        }

        public String getAddress() {
            return address == null ? "" : address;
        }

        public void setAddress(String address) {
            this.address = address == null ? "" : address;
        }

        public String getZipcode() {
            return zipcode == null ? "" : zipcode;
        }

        public void setZipcode(String zipcode) {
            this.zipcode = zipcode == null ? "" : zipcode;
        }

        public String getMobile() {
            return mobile == null ? "" : mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile == null ? "" : mobile;
        }

        public int getIs_default() {
            return is_default;
        }

        public void setIs_default(int is_default) {
            this.is_default = is_default;
        }

        public String getLongitude() {
            return longitude == null ? "" : longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude == null ? "" : longitude;
        }

        public String getLatitude() {
            return latitude == null ? "" : latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude == null ? "" : latitude;
        }

        public Object getPoiaddress() {
            return poiaddress;
        }

        public void setPoiaddress(Object poiaddress) {
            this.poiaddress = poiaddress;
        }

        public Object getPoiname() {
            return poiname;
        }

        public void setPoiname(Object poiname) {
            this.poiname = poiname;
        }

        public String getDistrictcode() {
            return districtcode == null ? "" : districtcode;
        }

        public void setDistrictcode(String districtcode) {
            this.districtcode = districtcode == null ? "" : districtcode;
        }

        public String getCountryname() {
            return countryname == null ? "" : countryname;
        }

        public void setCountryname(String countryname) {
            this.countryname = countryname == null ? "" : countryname;
        }

        public String getProvincename() {
            return provincename == null ? "" : provincename;
        }

        public void setProvincename(String provincename) {
            this.provincename = provincename == null ? "" : provincename;
        }

        public String getCityname() {
            return cityname == null ? "" : cityname;
        }

        public void setCityname(String cityname) {
            this.cityname = cityname == null ? "" : cityname;
        }

        public String getDistrictname() {
            return districtname == null ? "" : districtname;
        }

        public void setDistrictname(String districtname) {
            this.districtname = districtname == null ? "" : districtname;
        }

        public Object getTwonname() {
            return twonname;
        }

        public void setTwonname(Object twonname) {
            this.twonname = twonname;
        }
    }
}
