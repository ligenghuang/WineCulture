package com.zhifeng.wineculture.modules;

import java.util.ArrayList;
import java.util.List;

public class LogisticsDto {


    /**
     * status : 200
     * msg : 获取成功
     * data : {"status":"0","msg":"ok","result":{"number":"3835530862184","type":"YUNDA","list":[{"time":"2019-04-06 13:21:52","status":"【淄博市】快件已被 本人 签收。如有问题请电联业务员：张林【18853317980】。相逢是缘,如果您对我的服务感到满意,给个五星好不好？【请在评价小件员处给予五星好评】"},{"time":"2019-04-06 10:54:25","status":"【淄博市】山东淄博公司桓台县分部 派件员 张林 18853317980 正在为您派件"},{"time":"2019-04-06 09:57:25","status":"【淄博市】已到达 山东淄博公司桓台县分部 ；马上为您派送"},{"time":"2019-04-06 08:58:13","status":"【淄博市】已离开 山东淄博公司；发往 山东淄博公司桓台县分部"},{"time":"2019-04-05 10:56:56","status":"【济南市】已离开 山东济南分拨中心；发往 山东淄博公司"},{"time":"2019-04-05 10:56:32","status":"【济南市】已到达 山东济南分拨中心"},{"time":"2019-04-05 10:51:02","status":"【济南市】已到达 山东济南分拨中心"},{"time":"2019-04-04 04:15:19","status":"【广州市】已离开 广东广州分拨中心；发往 山东济南分拨中心"},{"time":"2019-04-04 04:12:38","status":"【广州市】已到达 广东广州分拨中心"},{"time":"2019-04-04 01:52:30","status":"【广州市】已离开 广州白云区平沙公司；发往 泰德淄市内包"},{"time":"2019-04-04 01:36:56","status":"【广州市】广州白云区平沙公司 已揽收"}],"deliverystatus":"3","issign":"1","expName":"韵达快递","expSite":"www.yundaex.com","expPhone":"95546","logo":"http://img3.fegine.com/express/yd.jpg","courier":"","courierPhone":"18853317980"}}
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
         * status : 0
         * msg : ok
         * result : {"number":"3835530862184","type":"YUNDA","list":[{"time":"2019-04-06 13:21:52","status":"【淄博市】快件已被 本人 签收。如有问题请电联业务员：张林【18853317980】。相逢是缘,如果您对我的服务感到满意,给个五星好不好？【请在评价小件员处给予五星好评】"},{"time":"2019-04-06 10:54:25","status":"【淄博市】山东淄博公司桓台县分部 派件员 张林 18853317980 正在为您派件"},{"time":"2019-04-06 09:57:25","status":"【淄博市】已到达 山东淄博公司桓台县分部 ；马上为您派送"},{"time":"2019-04-06 08:58:13","status":"【淄博市】已离开 山东淄博公司；发往 山东淄博公司桓台县分部"},{"time":"2019-04-05 10:56:56","status":"【济南市】已离开 山东济南分拨中心；发往 山东淄博公司"},{"time":"2019-04-05 10:56:32","status":"【济南市】已到达 山东济南分拨中心"},{"time":"2019-04-05 10:51:02","status":"【济南市】已到达 山东济南分拨中心"},{"time":"2019-04-04 04:15:19","status":"【广州市】已离开 广东广州分拨中心；发往 山东济南分拨中心"},{"time":"2019-04-04 04:12:38","status":"【广州市】已到达 广东广州分拨中心"},{"time":"2019-04-04 01:52:30","status":"【广州市】已离开 广州白云区平沙公司；发往 泰德淄市内包"},{"time":"2019-04-04 01:36:56","status":"【广州市】广州白云区平沙公司 已揽收"}],"deliverystatus":"3","issign":"1","expName":"韵达快递","expSite":"www.yundaex.com","expPhone":"95546","logo":"http://img3.fegine.com/express/yd.jpg","courier":"","courierPhone":"18853317980"}
         */

        private String status;
        private String msg;
        private ResultBean result;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public ResultBean getResult() {
            return result;
        }

        public void setResult(ResultBean result) {
            this.result = result;
        }

