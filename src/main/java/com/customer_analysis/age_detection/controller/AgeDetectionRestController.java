package com.customer_analysis.age_detection.controller;

import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.customer_analysis.age_detection.model.DetectionResult;
import com.customer_analysis.age_detection.model.Image;
import com.customer_analysis.age_detection.model.Store;
import com.customer_analysis.age_detection.model.Visit;
import com.customer_analysis.age_detection.service.AgeDetectionService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;



@RestController
@RequestMapping("/rest")
public class AgeDetectionRestController {

    @Autowired
    private AgeDetectionService service;


    @GetMapping("/stores")
    public ResponseEntity<List<Store>> getStores() {
        List<Store> stores = service.findAllStores();

        return ResponseEntity.ok(stores);
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

    @GetMapping("/images")
    public ResponseEntity<List<Image>> getImages(){
        List<Image> images = service.findAllImages();
        return ResponseEntity.ok(images);
    }

    @GetMapping("/findImageByVisit/{id}")
    public ResponseEntity<Image> getImageByVisit(@PathVariable Integer id){
        Visit visit = service.findVisitById(id);

        Image image = service.findImageByVisit(visit);

        return ResponseEntity.ok(image);
    }

    @GetMapping("/results")
    public ResponseEntity<List<DetectionResult>> getResults(){
        List<DetectionResult> results = service.findAllResults();

        return ResponseEntity.ok(results);
    }

    @GetMapping("/findResultByImage/{id}")
    public ResponseEntity<DetectionResult> getResultByImage(@PathVariable Integer id){
        Image image = service.findImageById(id);

        DetectionResult result = service.findResultByImage(image);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/post_image")
    public ResponseEntity<String> postImage(@RequestParam("data") String imageData, @RequestParam("id") Integer id){
        byte[] decodedData = Base64.getDecoder().decode(imageData);
        
        service.postImage(decodedData,id);

        return ResponseEntity.ok("Image posted.");
    }

    @PostMapping("/add_store")
    public ResponseEntity<String> addStore(@RequestParam("store_name") String storeName, @RequestParam("location") String location){
        service.addStore(storeName, location);

        return ResponseEntity.ok("Store posted.");
    }

    @PostMapping("/add_visit")
    public ResponseEntity<String> addVisit(@RequestParam("age_group") String ageGroup, @RequestParam("gender") String gender, @RequestParam("id") Integer id){
        
        service.addVisit(ageGroup, gender, id);

        return ResponseEntity.ok("Visit posted.");
    }

    @PostMapping("/add_result")
    public ResponseEntity<String> addResult(@RequestParam("detected_age") Integer age, @RequestParam("confidence_score") Double confidenceScore, @RequestParam("id") Integer id){

        service.addResult(age, confidenceScore, id);

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


}
