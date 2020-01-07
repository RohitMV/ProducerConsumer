package me.vasnani.rohit;

import java.util.Queue;

public interface ProducerConsumerListener {
    void onQueueUpdated(Queue<Integer> integers);
}
