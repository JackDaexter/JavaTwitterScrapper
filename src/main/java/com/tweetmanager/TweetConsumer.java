package com.tweetmanager;

import java.util.Objects;
import java.util.concurrent.BlockingQueue;

public class TweetConsumer implements Runnable{

    private final BlockingQueue<String> queue;
    private final int MAX_TWEET;

    public TweetConsumer(BlockingQueue<String> queue,int max_tweet){
        this.queue = Objects.requireNonNull(queue);
        this.MAX_TWEET = max_tweet;
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
            System.out.println("SIZE : " + nbOfTweetRead);
        }
    }

    @Override
    public void run() {
        readTweet();
    }
}
