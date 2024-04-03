package com.customer_analysis.age_detection.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Store")
public class Store {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Integer id;

    @Column(name="store_name")
    private String storeName;

    @Column(name = "location")
    private String location;

    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Visit> listVisit = new ArrayList<>();


    public Store(){}

    public Store(String storeName, String location){
        this.storeName = storeName;
        this.location = location;
    }

    public Integer getId(){
        return this.id;
    }

    public String getStoreName(){
        return this.storeName;
    }

    public String getLocation(){
        return this.location;
    }

    /*public List<Visit> getListVisit(){
        return this.listVisit;
    }*/

    


    @Override
    public String toString() {
        return "ID: " + this.id + " Store name: " + storeName + " Location: " + location;
    }
}
