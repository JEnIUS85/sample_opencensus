This code shows a sample SpringBoot service exposing custom metrics (using OpenCensus libraries) to a Jaeger Backend


###################
Pre-requisite: 
Download Jaeger setup from https://www.jaegertracing.io/docs/1.6/getting-started/
run all-in-one.exe, which launches Jaeger collector and query services.

##################
build : mvn clean install
Microservice -Endpoint : http://localhost:8080/customMetricEndpoint
Jaeger - Endpoing : http://localhost:16686

>> There are 2 custom metrics exposed :
    a) Count ==> number of time the REST service endpoint invoked
    b) Latency_ms ==> response time (in ms) of REST call.

