package org.reactor.onRequest;

import java.util.List;

public interface MyMessageListener<T> {

    public void onMessage(List<T> messages);
}
