package com.customer_analysis.age_detection.model;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="Age_Detection_Result")
public class DetectionResult {
    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "result_id")
    private Integer resultId;

    @Column(name = "detected_age")
    private Integer detectedAge;

    @Column(name = "confidence_score")
    private Double confidenceScore;

    @CreationTimestamp
    @Column(name = "timestamp")
    private String timestamp;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id")
    private Image image;

    public DetectionResult(){}

    public DetectionResult(Integer detectedAge, Double confidenceScore, Image image){
        this.detectedAge = detectedAge;
        this.confidenceScore = confidenceScore;
        this.image = image;
    }

    public Integer getResultId(){
        return this.resultId;
    }

    public Integer getDetectedAge(){
        return this.detectedAge;
    }

    public Double getConfidenceScore(){
        return this.confidenceScore;
    }

    public String getTimestamp(){
        return this.timestamp;
    }


}
