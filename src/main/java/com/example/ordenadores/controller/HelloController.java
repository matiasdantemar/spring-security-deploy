package com.example.ordenadores.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HelloController {

    @Value("${app.message}")
    private String mensaje;

    @GetMapping("api/hola")
    public String hola(@RequestHeader HttpHeaders headers) {
        System.out.println(headers.get("User-Agent"));
        return "hola" + mensaje;
    }
}
