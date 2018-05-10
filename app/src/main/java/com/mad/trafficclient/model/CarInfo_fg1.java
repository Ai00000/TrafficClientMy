package com.mad.trafficclient.model;

import java.util.List;

/**
 * Created by Administrator on 2017/6/2.
 */

public class CarInfo_fg1 {

    /**
     * ERRMSG : 成功
     * ROWS_DETAIL : [{"carnumber":"鲁A10001","number":101,"pcardid":"370214197107271055","carbrand":"audi","buydate":"2016-06-01"},{"carnumber":"鲁C50170","number":500,"pcardid":"370213199310221274","carbrand":"xiandai","buydate":"2016-06-01"}]
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
         * carnumber : 鲁A10001
         * number : 101
         * pcardid : 370214197107271055
         * carbrand : audi
         * buydate : 2016-06-01
         */

        private String carnumber;
        private int number;
        private String pcardid;
        private String carbrand;
        private String buydate;
        private String pname;


        public String getPname() {
            return pname;
        }

        public void setPname(String pname) {
            this.pname = pname;
        }

        public String getCarnumber() {
            return carnumber;
        }

        public void setCarnumber(String carnumber) {
            this.carnumber = carnumber;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public String getPcardid() {
            return pcardid;
        }

        public void setPcardid(String pcardid) {
            this.pcardid = pcardid;
        }

        public String getCarbrand() {
            return carbrand;
        }

        public void setCarbrand(String carbrand) {
            this.carbrand = carbrand;
        }

        public String getBuydate() {
            return buydate;
        }

        public void setBuydate(String buydate) {
            this.buydate = buydate;
        }
    }
}
