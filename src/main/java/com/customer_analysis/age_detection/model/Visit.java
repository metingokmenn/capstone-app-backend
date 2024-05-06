package com.customer_analysis.age_detection.model;

import java.time.LocalDateTime;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "Visit_Data")
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "visit_id")
    private Integer id;

    
    @Column(name = "timestamp")
    private LocalDateTime timestamp;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "store_id")
    private Store store;

    @OneToOne(mappedBy = "visit",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private DetectionResult result;

    @PrePersist
    public void prePersist(){
        this.timestamp = LocalDateTime.now();
    }

    public Visit(){}

    public Visit(Store store){
        
        this.store = store;
    }

    public Integer getVisitId(){
        return this.id;
    }


    public LocalDateTime getTimestamp(){
        return this.timestamp;
    }

    public void setStore(Store store){
        this.store = store;
    }

    
    

    @Override
    public String toString() {
        /*String toS = "Visit ID: " + this.id + "Store : " + store.getStoreName() + " Time: " + this.timestamp + " Age Group: " + this.ageGroup + " Gender: " + this.gender;*/
        return "Visit ID: " + this.id;
    }

    
}
