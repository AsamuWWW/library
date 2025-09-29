package com.library.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDate;
import java.util.Objects;

/**
 * 图书实体类
 * Book entity class representing a book in the library system
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Book {
    private String id;           // 图书ID
    private String title;        // 书名
    private String author;       // 作者
    private String isbn;         // ISBN号
    private String category;     // 分类
    private LocalDate publishDate; // 出版日期
    private boolean available;   // 是否可借
    private String description;  // 描述
    
    // 默认构造函数
    public Book() {
        this.available = true; // 默认可借
    }
    
    // 带参数的构造函数
    public Book(String id, String title, String author, String isbn, String category) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.category = category;
        this.available = true;
    }
    
    // Getter 和 Setter 方法
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public void setAuthor(String author) {
        this.author = author;
    }
    
    public String getIsbn() {
        return isbn;
    }
    
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public LocalDate getPublishDate() {
        return publishDate;
    }
    
    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }
    
    public boolean isAvailable() {
        return available;
    }
    
    public void setAvailable(boolean available) {
        this.available = available;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return String.format("Book{id='%s', title='%s', author='%s', isbn='%s', category='%s', available=%s}",
                id, title, author, isbn, category, available);
    }
}