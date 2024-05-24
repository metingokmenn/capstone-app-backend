package com.customer_analysis.age_detection.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.customer_analysis.age_detection.model.AgeGenderCountProjection;
import com.customer_analysis.age_detection.model.AgeGroupCountProjection;
import com.customer_analysis.age_detection.model.DetectionResult;
import com.customer_analysis.age_detection.model.GenderCountProjection;
import com.customer_analysis.age_detection.model.MonthlyCountProjection;
import com.customer_analysis.age_detection.model.Visit;
import java.time.LocalDateTime;

import java.util.List;



public interface AgeDetectionResultRepository extends JpaRepository<DetectionResult,Integer> {
    

    
    
    public DetectionResult findByVisit(Visit visit);

    public List<DetectionResult> findByConfidenceScore(Double confidenceScore);

    public List<DetectionResult> findByTimestampBetweenOrderByTimestamp(LocalDateTime startDate, LocalDateTime endDate);

    
    @Query("SELECT gender, COUNT(gender) FROM DetectionResult GROUP BY gender")
    public List<String> countGender();

    @Query("SELECT d.gender, COUNT(d.gender) FROM DetectionResult d WHERE d.timestamp BETWEEN :start_date AND :end_date GROUP BY d.gender")
    public List<String> countGenderByDate(@Param("start_date") LocalDateTime startDate, @Param("end_date") LocalDateTime endDate);

    @Query("SELECT adr.gender AS gender, COUNT(adr) AS totalCount " +
           "FROM DetectionResult adr " +
           "JOIN adr.visit vd " +
           "WHERE vd.store.id = :storeId " +
           "GROUP BY adr.gender")
    List<GenderCountProjection> findGenderCountsByStoreId(@Param("storeId") Integer storeId);

    @Query("SELECT adr.gender AS gender, COUNT(adr) AS totalCount " +
    "FROM DetectionResult adr " +
    "JOIN adr.visit vd " +
    "WHERE vd.store.id = :storeId " +
    "AND adr.timestamp BETWEEN :startDate AND :endDate " +
    "GROUP BY adr.gender")
List<GenderCountProjection> findGenderCountsByStoreIdAndDateRange(@Param("storeId") Integer storeId,
                                                               @Param("startDate") LocalDateTime startDate,
                                                               @Param("endDate") LocalDateTime endDate);

    @Query("SELECT ageGroup, COUNT(ageGroup) FROM DetectionResult GROUP BY ageGroup ORDER BY CAST(SUBSTRING(ageGroup, 1, POSITION('-' IN ageGroup) - 1) AS int)")
    public List<String> countAge();

    @Query("SELECT d.ageGroup, COUNT(d.ageGroup) FROM DetectionResult d WHERE d.timestamp BETWEEN :start_date AND :end_date GROUP BY d.ageGroup")
    public List<String> countAgeByDate(@Param("start_date") LocalDateTime startDate, @Param("end_date") LocalDateTime endDate);

    @Query("SELECT adr.ageGroup AS ageGroup, COUNT(adr) AS totalCount " +
       "FROM DetectionResult adr " +
       "JOIN adr.visit vd " +
       "WHERE vd.store.id = :storeId " +
       "GROUP BY adr.ageGroup " +
       "ORDER BY CAST(SUBSTRING(adr.ageGroup, 1, POSITION('-' IN adr.ageGroup) - 1) AS int)")
    List<AgeGroupCountProjection> findAgeGroupCountsByStoreId(@Param("storeId") int storeId);

    @Query("SELECT adr.ageGroup AS ageGroup, COUNT(adr) AS totalCount " +
           "FROM DetectionResult adr " +
           "JOIN adr.visit vd " +
           "WHERE vd.store.id = :storeId " +
           "AND adr.timestamp BETWEEN :startDate AND :endDate " +
           "GROUP BY adr.ageGroup " +
           "ORDER BY CAST(SUBSTRING(adr.ageGroup, 1, POSITION('-' IN adr.ageGroup) - 1) AS int)")
    List<AgeGroupCountProjection> findAgeGroupCountsByStoreIdAndDateRange(@Param("storeId") int storeId,
                                                                         @Param("startDate") LocalDateTime startDate,
                                                                         @Param("endDate") LocalDateTime endDate);

    public boolean existsByVisitId(Integer visitId);


    public DetectionResult findByVisitId(Integer visitId);

    @Query("SELECT d FROM DetectionResult d JOIN d.visit v WHERE v.id = :visitId AND d.timestamp BETWEEN :start_date AND :end_date")
    public DetectionResult findByVisitIdBetweenTimestamp(@Param("visitId") Integer visitId, @Param("start_date") LocalDateTime startDate, @Param("end_date") LocalDateTime endDate);

