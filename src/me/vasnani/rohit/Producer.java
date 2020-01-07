package me.vasnani.rohit;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable {

    private final BlockingQueue<Integer> queue;
    private final ProducerConsumerListener listener;

    private boolean producing = false;

    private final Random random;

    public Producer(BlockingQueue<Integer> queue, ProducerConsumerListener listener) {
        this.queue = queue;
        this.listener = listener;
        this.random = new Random(System.nanoTime());

    }

    public void setProducing(boolean producing) {
        this.producing = producing;
    }


    @Override
    public void run() {
        while (true){
            produce();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void produce() {
        if (queue.remainingCapacity() > 0 && producing){

            try {
                queue.put(random.nextInt(100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            listener.onQueueUpdated(queue);
        }
    }
}
