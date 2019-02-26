package com.sample.opentrace.controller;

import io.jaegertracing.Configuration;
import io.opentracing.Span;
import io.opentracing.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class hello {

    @Autowired
    private RestTemplate restTemplate;

    /*@Autowired
    private Tracer jaegerTracer;*/

    @RequestMapping(value = "/hello")
    public String helloMethod(){

        //Span childSpan = jaegerTracer.buildSpan("HelloRequest").start() ;
        try {
            return "Hello";
        }
        finally {
          //  childSpan.finish();
        }
    }

    @RequestMapping(value = "/chaining")
    public String chainMethod(){

        //Span parentSpan = jaegerTracer.buildSpan("Chaining Request").start();
        try {
            ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/hello", String.class);
            return "Chaining + " + response.getBody();
        }
        finally {
          //  parentSpan.finish();
        }
    }

}
