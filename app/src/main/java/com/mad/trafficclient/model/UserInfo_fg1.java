package com.mad.trafficclient.model;

import java.util.List;

/**
 * Created by Administrator on 2017/6/2.
 */

public class UserInfo_fg1 {

    /**
     * ERRMSG : 成功
     * ROWS_DETAIL : [{"username":"user1","pname":"王生安","pcardid":"370101196101011001","psex":"男","ptel":"13804110001","pregisterdate":"1990-5-21 02:19:21"},{"username":"user285","pname":"涂俱珍","pcardid":"370105199509051285","psex":"女","ptel":"13804110285","pregisterdate":"1991-3-01 02:19:21"}]
     * RESULT : S
     */

    private String ERRMSG;
    private String RESULT;
    private List<ROWSDETAILBean> ROWS_DETAIL;

    public String getERRMSG() {
        return ERRMSG;
    }

    public void setERRMSG(String ERRMSG) {
        this.ERRMSG = ERRMSG;
    }

    public String getRESULT() {
        return RESULT;
    }

    public void setRESULT(String RESULT) {
        this.RESULT = RESULT;
    }

    public List<ROWSDETAILBean> getROWS_DETAIL() {
        return ROWS_DETAIL;
    }

    public void setROWS_DETAIL(List<ROWSDETAILBean> ROWS_DETAIL) {
        this.ROWS_DETAIL = ROWS_DETAIL;
    }

    public static class ROWSDETAILBean {
        /**
         * username : user1
         * pname : 王生安
         * pcardid : 370101196101011001
         * psex : 男
         * ptel : 13804110001
         * pregisterdate : 1990-5-21 02:19:21
         */

        private String username;
        private String pname;
        private String pcardid;
        private String psex;
        private String ptel;
        private String pregisterdate;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPname() {
            return pname;
        }

        public void setPname(String pname) {
            this.pname = pname;
        }

        public String getPcardid() {
            return pcardid;
        }

        public void setPcardid(String pcardid) {
            this.pcardid = pcardid;
        }

        public String getPsex() {
            return psex;
        }

        public void setPsex(String psex) {
            this.psex = psex;
        }

        public String getPtel() {
            return ptel;
        }

        public void setPtel(String ptel) {
            this.ptel = ptel;
        }

        public String getPregisterdate() {
            return pregisterdate;
        }

        public void setPregisterdate(String pregisterdate) {
            this.pregisterdate = pregisterdate;
        }
    }
}
