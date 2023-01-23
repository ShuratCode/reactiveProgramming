package org.reactor;

import java.util.ArrayList;
import java.util.List;

public class EventProcessor<T> {
    public final List<MyEventListener<T>> listeners = new ArrayList<>();
    public void register(MyEventListener<T> eventListener){
        listeners.add(eventListener);
    }
}
