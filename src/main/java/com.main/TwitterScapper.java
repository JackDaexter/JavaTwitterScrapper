package com.main;

import com.argument.ReadArgument;
import com.loadpage.LoadPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import picocli.CommandLine;

import java.util.HashMap;
import java.util.List;

public class TwitterScapper {
    public static void main(String[] args) {
        //ChromeOptions chromeOptions = new ChromeOptions();
        final String link = "https://twitter.com";
        WebDriver driver = new ChromeDriver();

        try {
            var arguments = parseArguments(args);
            LoadPage loadPage = new LoadPage(link,arguments);

        }catch(Exception e){
            System.err.println("Error : " + e);
        }finally {
            driver.close();
        }
    }


    public static HashMap<String,String> parseArguments(String[] args){
        CommandLine commandLine = new CommandLine(new ReadArgument());
        commandLine.execute(args);
        return commandLine.getExecutionResult();
    }

}
