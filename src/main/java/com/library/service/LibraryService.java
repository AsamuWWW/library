package com.library.service;

import com.library.model.Book;
import java.util.List;

/**
 * 图书馆服务接口
 * Library service interface defining core operations
 */
public interface LibraryService {
    
    /**
     * 添加图书
     * Add a new book to the library
     */
    boolean addBook(Book book);
    
    /**
     * 根据ID删除图书
     * Remove a book by ID
     */
    boolean removeBook(String bookId);
    
    /**
     * 更新图书信息
     * Update book information
     */
    boolean updateBook(Book book);
    
    /**
     * 根据ID查找图书
     * Find a book by ID
     */
    Book findBookById(String bookId);
    
    /**
     * 根据标题搜索图书
     * Search books by title
     */
    List<Book> searchBooksByTitle(String title);
    
    /**
     * 根据作者搜索图书
     * Search books by author
     */
    List<Book> searchBooksByAuthor(String author);
    
    /**
     * 根据分类搜索图书
     * Search books by category
     */
    List<Book> searchBooksByCategory(String category);
    
    /**
     * 获取所有图书
     * Get all books in the library
     */
    List<Book> getAllBooks();
    
    /**
     * 获取可借的图书
     * Get all available books
     */
    List<Book> getAvailableBooks();
    
    /**
     * 借书
     * Borrow a book
     */
    boolean borrowBook(String bookId);
    
    /**
     * 还书
     * Return a book
     */
    boolean returnBook(String bookId);
    
    /**
     * 获取图书总数
     * Get total number of books
     */
    int getTotalBooks();
    
    /**
     * 保存数据到文件
     * Save data to file
     */
    boolean saveData();
    
    /**
     * 从文件加载数据
     * Load data from file
     */
    boolean loadData();
}