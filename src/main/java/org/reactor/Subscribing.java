package org.reactor;

import reactor.core.publisher.Flux;

public class Subscribing {
    public static void main(String[] args) {
        simpleFlux();
        System.out.println();
        subscribeWithLambda();
        System.out.println();
        subscribeWithErrorHandling();
        System.out.println();
        subscribeWithCompletionHandling();
        System.out.println();
        subscribeWithSampleSubscriber();
    }

    private static void simpleFlux() {
        Flux<Integer> integers = Flux.range(1, 3);
        integers.subscribe();
    }

    private static void subscribeWithLambda() {
        Flux<Integer> integers = Flux.range(1, 3);
        integers.subscribe(i -> System.out.println(i));
    }

    private static void subscribeWithErrorHandling() {
        Flux<Integer> integers = Flux.range(1, 4)
                .map(i -> {
                    if (i <= 3) return i;
                    throw new RuntimeException("Got to 4");
                });
        integers.subscribe(i -> System.out.println(i), error -> System.err.println("Error: " + error));
    }

    private static void subscribeWithCompletionHandling() {
        Flux<Integer> integers = Flux.range(1, 4);
        integers.subscribe(i -> System.out.println(i),
                           error -> System.out.println("Error: " + error),
                           () -> System.out.println("Done"));
    }

    private static void subscribeWithSampleSubscriber() {
        SampleSubscriber<Integer> sampleSubscriber = new SampleSubscriber<>();
        Flux<Integer> ints = Flux.range(1, 4);
        ints.subscribe(sampleSubscriber);
    }
}
