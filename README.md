# sample_opencensus
sample spring boot based service hooked with opencensus tracing libraries. Jaeger will be used as backend

This code shows minimum setup requried to hook up open census libraries with Spring-Boot project. 

###################
Pre-requisite: 
Download Jaeger setup from https://www.jaegertracing.io/docs/1.6/getting-started/
run all-in-one.exe, which launches Jaeger collector and query services.

##################
build : mvn clean install
Microservice -Endpoint : http://localhost:8080/hello
Jaeger - Endpoing : http://localhost:16686
