package com.cartservice.repository;

import com.cartservice.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart,Integer> {
    //Cart findByUser_Id(Integer userId);

    //hello

    Optional<Cart> findByUserId(Integer userId);

}
