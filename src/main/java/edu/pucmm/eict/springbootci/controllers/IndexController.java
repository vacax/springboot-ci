package edu.pucmm.eict.springbootci.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/")
public class IndexController {

    @GetMapping("/")
    public String index(){
        return "Proyecto Demostración CI/CD";
    }
}
