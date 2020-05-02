package com.Rheo.Rheo2020.eunm;

public enum TradeStatus {


    NOPAY(1),

    PAY(2),

    PAYED(3);



    private Integer type;

    TradeStatus(Integer type) {
        this.type = type;
    }


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
