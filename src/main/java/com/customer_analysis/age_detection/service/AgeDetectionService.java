package com.customer_analysis.age_detection.service;

import java.util.List;

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

    public boolean remove(String type, Integer id){
        switch (type) {
            case "STORE":
                storeRepositroy.deleteById(id);
                return true;    
            case "IMAGE":
                imageRepository.deleteById(id);
                return true;
            case "VISIT":
                visitRepository.deleteById(id);
                return true;
            case "RESULT":
                resultRepository.deleteById(id);
                return true;
            default:
                return false;
        }
    }
}
