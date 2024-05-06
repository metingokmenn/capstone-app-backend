package com.customer_analysis.age_detection.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.customer_analysis.age_detection.model.DetectionResult;
import com.customer_analysis.age_detection.model.Visit;
import java.time.LocalDateTime;

import java.util.List;
import java.util.Map;


public interface AgeDetectionResultRepository extends JpaRepository<DetectionResult,Integer> {
    

    
    
    public DetectionResult findByVisit(Visit visit);

    public List<DetectionResult> findByConfidenceScore(Double confidenceScore);

    public List<DetectionResult> findByTimestampBetween(LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT gender, COUNT(gender) FROM DetectionResult GROUP BY gender")
    public List<String> countGender();

    @Query("SELECT d.gender, COUNT(d.gender) FROM DetectionResult d WHERE d.timestamp BETWEEN :start_date AND :end_date GROUP BY d.gender")
    public List<String> countGenderByDate(@Param("start_date") LocalDateTime startDate, @Param("end_date") LocalDateTime endDate);

    @Query("SELECT ageGroup, COUNT(ageGroup) FROM DetectionResult GROUP BY ageGroup")
    public List<String> countAge();

    @Query("SELECT d.ageGroup, COUNT(d.ageGroup) FROM DetectionResult d WHERE d.timestamp BETWEEN :start_date AND :end_date GROUP BY d.ageGroup")
    public List<String> countAgeByDate(@Param("start_date") LocalDateTime startDate, @Param("end_date") LocalDateTime endDate);




    
}
