package com.opencensus.java.controller;

import io.opencensus.common.Scope;
import io.opencensus.stats.*;
import io.opencensus.tags.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CustomMetrics {
    private static long Count = 0;

    // The latency in milliseconds
    private static final Measure.MeasureDouble M_LATENCY_MS = Measure.MeasureDouble.create("repl/latency", "The latency in milliseconds per /countHit request", "ms");

    // Counts/groups the /countHit requested.
    private static final Measure.MeasureLong M_COUNT = Measure.MeasureLong.create("repl/line_lengths", "Number of time /countHit request made", "By");

    // The tag "method"
    private static final TagKey KEY_METHOD = TagKey.create("method");
    private static final TagKey KEY_STATUS = TagKey.create("status");
    private static final TagKey KEY_ERROR = TagKey.create("error");

    private static final Tagger tagger = Tags.getTagger();
    private static final StatsRecorder statsRecorder = Stats.getStatsRecorder();

    private static void recordStat(Measure.MeasureLong ml, Long n) {
        statsRecorder.newMeasureMap().put(ml, n);
    }

    private static void recordTaggedStat(TagKey key, String value, Measure.MeasureLong ml, Long n) {
        TagContext tctx = tagger.emptyBuilder().put(key, TagValue.create(value)).build();
        try (Scope ss = tagger.withTagContext(tctx)) {
            statsRecorder.newMeasureMap().put(ml, n).record();
        }
    }

    private static void recordTaggedStat(TagKey key, String value, Measure.MeasureDouble md, Double d) {
        TagContext tctx = tagger.emptyBuilder().put(key, TagValue.create(value)).build();
        try (Scope ss = tagger.withTagContext(tctx)) {
            statsRecorder.newMeasureMap().put(md, d).record();
        }
    }

    private static void recordTaggedStat(TagKey[] keys, String[] values, Measure.MeasureDouble md, Double d) {
        TagContextBuilder builder = tagger.emptyBuilder();
        for (int i = 0; i < keys.length; i++) {
            builder.put(keys[i], TagValue.create(values[i]));
        }
        TagContext tctx = builder.build();

        try (Scope ss = tagger.withTagContext(tctx)) {
            statsRecorder.newMeasureMap().put(md, d).record();
        }
    }

    private static void registerAllViews(){
        // Define the count aggregation
        Aggregation countAggregation = Aggregation.Count.create();

        // So tagKeys
        List<TagKey> noKeys = new ArrayList<TagKey>();

        View[] views = new View[]{
                View.create(View.Name.create("HitCount"), "The Count of RestRequest", M_COUNT, countAggregation, noKeys),
               // View.create(View.Name.create("HitCount"), "The Count of RestRequest", M_COUNT, lengthsDistribution, noKeys)
        };

        ViewManager vmgr = Stats.getViewManager();

        // Then finally register the views
        for (View view : views) {
            vmgr.registerView(view);
        }
    }

    @RequestMapping(value = "/countHit", method = RequestMethod.GET)
    public ResponseEntity<String> countHit(){

        //sleep for random time -- 10ms to 1 sec..
        try{

            double delay= 100+ ((1000-100)*Math.random());
            System.out.println("Delay = " + delay);
            Thread.sleep(((Double) delay).longValue());
        }catch(Exception e){
            e.printStackTrace();
        }
        //increment count..
        Count++;
        recordStat(M_COUNT,Count);

        return new ResponseEntity<>("Total Count = " + Count, HttpStatus.OK);
    }//countHit() called..
}
