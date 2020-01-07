package me.vasnani.rohit;

import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable {
    private final BlockingQueue<Integer> queue;
    private final ProducerConsumerListener listener;
    private final Listener consumerListener;

    private boolean consuming = false;

    public Consumer(BlockingQueue<Integer> queue, ProducerConsumerListener listener, Listener consumerListener) {
        this.queue = queue;
        this.listener = listener;
        this.consumerListener = consumerListener;
    }

    private void consume(){
        if (queue.size() > 0 && consuming){
            try {
                int n = queue.take();
                consumerListener.onConsume(n);
                listener.onQueueUpdated(queue);
            } catch (InterruptedException e) {
                System.out.println("Consumer interrupted");
            }
        }
    }

    public void setConsuming(boolean consuming) {
        this.consuming = consuming;
    }

    @Override
    public void run() {
        while (true){
            consume();
            try {
                Thread.sleep(110);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    interface Listener{
        void onConsume(int number);
    }

}
