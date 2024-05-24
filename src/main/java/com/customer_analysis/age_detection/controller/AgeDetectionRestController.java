package com.customer_analysis.age_detection.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.customer_analysis.age_detection.model.AgeGenderCountProjection;
import com.customer_analysis.age_detection.model.AgeGroupCountProjection;
import com.customer_analysis.age_detection.model.DetectionResult;
import com.customer_analysis.age_detection.model.GenderCountProjection;
import com.customer_analysis.age_detection.model.MonthlyCountProjection;
import com.customer_analysis.age_detection.model.Store;
import com.customer_analysis.age_detection.model.Visit;
import com.customer_analysis.age_detection.service.AgeDetectionService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;



@RestController
@RequestMapping("/rest")
@CrossOrigin
public class AgeDetectionRestController {

    @Autowired
    private AgeDetectionService service;


    @GetMapping("/stores")
    public ResponseEntity<List<Store>> getStores() {
        
        List<Store> stores = service.findAllStores();
        return ResponseEntity.ok(stores);
          
    }

    @GetMapping("/stores/{id}")
    public ResponseEntity<String> getStoreById(@PathVariable("id") Integer storeId){

        String storeName = service.findStoreName(storeId);
        return ResponseEntity.ok(storeName);
    }

    

    @GetMapping("/visits")
    public ResponseEntity<List<Visit>> getVisits(){
        List<Visit> visits = service.findAllVisits();

        return ResponseEntity.ok(visits);
    }

    @GetMapping("/findVisitByStore/{id}")
    public ResponseEntity<List<Visit>> getVisitsByStore(@PathVariable Integer id){

        Store store = service.findStoreById(id);

        List<Visit> visits = service.findVisitsOfStore(store);

        return ResponseEntity.ok(visits);
    }

    @GetMapping("/findResultByVisit/{id}")
    public ResponseEntity<DetectionResult> getResultByVisit(@PathVariable Integer id){

        Visit visit = service.findVisitById(id);

        Hibernate.initialize(visit);

        DetectionResult result = service.findResultByVisit(visit);

        Hibernate.initialize(result);

        return ResponseEntity.ok(result);

    }

    @GetMapping("/confidenceScoresByStore")
    public List<Double> getConfidenceScoresByStoreIdAndToday(@RequestParam("store_id") Integer storeId) {
        return service.getConfidenceScoresByStoreIdAndToday(storeId);
    }

    @GetMapping("/monthlyCountsByStore")
    public List<MonthlyCountProjection> getMonthlyCountsByStoreId(@RequestParam("start_date") LocalDateTime startDate,
                                                                  @RequestParam("store_id") Integer storeId) {
        return service.getMonthlyCountsByStoreId(startDate, storeId);
    }



    @GetMapping("/findResultByStore/{id}")
    public ResponseEntity<List<DetectionResult>> getResultByStore(@PathVariable Integer id, @RequestParam("start_date") Optional<String> startDateString, @RequestParam("end_date") Optional<String> endDateString){

        List<DetectionResult> results;

        if(startDateString.isPresent() && endDateString.isPresent()){
            LocalDateTime startDate = LocalDateTime.parse(startDateString.get());
            LocalDateTime endDate = LocalDateTime.parse(endDateString.get());

            results = service.findResultByStoreByDate(startDate, endDate, id);
        }
        else{
            results = service.findResultByStore(id);
        }

        

        return ResponseEntity.ok(results);

    }


    @GetMapping("/checkStoreId")
    public ResponseEntity<Boolean> checkStoreId(@RequestParam("store_id") Integer id) {

        boolean result = service.isStorExistById(id);

        return ResponseEntity.ok(result);
    }



    @GetMapping("/results")
    public ResponseEntity<List<DetectionResult>> getResults(@RequestParam("start_date") Optional<String> startDateString,
    @RequestParam("end_date") Optional<String> endDateString){
        List<DetectionResult> results;

        
        if(startDateString.isPresent() && endDateString.isPresent()){
            LocalDateTime startDate = LocalDateTime.parse(startDateString.get());
            LocalDateTime endDate = LocalDateTime.parse(endDateString.get());
            results = service.findResultByDate(startDate, endDate);
        }
        else{
            results = service.findAllResults();
        }

        return ResponseEntity.ok(results);
    }

