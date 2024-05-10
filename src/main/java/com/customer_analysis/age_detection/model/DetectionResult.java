package com.customer_analysis.age_detection.model;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name="Age_Detection_Result")
public class DetectionResult {


    @Id
    @SequenceGenerator(name = "result_id_seq", sequenceName = "age_detection_result_result_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "result_id_seq")
    @Column(name = "result_id")
    private Integer resultId;

    @Column(name = "age_group")
    private String ageGroup;

    @Column(name = "gender")
    private String gender;

    @Column(name = "confidence_score")
    private Double confidenceScore;
    
    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "visit_id")
    private Visit visit;

    @PrePersist
    public void prePersist(){
        this.timestamp = LocalDateTime.now();
    }

    public DetectionResult(){}

    public DetectionResult(String ageGroup, String gender, Double confidenceScore, Visit visit){
        this.confidenceScore = confidenceScore;
        this.visit = visit;
        this.gender = gender;
        this.ageGroup = ageGroup;
    }

    public Integer getResultId(){
        return this.resultId;
    }

    public String getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(String ageGroup) {
        this.ageGroup = ageGroup;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Double getConfidenceScore(){
        return this.confidenceScore;
    }

    public LocalDateTime getTimestamp(){
        return this.timestamp;
    }


    public void setConfidenceScore(Double confidenceScore){
        this.confidenceScore = confidenceScore;
    }

    

    public void setVisit(Visit visit) {
        this.visit = visit;
    }

}
