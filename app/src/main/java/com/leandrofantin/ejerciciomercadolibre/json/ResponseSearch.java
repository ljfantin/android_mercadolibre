package com.leandrofantin.ejerciciomercadolibre.json;


import java.util.ArrayList;


/**
 * @author lfantin (lea)
 * */

public class ResponseSearch implements ResponseService{

    private String query = "";

    private ArrayList<Product> results = new ArrayList<Product>();

    private Pager pager = null;

    public Pager getPager() {
        return pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }

    public ArrayList<Product> getResults() {
        return results;
    }

    public void setResults(ArrayList<Product> results) {
        this.results = results;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
