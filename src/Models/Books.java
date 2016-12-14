package Models;

/**
 * Created by NhatAnh on 12/12/16.
 */
public class Books {
    private String title, isbn, author;
    private Integer remain;

    public Books(String title, String isbn, String author, Integer remain) {
        this.title = title;
        this.isbn = isbn;
        this.author = author;
        this.remain = remain;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getISBN() {
        return isbn;
    }

    public void setISBN(String isbn) { this.isbn = isbn; }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getRemain() {
        return remain;
    }

    public void setRemain(Integer remain) {
        this.remain = remain;
    }
}
