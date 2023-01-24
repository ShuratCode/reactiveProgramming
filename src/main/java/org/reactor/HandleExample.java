package org.reactor;

import reactor.core.publisher.Flux;

public class HandleExample {

    public static void main(String[] args) {
        Flux.just(-1, 30, 13, 9, 20).handle((i, sink) -> {
            String letter = alphabet(i);
            if (letter != null) {
                sink.next(letter);
            }
        }).subscribe(System.out::println);
    }

    private static String alphabet(int letterNumber) {
        if (letterNumber < 1 || letterNumber > 26) {
            return null;
        }
        int letterIndexAscii = 'A' + letterNumber - 1;
        return "" + (char) letterIndexAscii;
    }
}
