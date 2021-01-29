package com.metis.annotation;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @Description: Tracer
 * @Author: YiHui
 * @Date: 2021-01-18 21:53
 * @Version: ing
 */
public class Tracer {

    public static Tracer newTracer() {
        return new Tracer();
    }

    public Span newSpan() {
        return new Span();
    }


    public static class Span {
        private final AtomicLong spanId = new AtomicLong();
        public void start() {
            spanId.incrementAndGet();
            System.out.println("---start a span---");
        }

        public void end() {
            spanId.incrementAndGet();
            System.out.println("---span finish---");
            // todo: save span in db
        }
    }
}