package com.Rheo.Rheo2020.eunm;

public enum PayType {

    //支付宝
    ALIPAY(1),
    //微信
    WECHART(2);



    private Integer type;

    PayType(Integer type) {
        this.type = type;
    }


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
