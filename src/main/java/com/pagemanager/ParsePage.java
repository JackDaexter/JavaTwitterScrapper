package com.pagemanager;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.BlockingQueue;

public class ParsePage implements Runnable{
    private final WebDriver driver;
    private final ArrayList<String> array = new ArrayList<>();
    private final BlockingQueue<String> tweetList;
    private final String link;

    public ParsePage(WebDriver driver,String link,BlockingQueue<String> queue){
        this.driver = Objects.requireNonNull(driver);
        this.link = Objects.requireNonNull(link);
        this.tweetList = queue;
    }

    public void retrieveTweets(){
        JavascriptExecutor jsa = (JavascriptExecutor) driver;
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(25));
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
                .forEach(this::handleBlockingQueue);
    }

    public void handleBlockingQueue(String element){
        if(element != null) {
            try {
                tweetList.put(element);
            } catch (Exception e) {
                System.err.println("Error : " + e);
            }
        }
    }

    public void tweetSlicer(String tweet){

    }

    public void tweetWithCitationSlicer(String tweet){

    }


    @Override
    public void run() {
        retrieveTweets();
    }
}
