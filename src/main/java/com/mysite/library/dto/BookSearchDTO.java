package com.mysite.library.dto;

public class BookSearchDTO {

    private String searchBy;
    private String keyword;

    // Getters and setters
    public String getSearchBy() {
        return searchBy;
    }

    public void setSearchBy(String searchBy) {
        this.searchBy = searchBy;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
