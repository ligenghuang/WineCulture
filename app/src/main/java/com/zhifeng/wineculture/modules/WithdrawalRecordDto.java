package com.zhifeng.wineculture.modules;

import java.util.ArrayList;
import java.util.List;
/**
  *
  * @ClassName:     提现明细
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/10/14 10:19
  * @Version:        1.0
 */

public class WithdrawalRecordDto {

    /**
     * status : 200
     * msg : success
     * data : {"list":[{"createtime":1552480416,"money":"100.00","taxfee":"5.00","status":1},{"createtime":1552480803,"money":"100.00","taxfee":"5.00","status":1}]}
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
        private List<ListBean> list;

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
             * createtime : 1552480416
             * money : 100.00
             * taxfee : 5.00
             * status : 1
             */

            private long createtime;
            private String money;
            private String taxfee;
            private int status;

            public long getCreatetime() {
                return createtime;
            }

            public void setCreatetime(long createtime) {
                this.createtime = createtime;
            }

            public String getMoney() {
                return money == null ? "" : money;
            }

            public void setMoney(String money) {
                this.money = money == null ? "" : money;
            }

            public String getTaxfee() {
                return taxfee == null ? "" : taxfee;
            }

            public void setTaxfee(String taxfee) {
                this.taxfee = taxfee == null ? "" : taxfee;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }
        }
    }
}
