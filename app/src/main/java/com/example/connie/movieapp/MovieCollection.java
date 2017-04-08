package com.example.connie.movieapp;


import java.io.IOException;
import java.util.ArrayList;
/**
 * Created by Connie on 1/30/2017.
 */
public class MovieCollection {
    private int page;
    MovieData[] results;
    public MovieCollection() throws IOException {
    }
    /**
     * @return page of list of movies
     */
    public int getPage() {
        return page;
    }
    /**
     * @return array of MovieData type
     */
    public MovieData[] getResults() {
        return results;
    }
    /**
     * increases the page
     */
    public void incPage() {
        page++;
    }








}
