package com.library.service;

import com.library.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * LibraryServiceImpl类的单元测试
 * Unit tests for LibraryServiceImpl class
 */
public class LibraryServiceImplTest {
    
    private LibraryService libraryService;
    
    @BeforeEach
    public void setUp() {
        libraryService = new LibraryServiceImpl();
        // 清理测试环境
        libraryService.getAllBooks().forEach(book -> libraryService.removeBook(book.getId()));
    }
    
    @Test
    public void testAddBook() {
        Book book = new Book("TEST001", "测试书籍", "测试作者", "123456789", "测试分类");
        
        assertTrue(libraryService.addBook(book));
        assertEquals(1, libraryService.getTotalBooks());
        
        Book foundBook = libraryService.findBookById("TEST001");
        assertNotNull(foundBook);
        assertEquals("测试书籍", foundBook.getTitle());
    }
    
    @Test
    public void testAddDuplicateBook() {
        Book book1 = new Book("TEST001", "测试书籍1", "测试作者1", "123456789", "测试分类");
        Book book2 = new Book("TEST001", "测试书籍2", "测试作者2", "987654321", "测试分类");
        
        assertTrue(libraryService.addBook(book1));
        assertFalse(libraryService.addBook(book2)); // 重复ID应该添加失败
        assertEquals(1, libraryService.getTotalBooks());
    }
    
    @Test
    public void testRemoveBook() {
        Book book = new Book("TEST001", "测试书籍", "测试作者", "123456789", "测试分类");
        libraryService.addBook(book);
        
        assertTrue(libraryService.removeBook("TEST001"));
        assertEquals(0, libraryService.getTotalBooks());
        assertNull(libraryService.findBookById("TEST001"));
    }
    
    @Test
    public void testUpdateBook() {
        Book book = new Book("TEST001", "原始标题", "原始作者", "123456789", "原始分类");
        libraryService.addBook(book);
        
        book.setTitle("更新后的标题");
        book.setAuthor("更新后的作者");
        
        assertTrue(libraryService.updateBook(book));
        
        Book updatedBook = libraryService.findBookById("TEST001");
        assertEquals("更新后的标题", updatedBook.getTitle());
        assertEquals("更新后的作者", updatedBook.getAuthor());
    }
    
    @Test
    public void testSearchBooksByTitle() {
        libraryService.addBook(new Book("B001", "Java编程思想", "Bruce Eckel", "111", "编程"));
        libraryService.addBook(new Book("B002", "Java核心技术", "Cay Horstmann", "222", "编程"));
        libraryService.addBook(new Book("B003", "Python学习手册", "Mark Lutz", "333", "编程"));
        
        List<Book> results = libraryService.searchBooksByTitle("Java");
        assertEquals(2, results.size());
        
        results = libraryService.searchBooksByTitle("Python");
        assertEquals(1, results.size());
        assertEquals("Python学习手册", results.get(0).getTitle());
    }
    
    @Test
    public void testSearchBooksByAuthor() {
        libraryService.addBook(new Book("B001", "Java编程思想", "Bruce Eckel", "111", "编程"));
        libraryService.addBook(new Book("B002", "Java核心技术", "Cay Horstmann", "222", "编程"));
        
        List<Book> results = libraryService.searchBooksByAuthor("Bruce");
        assertEquals(1, results.size());
        assertEquals("Java编程思想", results.get(0).getTitle());
    }
    
    @Test
    public void testBorrowAndReturnBook() {
        Book book = new Book("TEST001", "测试书籍", "测试作者", "123456789", "测试分类");
        libraryService.addBook(book);
        
        // 测试借书
        assertTrue(libraryService.borrowBook("TEST001"));
        Book borrowedBook = libraryService.findBookById("TEST001");
        assertFalse(borrowedBook.isAvailable());
        
        // 测试已借出的书不能再借
        assertFalse(libraryService.borrowBook("TEST001"));
        
        // 测试还书
        assertTrue(libraryService.returnBook("TEST001"));
        Book returnedBook = libraryService.findBookById("TEST001");
        assertTrue(returnedBook.isAvailable());
    }
    
    @Test
    public void testGetAvailableBooks() {
        libraryService.addBook(new Book("B001", "可借书籍1", "作者1", "111", "分类1"));
        libraryService.addBook(new Book("B002", "可借书籍2", "作者2", "222", "分类2"));
        libraryService.addBook(new Book("B003", "已借书籍", "作者3", "333", "分类3"));
        
        libraryService.borrowBook("B003"); // 借出一本书
        
        List<Book> availableBooks = libraryService.getAvailableBooks();
        assertEquals(2, availableBooks.size());
        
        // 确保可借书籍列表中不包含已借出的书
        assertFalse(availableBooks.stream().anyMatch(book -> "B003".equals(book.getId())));
    }
}