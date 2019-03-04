package com.opencensus.java;

import io.opencensus.exporter.trace.jaeger.JaegerTraceExporter;
import io.opencensus.stats.*;
import io.opencensus.tags.TagKey;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class MainClass {

    public static void main(String[] args){
        //Main Function
        SpringApplication.run(MainClass.class, args);
        JaegerTraceExporter.createAndRegister("http://localhost:14268/api/traces", "sample_custom_metric_service");

    }

}
