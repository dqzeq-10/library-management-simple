package com.example.librarymanagement.model;

import java.time.LocalDate;
import java.util.Map;

public class Report {
    private String name;
    private LocalDate generatedDate;
    private ReportType type;
    private Map<String, Object> data;

    //enum
    public enum ReportType {
        BOOK_CIRCULATION,
        MEMBER_ACTIVITY,
        OVERDUE_BOOKS,
        POPULAR_BOOKS,
        FINE_COLLECTION
    }

    public Report(){

    }

    public Report(String name, ReportType type, Map<String, Object> data) {
        this.name = name;
        this.generatedDate = LocalDate.now();
        this.type = type;
        this.data = data;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getGeneratedDate() {
        return generatedDate;
    }

    public void setGeneratedDate(LocalDate generatedDate) {
        this.generatedDate = generatedDate;
    }

    public ReportType getType() {
        return type;
    }

    public void setType(ReportType type) {
        this.type = type;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
