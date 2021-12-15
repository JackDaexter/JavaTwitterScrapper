package com.main;

import com.argument.ReadArgument;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import picocli.CommandLine;

public class TwitterScapper {
    public static void main(String[] args) {
        //ChromeOptions chromeOptions = new ChromeOptions();
        final String link = "https://twitter.com";
        WebDriver driver = new ChromeDriver();

        try {
            int commandLine = new CommandLine(new ReadArgument()).execute(args);
            //driver.get();
        }catch(Exception e){
            System.err.println("Error : " + e);
        }finally {
            driver.close();
        }
    }


    public static void parseArguments(String[] args){

    }

}
