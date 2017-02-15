package Models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by NhatAnh on 12/12/16.
 */
public class Material {
    private String title, isbn, author, due_date;
    private Integer remain;
    private Integer material_id;

    public Material(String title, String isbn, String author, Integer remain, Integer material_id, String due_date) {
        this.title = title;
        this.isbn = isbn;
        this.author = author;
        this.remain = remain;
        this.material_id = material_id;
        this.due_date = due_date;
    }

    public Material(ResultSet materialQuery) {
        try {
                this.title = materialQuery.getString("name");
                this.isbn = materialQuery.getString("isbn");
                this.author = materialQuery.getString("author");
                this.remain = materialQuery.getInt("remain");
                this.material_id = materialQuery.getInt("material_id");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

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

    public Integer getMaterial_id() {
        return material_id;
    }

    public void setMaterial_id(Integer material_id) {
        this.material_id = material_id;
    }

    public String getDue_date() {
        return due_date;
    }

    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }
}
