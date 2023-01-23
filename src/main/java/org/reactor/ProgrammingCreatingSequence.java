package org.reactor;

import reactor.core.publisher.Flux;

import java.util.concurrent.atomic.AtomicLong;

public class ProgrammingCreatingSequence {
    public static void main(String[] args) {
        useGenerate();
        System.out.println();
        useGenerateWithMutable();
    }

    private static void useGenerate() {
        Flux<String> flux = Flux.generate(() -> 0, (state, sink) -> {
            sink.next(String.format("3 X %s = %d", state, 3 * state));
            if (state == 10) {
                sink.complete();
            }
            return state + 1;
        });
        flux.doOnNext(System.out::println).blockLast();
    }

    private static void useGenerateWithMutable() {
        Flux.generate(AtomicLong::new, (state, sink) -> {
            long i = state.getAndIncrement();
            sink.next(String.format("3 X %s = %d", state, 3 * i));
            if (i == 10) {
                sink.complete();
            }
            return state;
        }).doOnNext(System.out::println).blockLast();
    }
}
