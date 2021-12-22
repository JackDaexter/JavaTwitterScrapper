package com.threading;

import com.main.TwitterScapper;
import com.pagemanager.LoadPage;
import com.pagemanager.ParsePage;
import com.tweetmanager.TweetQueueConsumer;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.sql.Array;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class TwitterScrapperThread {
    private static final TwitterScrapperThread instance;
    private Map<String,String> parameters;
    private ArrayList<WebDriver> webDriver;
    private BlockingQueue<String> queue = new LinkedBlockingDeque<>(400);
    private String link;

    static {
        instance = new TwitterScrapperThread();
    }
    private TwitterScrapperThread(){}

    public static TwitterScrapperThread getInstance(){
        return instance;
    }

    public void loadData(String link, Map<String,String> parameters){
        this.parameters = Collections.unmodifiableMap(parameters);
        this.link = Objects.requireNonNull(link);
    }

    public void  start(){
        int nbOfWeeks = nbOfWeeks(parameters.get("begin"),parameters.get("end"));
        LocalDate dateBegin = LocalDate.parse(parameters.get("begin"));
        LocalDate dateEnd = LocalDate.parse(parameters.get("end"));
        Map<WebDriver,LoadPage> listOfLoadPage = new HashMap<>(nbOfWeeks);
        ArrayList<ParsePage> listOfParsePage = new ArrayList<>(nbOfWeeks);
        webDriver = new ArrayList<>(nbOfWeeks);

        startThread(listOfLoadPage,listOfParsePage,nbOfWeeks,dateBegin);

    }

    private int nbOfWeeks(String begin, String end){
        LocalDate dateBegin = LocalDate.parse(begin);
        LocalDate dateEnd = LocalDate.parse(end);

        return Period.between(dateBegin, dateEnd).getDays() / 7;
    }

    private void startThread(Map<WebDriver,LoadPage> listOfLoadPage, ArrayList<ParsePage> listOfParsePage, int nbOfWeeks,LocalDate dateBegin){
        TweetQueueConsumer tqc = new TweetQueueConsumer(queue);

        do{
            listOfLoadPage.put(new ChromeDriver(),new LoadPage(link,dateBegin.toString(),dateBegin.plusDays(7).toString(),parameters));
            nbOfWeeks -= 7;
        }while (nbOfWeeks > 0);

        System.out.println(listOfLoadPage.size());
        listOfLoadPage.forEach( (d,l) -> {
            String relink = l.getLink();
            Thread t = new Thread(new ParsePage(d,relink,queue));
            t.start();
        });

        tqc.readTweet();
    }

}
