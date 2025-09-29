package com.library.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Book类的单元测试
 * Unit tests for Book class
 */
public class BookTest {
    
    @Test
    public void testBookCreation() {
        Book book = new Book("B001", "Java编程思想", "Bruce Eckel", "978-0131872486", "编程");
        
        assertEquals("B001", book.getId());
        assertEquals("Java编程思想", book.getTitle());
        assertEquals("Bruce Eckel", book.getAuthor());
        assertEquals("978-0131872486", book.getIsbn());
        assertEquals("编程", book.getCategory());
        assertTrue(book.isAvailable());
    }
    
    @Test
    public void testBookDefaultConstructor() {
        Book book = new Book();
        assertTrue(book.isAvailable()); // 默认应该可借
    }
    
    @Test
    public void testBookSetters() {
        Book book = new Book();
        
        book.setId("B002");
        book.setTitle("设计模式");
        book.setAuthor("GoF");
        book.setPublishDate(LocalDate.of(1995, 1, 1));
        book.setAvailable(false);
        
        assertEquals("B002", book.getId());
        assertEquals("设计模式", book.getTitle());
        assertEquals("GoF", book.getAuthor());
        assertEquals(LocalDate.of(1995, 1, 1), book.getPublishDate());
        assertFalse(book.isAvailable());
    }
    
    @Test
    public void testBookEquality() {
        Book book1 = new Book("B001", "Java编程思想", "Bruce Eckel", "978-0131872486", "编程");
        Book book2 = new Book("B001", "不同标题", "不同作者", "不同ISBN", "不同分类");
        Book book3 = new Book("B002", "Java编程思想", "Bruce Eckel", "978-0131872486", "编程");
        
        assertEquals(book1, book2); // 相同ID应该相等
        assertNotEquals(book1, book3); // 不同ID应该不相等
    }
    
    @Test
    public void testBookToString() {
        Book book = new Book("B001", "Java编程思想", "Bruce Eckel", "978-0131872486", "编程");
        String expected = "Book{id='B001', title='Java编程思想', author='Bruce Eckel', isbn='978-0131872486', category='编程', available=true}";
        assertEquals(expected, book.toString());
    }
}