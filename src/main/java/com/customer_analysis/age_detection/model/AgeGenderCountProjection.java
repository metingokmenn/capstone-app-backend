package com.customer_analysis.age_detection.model;

public interface AgeGenderCountProjection {
    String getAgeGroup();
    String getGender();
    Long getGenderCount();
}