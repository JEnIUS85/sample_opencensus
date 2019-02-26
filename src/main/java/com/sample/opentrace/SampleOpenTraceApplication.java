package com.sample.opentrace;

import io.jaegertracing.Configuration;
import io.jaegertracing.internal.samplers.ConstSampler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SampleOpenTraceApplication {

    @Bean
    public io.opentracing.Tracer jaegerTracer(){
       // return new Configuration("helloService").getTracer();

        Configuration.SamplerConfiguration samplerConfiguration = new Configuration.SamplerConfiguration()
                .withType(ConstSampler.TYPE)
                .withParam(1);

        Configuration.SenderConfiguration senderConfiguration = new Configuration.SenderConfiguration()
                .withEndpoint("http://localhost:14268/api/traces");

        Configuration.ReporterConfiguration reporterConfiguration = new Configuration.ReporterConfiguration()
                .withLogSpans(true)
                .withFlushInterval(1000)
                .withMaxQueueSize(1000)
                .withSender(senderConfiguration);

        return new Configuration("HelloService")
                .withSampler(samplerConfiguration)
                .withReporter(reporterConfiguration)
                .getTracer();

		
        //return Configuration.fromEnv().getTracer();
    }
    public static void main(String [] args){
        SpringApplication.run(SampleOpenTraceApplication.class,args);
    }
}