    @GetMapping("/results/gender")
    public ResponseEntity<Map<String,Integer>> getGenderCount(@RequestParam("start_date") Optional<String> startDateString, @RequestParam("end_date") Optional<String> endDateString){
        Map<String, Integer> genderCountsMap;
        
        if(startDateString.isPresent() && endDateString.isPresent()){
            LocalDateTime startDate = LocalDateTime.parse(startDateString.get());
            LocalDateTime endDate = LocalDateTime.parse(endDateString.get());
            genderCountsMap = service.getGenderCountByDate(startDate, endDate);
        }

        else{
            genderCountsMap = service.getGenderCounts();
        }


        return ResponseEntity.ok(genderCountsMap);
    }

    @GetMapping("/gender-counts")
    public Map<String,Long> findGenderCountsByStoreIdAndDateRange(@RequestParam("store_id") Integer storeId,
                                                                             @RequestParam("start_date") LocalDateTime startDate,
                                                                             @RequestParam("end_date") LocalDateTime endDate) {
        return service.findGenderCountsByStoreIdAndDateRange(storeId, startDate, endDate);
    }

    @GetMapping("/age-group-counts")
    public Map<String,Long> findAgeGroupCountsByStoreIdAndDateRange(@RequestParam("store_id") int storeId,
                                                                                 @RequestParam("start_date") LocalDateTime startDate,
                                                                                 @RequestParam("end_date") LocalDateTime endDate) {
        return service.findAgeGroupCountsByStoreIdAndDateRange(storeId, startDate, endDate);
    }

    @GetMapping("/confidenceScores")
    public List<Double> getConfidenceScoresForToday() {
        return service.getConfidenceScoresForToday();
    }

    @GetMapping("/results/age")
    public ResponseEntity<Map<String,Integer>> getAgeCount(@RequestParam("start_date") Optional<String> startDateString, @RequestParam("end_date") Optional<String> endDateString){
        Map<String,Integer> ageCounts;
        if(startDateString.isPresent() && endDateString.isPresent()){
            LocalDateTime startDate = LocalDateTime.parse(startDateString.get());
            LocalDateTime endDate = LocalDateTime.parse(endDateString.get());
            ageCounts = service.getAgeCountsByDate(startDate,endDate);
        }

        else{
            ageCounts = service.getAgeCounts();
        }

        return ResponseEntity.ok(ageCounts);
    }


    @PostMapping("/add_store")
    public ResponseEntity<String> addStore(@RequestParam("store_name") String storeName, @RequestParam("location") String location){
        service.addStore(storeName, location);

        return ResponseEntity.ok("Store posted.");
    }

    @GetMapping("/results/getGenderCountByAgeGroup")
    public List<Map<String, Map<String, Long>>> getGenderCountByAgeGroup() {
        return service.getFormattedAgeGenderCounts();
    }
    
    @GetMapping("/results/getGenderCountByAgeGroupByStoreId")
    public List<Map<String, Map<String, Long>>> getGenderCountByAgeGroupByStoreId(@RequestParam("store_id") Integer storeId) {
        return service.getFormattedAgeGenderCountsByStoreId(storeId);
    }

    @GetMapping("/results/getGenderCountByAgeGroupByDateRange")
    public List<Map<String, Map<String, Long>>> getGenderCountByAgeGroupByDateRange(@RequestParam("start_date") LocalDateTime startDate, @RequestParam("end_date") LocalDateTime endDate) {
        return service.getFormattedAgeGenderCountsByDateRange(startDate,endDate);
    }

    @GetMapping("/results/getGenderCountByAgeGroupByStoreIdAndDateRange")
    public List<Map<String, Map<String, Long>>> getGenderCountByAgeGroupByStoreIdAndDateRange(@RequestParam("store_id") Integer storeId,@RequestParam("start_date") LocalDateTime startDate, @RequestParam("end_date") LocalDateTime endDate ) {
        return service.getFormattedAgeGenderCountsByStoreIdAndDateRange(storeId,startDate,endDate);
    }
    
