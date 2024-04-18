package com.customer_analysis.age_detection.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.customer_analysis.age_detection.model.Store;
import java.util.List;


public interface AgeDetectionStoreRepositroy extends JpaRepository<Store,Integer> {
    
    public List<Store> findByLocation(String Location);

    public List<Store> findByStoreName(String storeName);
}
