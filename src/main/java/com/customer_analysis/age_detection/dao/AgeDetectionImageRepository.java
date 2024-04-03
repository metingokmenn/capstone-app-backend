package com.customer_analysis.age_detection.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.customer_analysis.age_detection.model.Image;
import com.customer_analysis.age_detection.model.Visit;

public interface AgeDetectionImageRepository extends JpaRepository<Image,Integer> {
    
    public Image findByVisit(Visit visit); 
}
