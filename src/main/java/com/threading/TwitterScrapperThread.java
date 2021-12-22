package com.threading;

import com.pagemanager.LoadPage;
import com.pagemanager.ParsePage;
import com.tweetmanager.TweetConsumer;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

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

        int month = Period.between(dateBegin, dateEnd).getMonths();
        int days = Period.between(dateBegin, dateEnd).getDays();

        System.out.println("MONTH : " + month);
        System.out.println("DAYS : " + days);
        return (month * 4) + (days / 7) ;
    }

    private void startThread(Map<WebDriver,LoadPage> listOfLoadPage, ArrayList<ParsePage> listOfParsePage, int nbOfWeeks,LocalDate dateBegin){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");

        do{
            listOfLoadPage.put(new ChromeDriver(options),new LoadPage(link,dateBegin.toString(),dateBegin.plusDays(7).toString(),parameters));
            nbOfWeeks--;
        }while (nbOfWeeks > 0);

        System.out.println("SIZE : " + listOfLoadPage.size());

        System.out.println(listOfLoadPage.size());
        listOfLoadPage.forEach( (d,l) -> {
            String relink = l.getLink();
            Thread t = new Thread(new ParsePage(d,relink,queue));
            t.start();
        });

        Thread consumerThread = new Thread(new TweetConsumer(queue));
        consumerThread.start();
    }

}