    @GetMapping("/gender_counts/{storeId}")
    public Map<String, Long> getGenderCounts(@PathVariable int storeId) {
        return service.getGenderCountsByStoreId(storeId);
    }

    @GetMapping("/age_group_counts/{storeId}")
    public Map<String, Long> getAgeGroupCounts(@PathVariable int storeId) {
        return service.getAgeGroupCountsByStoreId(storeId);
    }

    @GetMapping("/monthly_counts")
    public List<MonthlyCountProjection> getMonthlyCounts(
            @RequestParam("start_date") String startDateString) {
        LocalDateTime startDate = LocalDateTime.parse(startDateString);
        return service.getMonthlyCounts(startDate);
    }

    @PostMapping("/add_visit")
    public ResponseEntity<String> addVisit(@RequestParam("store_id") Integer id){
        
        service.addVisit(id);

        return ResponseEntity.ok("Visit posted.");
    }


    @PostMapping("/add_result")
    public ResponseEntity<String> addResult(@RequestParam("age_group") String ageGroup, @RequestParam("gender") String gender , @RequestParam("confidence_score") Double confidenceScore, @RequestParam("visit_id") Integer id){

        service.addResult(gender, ageGroup, confidenceScore, id);

        return ResponseEntity.ok("Result posted.");
    }

    @DeleteMapping("/delete/{type}/{id}")
    public ResponseEntity<String> delete(@PathVariable String type, @PathVariable Integer id){

        if(service.remove(type.toLowerCase(), id)){
            return ResponseEntity.ok("Record removed.");
        }
        else{
            return ResponseEntity.ok("Given type is not available.");
        }
    }

    @PatchMapping("/update_store")
    public ResponseEntity<String> updateStore(@RequestParam("id") Integer id, @RequestParam("store_name") String storeName, @RequestParam("location") String location){

        service.updateStore(id, storeName, location);

        return ResponseEntity.ok("Store is updated.");
    }

    @PatchMapping("/update_store_name")
    public ResponseEntity<String> updateStoreStoreName(@RequestParam("id") Integer id, @RequestParam("store_name") String storeName){

        service.updateStoreStoreName(id, storeName);

        return ResponseEntity.ok("Store name is updated.");
    }

    @PatchMapping("/update_store_location")
    public ResponseEntity<String> updateStoreLocation(@RequestParam("id") Integer id, @RequestParam("location") String location){

        service.updateStoreLocation(id, location);

        return ResponseEntity.ok("Store location is updated.");
    }

    @PatchMapping("/update_visit")
    public ResponseEntity<String> updateVisit(@RequestParam("id") Integer id, @RequestParam("store_id") Integer storeId){

        Store store = service.findStoreById(storeId);

        service.updateVisit(id,store);

        return ResponseEntity.ok("Visit is updated.");
    }



    @PatchMapping("/update_result")
    public ResponseEntity<String> updateResult(@RequestParam("id") Integer id, @RequestParam("gender") String gender , @RequestParam("age_group") String ageGroup, @RequestParam("visit_id") Integer visitId, @RequestParam("confidence_score") Double confidenceScore){

        Visit visit = service.findVisitById(visitId);

        service.updateResult(id, gender, ageGroup, visit, confidenceScore);


        return ResponseEntity.ok("Result is updated");
    }

    @PatchMapping("/update_result_details")
    public ResponseEntity<String> updateResultDetails(@RequestParam("id") Integer id, @RequestParam("gender") String gender,@RequestParam("age_group") String ageGroup , @RequestParam("confidence_score") Double confidenceScore){

        service.updateResultDetails(id, gender, ageGroup, confidenceScore);


        return ResponseEntity.ok("Result details are updated");
    }

    @PatchMapping("update_result_visit")
    public ResponseEntity<String> updateResultVisit(@RequestParam("id") Integer id, @RequestParam("visit_id") Integer visitId){
        Visit visit = service.findVisitById(visitId);

        service.updateResultVisit(id, visit);

        return ResponseEntity.ok("Result visit is updated.");
    }

    

    @GetMapping("/search/{key}")
    public ResponseEntity<List<Object>> searchEntity(@PathVariable() String key){

        List<Object> result = service.search(key);

        return ResponseEntity.ok(result);
    }


}
