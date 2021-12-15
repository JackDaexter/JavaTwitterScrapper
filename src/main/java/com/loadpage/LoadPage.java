package com.loadpage;

import java.util.*;


public class LoadPage {

    private final String link;
    private HashMap<String, String> parameters;

    public LoadPage(String link, HashMap<String,String> parameters){
        this.link = Objects.requireNonNull(link);
        this.parameters = Collections.unmodifiableMap(parameters);
    }


}
