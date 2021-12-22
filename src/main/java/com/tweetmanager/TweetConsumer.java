package com.tweetmanager;

import java.util.Objects;
import java.util.concurrent.BlockingQueue;

public class TweetConsumer implements Runnable{

    private final BlockingQueue<String> queue;
    private int MAX_TWEET = 20;

    public TweetConsumer(BlockingQueue<String> queue){
        this.queue = Objects.requireNonNull(queue);
    }

    public void consume() throws InterruptedException {
        int nb_tweet = 0;
        synchronized (queue) {

            System.out.print("consume : ");
            while(queue.isEmpty()) {
                System.out.println("empty");
                queue.wait();
            }

            queue.notify();

            System.out.println(queue.take().getBytes());

        }
    }

    public void readTweet() {
        int nbOfTweetRead = 0;

        while(nbOfTweetRead < MAX_TWEET){

            try {
                consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            nbOfTweetRead++;
        }
    }

    @Override
    public void run() {
        readTweet();
    }
}
