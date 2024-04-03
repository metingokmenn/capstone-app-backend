package com.customer_analysis.age_detection.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Visit_Data")
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "visit_id")
    private Integer id;

    @Column(name = "timestamp")
    private String timestamp;

    @Column(name = "age_group")
    private String ageGroup;

    @Column(name = "gender")
    private String gender;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "store_id")
    private Store store;

    @OneToOne(mappedBy = "visit")
    private Image image;

    public Visit(){}

    public Visit(String ageGroup,String gender){
        this.ageGroup = ageGroup;
        this.gender = gender;
    }

    public Integer getId(){
        return this.id;
    }


    public String getTimestamp(){
        return this.timestamp;
    }

    public String getAgeGroup(){
        return this.ageGroup;
    }

    public String getGender(){
        return this.gender;
    }

    /*
    public Store getStore(){
        return this.store;
    }*/

    @Override
    public String toString() {
        /*String toS = "Visit ID: " + this.id + "Store : " + store.getStoreName() + " Time: " + this.timestamp + " Age Group: " + this.ageGroup + " Gender: " + this.gender;*/
        return "Visit ID: " + this.id;
    }

    
}
