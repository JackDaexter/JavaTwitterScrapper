package com.pagemanager;

import java.util.*;


public class LoadPage {
    private final StringBuilder link = new StringBuilder();
    private String begin;
    private String end;
    private Map<String, String> parameters;

    public LoadPage(String link,String begin,String end, Map<String,String> parameters){
        Objects.requireNonNull(link);
        this.link.append(link);
        this.begin = Objects.requireNonNull(begin);
        this.end = Objects.requireNonNull(end);
        this.parameters = Collections.unmodifiableMap(parameters);
    }

    public String getLink(){
        StringBuilder newString = new StringBuilder();

        newString.append(link);
        parameters.forEach((x,y) -> newString.append(generateLink(x,y)));

        newString.append("%20until%3A").append(begin).append("&");
        newString.append("%20until%3A").append(end).append("&");

        newString.append("src=typed_query");

        return newString.toString();
    }

    public String generateLink(String type, String value){
        if(value != null){
            switch (type){
                case "crypto": return "q="+value+"&";
                case "lang": return "lang="+value+"&";
                /*case "begin": return "%20until%3A"+value+"&";
                case "end":return "%20since%3A"+value+"&";*/
                default: break;
            }
        }
        return "";
    }



}
