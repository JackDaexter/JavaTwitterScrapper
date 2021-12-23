package com.pagemanager;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.BlockingQueue;

public class TweetProducer implements Runnable{
    private final WebDriver driver;
    private final ArrayList<String> array = new ArrayList<>();
    private final BlockingQueue<String> tweetList;
    private final String link;
    private final int MAX_TWEET = 30;

    public TweetProducer(WebDriver driver, String link, BlockingQueue<String> queue){
        this.driver = Objects.requireNonNull(driver);
        this.link = Objects.requireNonNull(link);
        this.tweetList = queue;
    }

    public void retrieveTweets(){
        JavascriptExecutor jsa = (JavascriptExecutor) driver;
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get(link);

        /* Avoid error because of DOM element disapear/reapear*/
        try{
            parseWebElement();
        }catch (Exception e){
            parseWebElement();
        }
    }

    private void parseWebElement(){
        WebElement element = driver.findElement(By.cssSelector("#react-root > div > div > div.css-1dbjc4n.r-18u37iz.r-13qz1uu.r-417010 > main > div > div > div > div > div > div:nth-child(2) > div > div > section > div > div"));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));

        element.findElements(By.cssSelector("*"))
                .stream()
                .filter(x -> x.findElements(By.tagName("article")).size() != 0)
                .map(x -> x.getAttribute("innerText"))
                .limit(MAX_TWEET)
                .forEach(this::handleBlockingQueue);

        driver.close();
    }

    public void handleBlockingQueue(String element){
        if(element != null) {
            try {
                System.out.println("THREAD : " + Thread.currentThread());
                synchronized (tweetList){
                    while (tweetList.size() == MAX_TWEET){
                        System.out.println("WAIT");
                        tweetList.wait();
                        System.out.println("END WAIT");
                    }
                    tweetList.notify();
                    tweetList.put(element);
                }
            }catch (Exception e) {
                System.err.println("Error : " + e);
            }
        }
    }

    @Override
    public void run() {
        retrieveTweets();
    }
}
