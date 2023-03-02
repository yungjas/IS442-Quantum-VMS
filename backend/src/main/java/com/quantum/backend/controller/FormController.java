package com.quantum.backend.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


public class FormController {
    
    @GetMapping("/")
    public String getForms(Model model){
        return "";
    }

}
