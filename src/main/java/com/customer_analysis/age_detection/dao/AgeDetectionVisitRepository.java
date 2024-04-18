package com.customer_analysis.age_detection.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.customer_analysis.age_detection.model.Store;
import com.customer_analysis.age_detection.model.Visit;

public interface AgeDetectionVisitRepository extends JpaRepository<Visit,Integer> {
    
    public List<Visit> findByStore(Store store);

    public List<Visit> findByGender(String gender);

    public List<Visit> findByAgeGroup(String ageGroup);
}
