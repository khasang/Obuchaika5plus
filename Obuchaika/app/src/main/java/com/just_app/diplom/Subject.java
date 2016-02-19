package com.just_app.diplom;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)

public class Subject {
    public String name;
    public  ArrayList<Content> content;

}
