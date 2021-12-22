package com.main;

import com.argument.ReadArgument;
import com.threading.TwitterScrapperThread;
import picocli.CommandLine;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class TwitterScapper {

    public static void main(String[] args) {
        //ChromeOptions chromeOptions = new ChromeOptions();
        final String researchLink = "https://twitter.com/search-advanced?lang=en";
        final String link = "https://twitter.com/search?";
        final BlockingQueue<String> tweetList = new LinkedBlockingDeque<>(300);

        var arguments = parseArguments(args);
        var twitterScrapperThread = TwitterScrapperThread.getInstance();
        twitterScrapperThread.loadData(link, arguments);
        twitterScrapperThread.start();


    }

    public static Map<String,String> parseArguments(String[] args){
        CommandLine commandLine = new CommandLine(new ReadArgument());
        commandLine.execute(args);
        return commandLine.getExecutionResult();
    }


}
