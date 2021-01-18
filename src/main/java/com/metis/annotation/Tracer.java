package com.metis.annotation;

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
        public void start() {
            System.out.println("---start a span---");
        }

        public void end() {
            System.out.println("---span finish---");
            // todo: save span in db
        }
    }
}