package com.customer_analysis.age_detection.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.customer_analysis.age_detection.dao.AgeDetectionImageRepository;
import com.customer_analysis.age_detection.dao.AgeDetectionResultRepository;
import com.customer_analysis.age_detection.dao.AgeDetectionStoreRepositroy;
import com.customer_analysis.age_detection.dao.AgeDetectionVisitRepository;
import com.customer_analysis.age_detection.model.DetectionResult;
import com.customer_analysis.age_detection.model.Image;
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
    private AgeDetectionImageRepository imageRepository;

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

    public Visit findVisitById(Integer id){
        return visitRepository.getReferenceById(id);
    }

    public Image findImageByVisit(Visit visit){
        return imageRepository.findByVisit(visit);
    }

    public List<Image> findAllImages(){
        return imageRepository.findAll();
    }

    public Image findImageById(Integer id){
        return imageRepository.getReferenceById(id);
    }

    public List<DetectionResult> findAllResults(){
        return resultRepository.findAll();
    }

    public DetectionResult findResultByImage(Image image){
        return resultRepository.findByImage(image);
    }

    public void postImage(byte[] imageData, Integer id){
        Visit visit = findVisitById(id);
        imageRepository.save(new Image(imageData,visit));
    }

    public void addStore(String storeName, String location){
        storeRepositroy.save(new Store(storeName,location));
    }

    public void addVisit(String ageGroup, String gender, Integer id){
        Store store = findStoreById(id);
        visitRepository.save(new Visit(ageGroup,gender,store));
    }

    public void addResult(Integer detectedAge, Double confidenceScore, Integer id){
        Image image = findImageById(id);
        resultRepository.save(new DetectionResult(detectedAge,confidenceScore,image));
        
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

    public void updateVisit(Integer id, String ageGroup, String gender, Store store){
        Visit updatedVisit = visitRepository.getReferenceById(id);
        
        updatedVisit.setAgeGroup(ageGroup);
        updatedVisit.setGender(gender);
        updatedVisit.setStore(store);
    }

    public void updateVisitDetails(Integer id, String ageGroup, String gender){
        Visit updatedVisit = visitRepository.getReferenceById(id);

        updatedVisit.setAgeGroup(ageGroup);
        updatedVisit.setGender(gender);
    }

    public void updateVisitStore(Integer id, Store store){
        Visit updatedVisit = visitRepository.getReferenceById(id);

        updatedVisit.setStore(store);
    }

    public void updateImage(Integer id, byte[] imageData, Visit visit){
        Image updatedImage = imageRepository.getReferenceById(id);

        updatedImage.setImageData(imageData);
        updatedImage.setVisit(visit);
    }

    public void updateImageData(Integer id, byte[] imageData){
        Image updatedImage = imageRepository.getReferenceById(id);

        updatedImage.setImageData(imageData);
    }

    public void updateImageVisit(Integer id, Visit visit){
        Image updatedImage = imageRepository.getReferenceById(id);

        updatedImage.setVisit(visit);
    }

    public void updateResult(Integer id, Integer detectedAge, Double confidenceScore, Image image){
        DetectionResult updatedResult = resultRepository.getReferenceById(id);

        updatedResult.setDetectedAge(detectedAge);
        updatedResult.setConfidenceScore(confidenceScore);
        updatedResult.setImage(image);
    }

    public void updateResultDetails(Integer id, Integer detectedAge, Double confidenceScore){
        DetectionResult updatedResult = resultRepository.getReferenceById(id);

        updatedResult.setDetectedAge(detectedAge);
        updatedResult.setConfidenceScore(confidenceScore);
    }

    public void updateResultImage(Integer id, Image image){
        DetectionResult updatedResult = resultRepository.getReferenceById(id);

        updatedResult.setImage(image);
    }

    public boolean remove(String type, Integer id){
        switch (type) {
            case "store":
                storeRepositroy.deleteById(id);
                return true;    
            case "image":
                imageRepository.deleteById(id);
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
            Optional<Image> searchedImage = imageRepository.findById(idKey);
            Optional<Store> searchedStore = storeRepositroy.findById(idKey);
            Optional<DetectionResult> searchedResult = resultRepository.findById(idKey);
            if(searchedVisit.isPresent()) resultList.add(searchedVisit);
            if(searchedImage.isPresent()) resultList.add(searchedImage);
            if(searchedStore.isPresent()) resultList.add(searchedStore);
            if(searchedResult.isPresent()) resultList.add(searchedResult);
            
        } catch (NumberFormatException e) {
            
        }

        return resultList;
        
    }
}
