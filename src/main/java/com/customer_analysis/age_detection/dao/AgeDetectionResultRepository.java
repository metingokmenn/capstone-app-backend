package com.customer_analysis.age_detection.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.customer_analysis.age_detection.model.DetectionResult;
import com.customer_analysis.age_detection.model.Image;

import java.time.LocalDateTime;
import java.util.List;


public interface AgeDetectionResultRepository extends JpaRepository<DetectionResult,Integer> {
    
    public DetectionResult findByImage(Image image);

    public List<DetectionResult> findByDetectedAge(Integer detectedAge);

    public List<DetectionResult> findByConfidenceScore(Double confidenceScore);

    public List<DetectionResult> findByTimestampBetween(LocalDateTime startDate, LocalDateTime endDate);



    
}
