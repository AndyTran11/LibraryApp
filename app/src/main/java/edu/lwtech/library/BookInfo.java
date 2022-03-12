// Andy Tran
package edu.lwtech.library;

import java.util.ArrayList;

//Model class for book items
public class BookInfo {

    private String bookID;
    private String title;
    private String subtitle;
    private ArrayList<String> authors;
    private String datePublished;
    private String description;
    private int pageCount;

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public ArrayList<String> getAuthors() { return authors; }

    public void setAuthors(ArrayList<String> authors) {
        this.authors = authors;
    }

    public String getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(String datePublished) {
        this.datePublished = datePublished;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public BookInfo(String bookID, String title, String subtitle, ArrayList<String> authors,
                    String datePublished, String description, int pageCount) {

        this.bookID = bookID;
        this.title = title;
        this.subtitle = subtitle;
        this.authors = authors;
        this.datePublished = datePublished;
        this.description = description;
        this.pageCount = pageCount;
    }
}