        public static class ResultBean {
            /**
             * number : 3835530862184
             * type : YUNDA
             * list : [{"time":"2019-04-06 13:21:52","status":"【淄博市】快件已被 本人 签收。如有问题请电联业务员：张林【18853317980】。相逢是缘,如果您对我的服务感到满意,给个五星好不好？【请在评价小件员处给予五星好评】"},{"time":"2019-04-06 10:54:25","status":"【淄博市】山东淄博公司桓台县分部 派件员 张林 18853317980 正在为您派件"},{"time":"2019-04-06 09:57:25","status":"【淄博市】已到达 山东淄博公司桓台县分部 ；马上为您派送"},{"time":"2019-04-06 08:58:13","status":"【淄博市】已离开 山东淄博公司；发往 山东淄博公司桓台县分部"},{"time":"2019-04-05 10:56:56","status":"【济南市】已离开 山东济南分拨中心；发往 山东淄博公司"},{"time":"2019-04-05 10:56:32","status":"【济南市】已到达 山东济南分拨中心"},{"time":"2019-04-05 10:51:02","status":"【济南市】已到达 山东济南分拨中心"},{"time":"2019-04-04 04:15:19","status":"【广州市】已离开 广东广州分拨中心；发往 山东济南分拨中心"},{"time":"2019-04-04 04:12:38","status":"【广州市】已到达 广东广州分拨中心"},{"time":"2019-04-04 01:52:30","status":"【广州市】已离开 广州白云区平沙公司；发往 泰德淄市内包"},{"time":"2019-04-04 01:36:56","status":"【广州市】广州白云区平沙公司 已揽收"}]
             * deliverystatus : 3
             * issign : 1
             * expName : 韵达快递
             * expSite : www.yundaex.com
             * expPhone : 95546
             * logo : http://img3.fegine.com/express/yd.jpg
             * courier :
             * courierPhone : 18853317980
             */

            private String number;
            private String type;
            private String deliverystatus;
            private String issign;
            private String expName;
            private String expSite;
            private String expPhone;
            private String logo;
            private String courier;
            private String courierPhone;
            private List<ListBean> list;
            private String address;

            public String getAddress() {
                return address == null ? "" : address;
            }

            public void setAddress(String address) {
                this.address = address == null ? "" : address;
            }

            public String getNumber() {
                return number == null ? "" : number;
            }

            public void setNumber(String number) {
                this.number = number == null ? "" : number;
            }

            public String getType() {
                return type == null ? "" : type;
            }

            public void setType(String type) {
                this.type = type == null ? "" : type;
            }

            public String getDeliverystatus() {
                return deliverystatus == null ? "" : deliverystatus;
            }

            public void setDeliverystatus(String deliverystatus) {
                this.deliverystatus = deliverystatus == null ? "" : deliverystatus;
            }

            public String getIssign() {
                return issign == null ? "" : issign;
            }

            public void setIssign(String issign) {
                this.issign = issign == null ? "" : issign;
            }

            public String getExpName() {
                return expName == null ? "" : expName;
            }

            public void setExpName(String expName) {
                this.expName = expName == null ? "" : expName;
            }

            public String getExpSite() {
                return expSite == null ? "" : expSite;
            }

            public void setExpSite(String expSite) {
                this.expSite = expSite == null ? "" : expSite;
            }

            public String getExpPhone() {
                return expPhone == null ? "" : expPhone;
            }

            public void setExpPhone(String expPhone) {
                this.expPhone = expPhone == null ? "" : expPhone;
            }

            public String getLogo() {
                return logo == null ? "" : logo;
            }

            public void setLogo(String logo) {
                this.logo = logo == null ? "" : logo;
            }

            public String getCourier() {
                return courier == null ? "" : courier;
            }

            public void setCourier(String courier) {
                this.courier = courier == null ? "" : courier;
            }

            public String getCourierPhone() {
                return courierPhone == null ? "" : courierPhone;
            }

            public void setCourierPhone(String courierPhone) {
                this.courierPhone = courierPhone == null ? "" : courierPhone;
            }

            public List<ListBean> getList() {
                if (list == null) {
                    return new ArrayList<>();
                }
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public static class ListBean {
                /**
                 * time : 2019-04-06 13:21:52
                 * status : 【淄博市】快件已被 本人 签收。如有问题请电联业务员：张林【18853317980】。相逢是缘,如果您对我的服务感到满意,给个五星好不好？【请在评价小件员处给予五星好评】
                 */

                private String time;
                private String status;

                public ListBean(String time, String status) {
                    this.time = time;
                    this.status = status;
                }

                public String getTime() {
                    return time == null ? "" : time;
                }

                public void setTime(String time) {
                    this.time = time == null ? "" : time;
                }

                public String getStatus() {
                    return status == null ? "" : status;
                }

                public void setStatus(String status) {
                    this.status = status == null ? "" : status;
                }
            }
        }
    }
}
