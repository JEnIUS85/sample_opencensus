package com.opencensus.java.controller;

import io.opencensus.common.Scope;
import io.opencensus.trace.Tracer;
import io.opencensus.trace.Tracing;
import io.opencensus.trace.config.TraceConfig;
import io.opencensus.trace.config.TraceParams;
import io.opencensus.trace.samplers.Samplers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    Logger log = LoggerFactory.getLogger(HelloController.class);


   @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public ResponseEntity<String> helloMethod(){
       createTrace("helloMethod");
       return new ResponseEntity<>("hello", HttpStatus.OK);
   }

   public void createTrace(String spanName){
      // Configure 100% sample rate, otherwise, few traces will be sampled.
       TraceConfig traceConfig = Tracing.getTraceConfig();
       TraceParams activeTraceParams = traceConfig.getActiveTraceParams();
       traceConfig.updateActiveTraceParams(activeTraceParams.toBuilder().setSampler(Samplers.alwaysSample()).build());

       // Get the global singleton Tracer object.
       Tracer tracer = Tracing.getTracer();

      // Create a scoped span, a scoped span will automatically end when closed.
       // It implements AutoClosable, so it'll be closed when the try block ends.
       try (Scope scope = tracer.spanBuilder(spanName).startScopedSpan()) {

       }
       catch(Exception e){
           log.error("Error in creating Tracer");
           e.printStackTrace();
       }

   }

   public void endTraceGracefully(){
       //Gracefully shutdown the exporter, so that it'll flush queued traces to Backend.
       //should be called before spring boot application ends...
       Tracing.getExportComponent().shutdown();
   }
}
