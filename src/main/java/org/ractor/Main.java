package org.ractor;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        Flux<String> seq1 = Flux.just("foo", "bar", "footbar"); // Use enumerate to create flux

        List<String> iterable = Arrays.asList("foo", "bar", "foobar"); // Create a collection
        Flux<String> seq2 = Flux.fromIterable(iterable); // use the collection to create a flux

        Mono<String> noData = Mono.empty(); // creates an empty mono with no data to pass
        Mono<String> data = Mono.just("foo"); // creates a single element in the stream.
        Flux<Integer> numbersFromFiveToSeven = Flux.range(5, 3); // first parameter is start, second in number of items
    }
}
