package me.vasnani.rohit;

import javax.swing.*;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main implements ProducerConsumerListener {

    private final static int QUEUE_CAPACITY = 5;

    private JLabel labelConsumer1Field;
    private JLabel labelConsumer2Field;
    private JLabel labelQueueSizeField;
    private JLabel labelQueueItemField;

    private JLabel labelConsumer1;
    private JLabel labelQueueSize;
    private JLabel labelQueueItems;
    private JLabel labelConsumer2;

    private JButton startButton;
    private JButton stopButton;

    private JPanel mainPanel;

    public Main() {

    }

    public static void main(String[] args){

        Main mainClass = new Main();
        JFrame frame = new JFrame("Multithreading");
        frame.setContentPane(mainClass.mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(QUEUE_CAPACITY);
        Producer producer = new Producer(queue, mainClass);
        Consumer consumer1 = new Consumer(queue, mainClass, number -> mainClass.labelConsumer1.setText(String.valueOf(number)));
        Consumer consumer2 = new Consumer(queue, mainClass, number -> mainClass.labelConsumer2.setText(String.valueOf(number)));


        Thread producerThread = new Thread(producer);
        Thread consumer1Thread = new Thread(consumer1);
        Thread consumer2Thread = new Thread(consumer2);

        producerThread.start();
        consumer1Thread.start();
        consumer2Thread.start();


        mainClass.startButton.addActionListener(actionEvent -> {
            producer.setProducing(true);
            consumer1.setConsuming(true);
            consumer2.setConsuming(true);
        });
        mainClass.stopButton.addActionListener(actionEvent -> {
            producer.setProducing(false);
            consumer1.setConsuming(false);
            consumer2.setConsuming(false);
        });

    }

    @Override
    public void onQueueUpdated(Queue<Integer> integers) {
        labelQueueSize.setText(String.valueOf(integers.size()));
        StringBuilder builder = new StringBuilder();
        integers.forEach(e -> builder.append(e).append(" "));

        labelQueueItems.setText(builder.toString());
    }

}
