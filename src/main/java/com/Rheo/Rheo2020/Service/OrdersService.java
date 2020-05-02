package com.Rheo.Rheo2020.Service;

import com.Rheo.Rheo2020.model.Orders;
import com.Rheo.Rheo2020.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OrdersService {

    @Autowired
    OrderRepository orderRepository;


    public Orders createOrUpdate(Orders orders){
        orderRepository.save(orders);
        return orders;
    }


    public Orders findByOrderNo(String orderNo){

        return  orderRepository.findByOrderNo(orderNo);

    }

}