    @Query("SELECT adr.ageGroup AS ageGroup, adr.gender AS gender, COUNT(adr) AS genderCount " +
       "FROM DetectionResult adr " +
       "JOIN adr.visit vd " +
       "GROUP BY adr.ageGroup, adr.gender " +
       "ORDER BY CAST(SUBSTRING(adr.ageGroup, 1, POSITION('-' IN adr.ageGroup) - 1) AS int)")
    List<AgeGenderCountProjection> getAgeGenderCounts();

    @Query("SELECT adr.ageGroup AS ageGroup, adr.gender AS gender, COUNT(adr) AS genderCount " +
       "FROM DetectionResult adr " +
       "JOIN adr.visit vd " +
       "WHERE vd.store.id = :storeId " +
       "GROUP BY adr.ageGroup, adr.gender " +
       "ORDER BY CAST(SUBSTRING(adr.ageGroup, 1, POSITION('-' IN adr.ageGroup) - 1) AS int)")
List<AgeGenderCountProjection> getAgeGenderCountsByStoreId(@Param("storeId") int storeId);

@Query("SELECT adr.ageGroup AS ageGroup, adr.gender AS gender, COUNT(adr) AS genderCount " +
       "FROM DetectionResult adr " +
       "JOIN adr.visit vd " +
       "WHERE adr.timestamp BETWEEN :startDate AND :endDate " +
       "GROUP BY adr.ageGroup, adr.gender " +
       "ORDER BY CAST(SUBSTRING(adr.ageGroup, 1, POSITION('-' IN adr.ageGroup) - 1) AS int)")
List<AgeGenderCountProjection> getAgeGenderCountsByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

@Query("SELECT adr.ageGroup AS ageGroup, adr.gender AS gender, COUNT(adr) AS genderCount " +
       "FROM DetectionResult adr " +
       "JOIN adr.visit vd " +
       "WHERE vd.store.id = :storeId " +
       "AND adr.timestamp BETWEEN :startDate AND :endDate " +
       "GROUP BY adr.ageGroup, adr.gender " +
       "ORDER BY CAST(SUBSTRING(adr.ageGroup, 1, POSITION('-' IN adr.ageGroup) - 1) AS int)")
List<AgeGenderCountProjection> getAgeGenderCountsByStoreIdAndDateRange(@Param("storeId") Integer storeId,
                                                                      @Param("startDate") LocalDateTime startDate,
                                                                      @Param("endDate") LocalDateTime endDate);

    @Query("SELECT EXTRACT(MONTH FROM ar.timestamp) AS month, COUNT(*) AS totalCount " +
       "FROM DetectionResult ar " +
       "WHERE (EXTRACT(YEAR FROM ar.timestamp) = EXTRACT(YEAR FROM CAST(:startDate AS timestamp)) " +
       "AND EXTRACT(MONTH FROM ar.timestamp) <= EXTRACT(MONTH FROM CAST(:startDate AS timestamp))) " +
       "GROUP BY EXTRACT(MONTH FROM ar.timestamp) " +
       "ORDER BY month")
    List<MonthlyCountProjection> getMonthlyCounts(LocalDateTime startDate);

    @Query("SELECT EXTRACT(MONTH FROM ar.timestamp) AS month, COUNT(*) AS totalCount " +
           "FROM DetectionResult ar " +
           "JOIN ar.visit vd " +
           "WHERE vd.store.id = :storeId " +
           "AND EXTRACT(YEAR FROM ar.timestamp) = EXTRACT(YEAR FROM CAST(:startDate AS timestamp)) " +
           "AND EXTRACT(MONTH FROM ar.timestamp) <= EXTRACT(MONTH FROM CAST(:startDate AS timestamp)) " +
           "GROUP BY EXTRACT(MONTH FROM ar.timestamp) " +
           "ORDER BY month")
    List<MonthlyCountProjection> getMonthlyCountsByStoreId(@Param("startDate") LocalDateTime startDate, @Param("storeId") Integer storeId);

    

    @Query("SELECT adr.confidenceScore " +
           "FROM DetectionResult adr " +
           "JOIN adr.visit vd " +
           "WHERE vd.store.id = :storeId " +
           "AND adr.timestamp >= :startOfDay ")
    List<Double> findConfidenceScoresByStoreIdAndToday(@Param("storeId") Integer storeId,
    @Param("startOfDay") LocalDateTime startOfDay);

    @Query("SELECT COUNT(adr) " +
           "FROM DetectionResult adr " +
           "JOIN adr.visit vd " +
           "WHERE vd.store.id = :storeId " +
           "AND adr.timestamp >= :startOfDay")
    Long countVisitsByStoreIdAndToday(@Param("storeId") Integer storeId,
                                      @Param("startOfDay") LocalDateTime startOfDay);

    


    @Query("SELECT dr.confidenceScore FROM DetectionResult dr WHERE dr.timestamp >= :startOfToday")
    List<Double> findAllConfidenceScoresForToday(@Param("startOfToday") LocalDateTime startOfToday);


    
    
}
