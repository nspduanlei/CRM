package com.apec.crm.domin.entities.func;

import java.util.ArrayList;

/**
 * Created by duanlei on 16/9/29.
 */

public class ListPage<T> {
    private ArrayList<T> content;
    private int totalElements;
    private int totalPages;

    public ArrayList<T> getContent() {
        return content;
    }

    public void setContent(ArrayList<T> content) {
        this.content = content;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
