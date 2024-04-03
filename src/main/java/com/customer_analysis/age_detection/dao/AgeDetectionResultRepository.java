package com.customer_analysis.age_detection.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.customer_analysis.age_detection.model.DetectionResult;
import com.customer_analysis.age_detection.model.Image;

public interface AgeDetectionResultRepository extends JpaRepository<DetectionResult,Integer> {
    
    public DetectionResult findByImage(Image image);
}
