package com.cartservice.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class Catogery {


    private Integer catogeryId;
    private String catogeryName;


    private List<Product> products=new ArrayList<>();

}
