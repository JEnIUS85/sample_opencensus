# sample_opencensus
This is a simple Spring Boot based service hooked with Opencensus tracing libraries.
Examples in this projects shows how to pair Opencensus with SpringBoot microservices and provide tracing and metrics related features

Note: Jaeger to be used as backend



###################
Pre-requisite: 
Download Jaeger setup from https://www.jaegertracing.io/docs/1.6/getting-started/
run all-in-one.exe, which launches Jaeger collector and query services.

##################
build : mvn clean install
Microservice -Endpoint : http://localhost:8080/hello
Jaeger - Endpoing : http://localhost:16686


###################
Example1:
Shows minimum setup required to hook up open census libraries with Spring-Boot project.
A sample microservice exposing /hello rest endpoint and on invocation send tracing details to backend Jaeger.

Example2:
Sample Microservice exposing custom metrics using OpenCensus Libraries.
very useful feature of Opencensus libraries that allows developer to expose custom metrics with in code to backend like Jaeger, Prometheus, etc.
This way we can get more detail insight of application at run time.