package com.customer_analysis.age_detection.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

@Controller
public class AgeDetectionController {
    
    @Autowired
    private RestTemplate restTemplate;
}
