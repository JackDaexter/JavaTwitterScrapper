package com.pagemanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.time.Duration;
import java.util.ArrayList;

public class ParsePage {
    private final WebDriver driver;
    private final ArrayList<String> array = new ArrayList<>();

    public ParsePage(WebDriver driver){
        this.driver = driver;
    }

    public void retrieveTweets(String link){
        driver.get(link);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));

        try{
            driver.findElement(By.cssSelector("#react-root > div > div > div.css-1dbjc4n.r-18u37iz.r-13qz1uu.r-417010 > main > div > div > div > div > div > div:nth-child(2) > div > div > section > div > div"));

        }catch (Exception e){
            System.out.println("Exception :" + e);
            System.out.println("Element not find");
        }
    }



}
