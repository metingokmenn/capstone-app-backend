package com.customer_analysis.age_detection.model;


import org.hibernate.annotations.Type;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="Image")
public class Image {
    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Integer imageId;

    @Column(name = "image_data")
    private byte[] imageData; 

    @Column(name = "timestamp")
    private String timestamp;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "visit_id")
    private Visit visit;

    @OneToOne(mappedBy = "image")
    private DetectionResult result;

    public Integer getId(){
        return this.imageId;
    }

    public byte[] getImageData(){
        return this.imageData;
    }

    public String getTimestamp(){
        return this.timestamp;
    }

}
