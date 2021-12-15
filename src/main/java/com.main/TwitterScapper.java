package com.main;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;

public class TwitterScapper {
    public static void main(String[] args) {

        //ChromeOptions chromeOptions = new ChromeOptions();
        WebDriver driver = new ChromeDriver();

        try {
            driver.get("https://twitter.com");
        }catch(Exception e){
            System.err.println("Error : " + e);
        }finally {
            driver.close();
        }
    }


    public static void parseArguments(String[] args){

    }

}
