package com.Rheo.Rheo2020.model;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Orders {


    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;


    //该订单创建的时间
    private Long gmtCreate;

    //该订单支付完成的时间
    private Long gmtCompleted;


    //表示是支付宝交易还是微信交易
    private Integer payType;




    //商户订单号
    private String orderNo;
    //支付宝交易号
    private String tradeNo;
    //付款金额
    private String totalAmount ;
    //交易状态
    private Integer tradeStatus ;

}
