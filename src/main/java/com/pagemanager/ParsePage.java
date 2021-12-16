package com.pagemanager;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class ParsePage {
    private final WebDriver driver;
    private final ArrayList<String> array = new ArrayList<>();

    public ParsePage(WebDriver driver){
        this.driver = driver;
    }

    public void retrieveTweets(String link){
        JavascriptExecutor jsa = (JavascriptExecutor) driver;
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(13));
        driver.get(link);

        try{
            WebElement element = driver.findElement(By.cssSelector("#react-root > div > div > div.css-1dbjc4n.r-18u37iz.r-13qz1uu.r-417010 > main > div > div > div > div > div > div:nth-child(2) > div > div > section > div > div"));
            List<WebElement> allElement = element.findElements(By.cssSelector("*"));
            System.out.println("Size is " + allElement.size());
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));

            allElement.forEach(this::readTweetMessage);
        }catch (Exception e){
            System.err.println("Exception :" + e);
            System.err.println("Element not find");
        }
    }

    public void readTweetMessage(WebElement childElement){
        if(childElement.findElements(By.tagName("article")).size() != 0){
            System.out.println("ARTICLE");
            System.out.println(childElement.getAttribute("innerText"));
        }
    }



}
