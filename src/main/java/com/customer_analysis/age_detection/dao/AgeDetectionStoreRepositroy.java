package com.customer_analysis.age_detection.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.customer_analysis.age_detection.model.Store;

public interface AgeDetectionStoreRepositroy extends JpaRepository<Store,Integer> {
    
}
