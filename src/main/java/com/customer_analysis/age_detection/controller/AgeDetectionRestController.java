package com.customer_analysis.age_detection.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.customer_analysis.age_detection.model.DetectionResult;
import com.customer_analysis.age_detection.model.Image;
import com.customer_analysis.age_detection.model.Store;
import com.customer_analysis.age_detection.model.Visit;
import com.customer_analysis.age_detection.service.AgeDetectionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



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

    
}
