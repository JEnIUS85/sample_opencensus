This code shows minimum setup requried to hook up open census libraries with Spring-Boot project.

###################
Pre-requisite: 
Download Jaeger setup from https://www.jaegertracing.io/docs/1.6/getting-started/
run all-in-one.exe, which launches Jaeger collector and query services.

##################
build : mvn clean install
Microservice -Endpoint : http://localhost:8080/hello
Jaeger - Endpoing : http://localhost:16686


>> MainClass created Jaeger Exporter object to push the traces to backend Jaeger server at TCP port 14268.

    JaegerTraceExporter.createAndRegister("http://localhost:14268/api/traces", "sample_hello_service");

>> you can create Different Exporter (like prometheus ) based upon backed system chosen for your application.

>> Once the service started, hit the endpoint http://localhost:8080/hello and check the trace in Jaeger at the endpoint http://localhost:16686