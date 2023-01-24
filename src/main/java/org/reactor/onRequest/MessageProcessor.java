package org.reactor.onRequest;

import java.util.ArrayList;
import java.util.List;

public class MessageProcessor<T> {

    private List<MyMessageListener<T>> listeners;

    public void register(MyMessageListener<T> messageListener) {
        listeners.add(messageListener);
    }

    public List<T> getHistory(long requestSize) {
        return new ArrayList<>((int) requestSize);
    }
}
