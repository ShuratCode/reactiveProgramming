package org.reactor.push;

import org.reactor.create.MyEventListener;

public interface SingleThreadEventListener<T> extends MyEventListener<T> {

    void processError(Throwable throwable);
}
