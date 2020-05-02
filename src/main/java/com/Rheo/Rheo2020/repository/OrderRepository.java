package com.Rheo.Rheo2020.repository;


import com.Rheo.Rheo2020.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderRepository extends JpaRepository<Orders, Integer> {


    Orders findByOrderNo(String OrderNo);


}
