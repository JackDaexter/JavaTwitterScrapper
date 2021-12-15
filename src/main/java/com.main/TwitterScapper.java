package com.main;

import com.argument.ReadArgument;
import com.pagemanager.LoadPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import picocli.CommandLine;

import java.util.Map;

public class TwitterScapper {
    public static void main(String[] args) {
        //ChromeOptions chromeOptions = new ChromeOptions();
        final String researchLink = "https://twitter.com/search-advanced?lang=en";
        final String link = "https://twitter.com/search?";
        WebDriver driver = new ChromeDriver();

        try {
            var arguments = parseArguments(args);
            LoadPage loadPage = new LoadPage(link,arguments);
            String relink = loadPage.getLink();
            System.out.println(relink);
            driver.get(relink);

            Thread.sleep(10000);

        }catch(Exception e){
            System.err.println("Error : " + e);
        }finally {
            driver.close();
        }
    }


    public static Map<String,String> parseArguments(String[] args){
        CommandLine commandLine = new CommandLine(new ReadArgument());
        commandLine.execute(args);
        return commandLine.getExecutionResult();
    }

}
