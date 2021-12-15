package com.loadpage;

import java.util.Objects;

public class LoadPage {

    private String link;
    private String parameters;

    public LoadPage(String link, String parameters){
        link = Objects.requireNonNull(link);
        parameters = Objects.requireNonNull(parameters);
    }


}
