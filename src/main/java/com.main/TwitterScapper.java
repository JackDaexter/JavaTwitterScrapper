package com.main;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;

public class TwitterScapper {


    public static void main(String[] args) {

        TwitterScapper twitter = new TwitterScapper();
        twitter.listElement("C:/Users/franc/Music/napsterStream/chrome-win");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setBinary("C:/Users/franc/Music/napsterStream/chrome-win");
        WebDriver driver = new ChromeDriver(chromeOptions);

        try {
            driver.get("https://twitter.com");

        }catch(Exception e){
            System.err.println("Error : " + e);
        }finally {
            driver.close();
        }
    }

    public void listElement(String path){
        File file = new File(path);
        String[] elem = file.list();
        if(elem != null){
            for (String x : elem) {
                System.out.println(x);
            }
        }

    }



}
