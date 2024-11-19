package com.liter.demo;

public class Book {
    private String id;
    private String title;
    private String[] authors;
    private String publisher;
    private String publishedDate;
    private String description;
    private int pageCount;

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String[] getAuthors() { return authors; }
    public void setAuthors(String[] authors) { this.authors = authors; }

    public String getPublisher() { return publisher; }
    public void setPublisher(String publisher) { this.publisher = publisher; }

    public String getPublishedDate() { return publishedDate; }
    public void setPublishedDate(String publishedDate) { this.publishedDate = publishedDate; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getPageCount() { return pageCount; }
    public void setPageCount(int pageCount) { this.pageCount = pageCount; }

    @Override
    public String toString() {
        return "Title: " + title + "\n" +
                "Author(s): " + String.join(", ", authors != null ? authors : new String[]{}) + "\n" +
                "Publisher: " + publisher + "\n" +
                "Published Date: " + publishedDate + "\n" +
                "Pages: " + pageCount + "\n" +
                "Description: " + (description != null ? description.substring(0, Math.min(200, description.length())) + "..." : "N/A") + "\n";
    }
}
