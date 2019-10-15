package com.zhifeng.wineculture.modules;

import java.util.ArrayList;
import java.util.List;
/**
  *
  * @ClassName:     搜索历史
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/10/15 16:23
  * @Version:        1.0
 */

public class SearchGoodsHistoryDto {


    /**
     * status : 1
     * msg : 获取数据成功
     * data : [{"id":32,"keyword":"15817046397"}]
     */

    private int status;
    private String msg;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        if (data == null) {
            return new ArrayList<>();
        }
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 32
         * keyword : 15817046397
         */

        private int id;
        private String keyword;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getKeyword() {
            return keyword == null ? "" : keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword == null ? "" : keyword;
        }
    }
}
