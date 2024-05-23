package com.customer_analysis.age_detection.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.customer_analysis.age_detection.model.Store;
import java.util.List;


public interface AgeDetectionStoreRepositroy extends JpaRepository<Store,Integer> {
    
    public List<Store> findByLocation(String Location);

    public List<Store> findByStoreName(String storeName);

    @Query("SELECT storeName from Store s where s.id = :storeId")
    public String findStoreName(@Param("storeId") Integer storeId);

}
