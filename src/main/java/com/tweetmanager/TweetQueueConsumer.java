package com.tweetmanager;

import java.util.Objects;
import java.util.concurrent.BlockingQueue;

public class TweetQueueConsumer {

    private final BlockingQueue<String> queue;
    private int MAX_TWEET = 20;

    public TweetQueueConsumer(BlockingQueue<String> queue, int max_Tweet){
        this.queue = Objects.requireNonNull(queue);
    }

    public void consume() throws InterruptedException {
        int nb_tweet = 0;
        synchronized (queue) {

            while(queue.isEmpty()) {
                queue.wait();
            }

            queue.notify();

            System.out.println(queue.take());

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
}
