package org.reactor;

import org.reactor.create.MyEventListener;
import org.reactor.onRequest.MessageProcessor;
import org.reactor.onRequest.MyMessageListener;
import org.reactor.push.SingleThreadEventListener;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class ProgrammingCreatingSequence {
    public static void main(String[] args) {
        useGenerate();
        System.out.println();
        useGenerateWithMutable();
        System.out.println();
        useGenerateWithConsumer();
        System.out.println();
        useCreate();
        System.out.println();
        usePush();
        System.out.println();
        createWithOnRequest();
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

    private static void useGenerateWithConsumer() {
        Flux.generate(AtomicLong::new, (state, sink) -> {
            long i = state.getAndIncrement();
            sink.next("3 x " + i + " = " + 3 * i);
            if (i == 10) {sink.complete();}
            return state;
        }, state -> System.out.println("state: " + state)).blockLast();
    }

    private static void useCreate() {
        EventProcessor<String> myEventProcessor = new EventProcessor<>();
        Flux<String> bridge = Flux.create(sink -> myEventProcessor.register(new MyEventListener<>() {
            @Override
            public void onDataChunk(List<String> chunk) {
                chunk.forEach(sink::next);
            }

            public void processComplete() {
                sink.complete();
            }
        }));
    }

    private static void usePush() {
        EventProcessor<String> myEventProcessor = new EventProcessor<>();
        Flux.push(sink -> myEventProcessor.register(new SingleThreadEventListener<String>() {
            @Override
            public void processError(Throwable throwable) {
                sink.error(throwable);
            }

            @Override
            public void onDataChunk(List<String> chunk) {
                chunk.forEach(sink::next);
            }

            @Override
            public void processComplete() {
                sink.complete();
            }
        }));
    }

    private static void createWithOnRequest() {
        MessageProcessor<String> messageProcessor = new MessageProcessor<>();
        Flux.create(sink -> {
            messageProcessor.register(new MyMessageListener<String>() {
                @Override
                public void onMessage(List<String> messages) {
                    messages.forEach(sink::next);
                }
            });
            sink.onRequest(n -> {
                List<String> messages = messageProcessor.getHistory(n);
                messages.forEach(sink::next);
            });
        });
    }
}