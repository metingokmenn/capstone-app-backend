package com.customer_analysis.age_detection.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "Store")
public class Store {
    
    @Id
    @SequenceGenerator(name = "store_id_seq", sequenceName = "store_store_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "store_id_seq")
    @Column(name = "store_id")
    private Integer id;

    @Column(name="store_name")
    private String storeName;

    @Column(name = "location")
    private String location;

    @OneToMany(mappedBy = "store",cascade = CascadeType.ALL)
    private List<Visit> listVisit = new ArrayList<>();


    public Store(){}

    public Store(String storeName, String location){
        this.storeName = storeName;
        this.location = location;
    }

    public Integer getStoreId(){
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

    public void setStoreName(String storeName){
        this.storeName = storeName;
    }

    public void setLocation(String location){
        this.location = location;
    }

    


    @Override
    public String toString() {
        return "ID: " + this.id + " Store name: " + storeName + " Location: " + location;
    }
}
