package org.reactor;

public interface SingleThreadEventListener<T> extends MyEventListener<T> {

    void processError(Throwable throwable);
}
