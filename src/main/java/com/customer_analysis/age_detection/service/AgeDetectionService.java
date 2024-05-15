package com.customer_analysis.age_detection.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import java.lang.IllegalStateException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.customer_analysis.age_detection.dao.AgeDetectionResultRepository;
import com.customer_analysis.age_detection.dao.AgeDetectionStoreRepositroy;
import com.customer_analysis.age_detection.dao.AgeDetectionVisitRepository;
import com.customer_analysis.age_detection.model.DetectionResult;

import com.customer_analysis.age_detection.model.Store;
import com.customer_analysis.age_detection.model.Visit;

@Service
@Transactional
public class AgeDetectionService {
    

    @Autowired
    private AgeDetectionStoreRepositroy storeRepositroy;

    @Autowired
    private AgeDetectionVisitRepository visitRepository;


    @Autowired
    private AgeDetectionResultRepository resultRepository;


    public List<Store> findAllStores(){
        return storeRepositroy.findAll();
    }

    public List<Visit> findAllVisits(){
        return visitRepository.findAll();
    }

    public Store findStoreById(Integer id){
        return storeRepositroy.getReferenceById(id);
    }

    public List<Visit> findVisitsOfStore(Store store){
        return visitRepository.findByStore(store);
    }

    public List<DetectionResult> findResultByStore(Integer id){
        Store store = findStoreById(id);

        List<Visit> visits = findVisitsOfStore(store);
        List<DetectionResult> results = new ArrayList<DetectionResult>();
        

        for (int i = 0; i < visits.size(); i++) {
            results.add(resultRepository.findByVisitId(visits.get(i).getVisitId()));
        }

        return results;

    }

    public List<DetectionResult> findResultByStoreByDate(LocalDateTime startDate, LocalDateTime endDate, Integer id){
        Store store = findStoreById(id);
    
        List<Visit> visits = findVisitsOfStore(store);
    
        List<DetectionResult> results = new ArrayList<>();
    
        for (Visit visit : visits) {
            DetectionResult result = resultRepository.findByVisitIdBetweenTimestamp(visit.getVisitId(), startDate, endDate);
            if (result != null) {
                results.add(result);
            }
        }
    
        return results;
    }

    

    public Visit findVisitById(Integer id){
        return visitRepository.getReferenceById(id);
    }
   

    public List<DetectionResult> findAllResults(){
        return resultRepository.findAll();
    }

    public Map<String,Integer> getAgeCounts(){
        List<String> ageCounts = resultRepository.countAge();
        Map<String,Integer> ageGroupCounts = new HashMap<String,Integer>();
        // Iterate over each data entry
        for (String entry : ageCounts) {
            // Split each entry into age group and count parts
            String[] parts = entry.split(",");
            String ageGroup = parts[0];
            int count = Integer.parseInt(parts[1]);

            // Add age group and count to the map
            ageGroupCounts.put(ageGroup, count);
        }

        return ageGroupCounts;
    }

    public Map<String,Integer> getAgeCountsByDate(LocalDateTime startDate, LocalDateTime endDate){
        List<String> ageCounts = resultRepository.countAgeByDate(startDate, endDate);
        Map<String,Integer> ageCountsMap = new HashMap<String,Integer>();

        for (String entry : ageCounts) {
            // Split each entry into age group and count parts
            String[] parts = entry.split(",");
            String ageGroup = parts[0];
            int count = Integer.parseInt(parts[1]);

            // Add age group and count to the map
            ageCountsMap.put(ageGroup, count);
        }

        return ageCountsMap;
    }

    public Map<String,Integer> getGenderCounts(){
        

        List<String> genderCounts = resultRepository.countGender();
        Map<String,Integer> genderCountsMap = new HashMap<String,Integer>();

        for (String genderCount : genderCounts) {
            // Split the string by comma to separate gender and count
            String[] parts = genderCount.split(",");
            if (parts.length == 2) {
                String gender = parts[0].trim();
                int count = Integer.parseInt(parts[1].trim());
                // Add gender and count to the map
                genderCountsMap.put(gender, count);
            }
        }

        return genderCountsMap;
    
    }

    public Map<String,Integer> getGenderCountByDate(LocalDateTime startDate, LocalDateTime endDate){
        List<String> genderCounts = resultRepository.countGenderByDate(startDate, endDate);
        Map<String,Integer> genderCountsMap = new HashMap<String,Integer>();

        for (String genderCount : genderCounts) {
            // Split the string by comma to separate gender and count
            String[] parts = genderCount.split(",");
            if (parts.length == 2) {
                String gender = parts[0].trim();
                int count = Integer.parseInt(parts[1].trim());
                // Add gender and count to the map
                genderCountsMap.put(gender, count);
            }
        }

        return genderCountsMap;
    }

    public List<DetectionResult> findResultByDate(LocalDateTime startDate, LocalDateTime endDate){
        return resultRepository.findByTimestampBetweenOrderByTimestamp(startDate, endDate);
    }

    public DetectionResult findResultByVisit(Visit visit){
        return resultRepository.findByVisit(visit);
    }


    public void addStore(String storeName, String location){
        storeRepositroy.save(new Store(storeName,location));
    }

    public void addVisit(Integer id){
        Store store = findStoreById(id);
        visitRepository.save(new Visit(store));
    }

    
    public void addResult(String gender, String ageGroup, Double confidenceScore, Integer id){
        Visit visit = findVisitById(id);
        if(resultRepository.existsByVisitId(id)){
            throw new IllegalStateException("This visit is already related with a result.");
        }
        else{
            resultRepository.save(new DetectionResult(ageGroup, gender, confidenceScore,visit));
        }
        
    }
    
    
    public void updateStore(Integer id,String storeName,String location){
        Store updatedStore = storeRepositroy.getReferenceById(id);

        updatedStore.setStoreName(storeName);
        updatedStore.setLocation(location);
    }

    public void updateStoreStoreName(Integer id,String storeName){
        Store updatedStore = storeRepositroy.getReferenceById(id);

        updatedStore.setStoreName(storeName);
    }

    public void updateStoreLocation(Integer id,String location){
        Store updatedStore = storeRepositroy.getReferenceById(id);

        updatedStore.setLocation(location);
    }

    public void updateVisit(Integer id,Store store){
        Visit updatedVisit = visitRepository.getReferenceById(id);
        
        updatedVisit.setStore(store);
    }

    
    public void updateResult(Integer id, String gender, String ageGroup, Visit visit, Double confidenceScore){
        DetectionResult updatedResult = resultRepository.getReferenceById(id);

        updatedResult.setAgeGroup(ageGroup);
        updatedResult.setGender(gender);
        updatedResult.setVisit(visit);
        updatedResult.setConfidenceScore(confidenceScore);
    }
    

    public void updateResultDetails(Integer id, String gender, String ageGroup, Double confidenceScore){
        DetectionResult updatedResult = resultRepository.getReferenceById(id);

        updatedResult.setAgeGroup(ageGroup);
        updatedResult.setGender(gender);
        updatedResult.setConfidenceScore(confidenceScore);
    }

    public void updateResultVisit(Integer id, Visit visit){
        DetectionResult updatedResult = resultRepository.getReferenceById(id);

        updatedResult.setVisit(visit);
    }


    public boolean remove(String type, Integer id){
        switch (type) {
            case "store":
                storeRepositroy.deleteById(id);
                return true;    
            case "visit":
                visitRepository.deleteById(id);
                return true;
            case "result":
                resultRepository.deleteById(id);
                return true;
            default:
                return false;
        }
    }

    public List<Object> search(String key){
        List<Object> resultList = new ArrayList<Object>();
        
        try {
            Integer idKey = Integer.parseInt(key);
            Optional<Visit> searchedVisit = visitRepository.findById(idKey);
            Optional<Store> searchedStore = storeRepositroy.findById(idKey);
            Optional<DetectionResult> searchedResult = resultRepository.findById(idKey);
            if(searchedVisit.isPresent()) resultList.add(searchedVisit);
            if(searchedStore.isPresent()) resultList.add(searchedStore);
            if(searchedResult.isPresent()) resultList.add(searchedResult);
            
        } catch (NumberFormatException e) {
            
        }

        return resultList;
        
    }
}
