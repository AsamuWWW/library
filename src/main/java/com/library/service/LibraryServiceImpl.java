package com.library.service;

import com.library.model.Book;
import com.library.util.DataManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 图书馆服务实现类
 * Implementation of LibraryService interface
 */
public class LibraryServiceImpl implements LibraryService {
    
    private final Map<String, Book> books;
    private final DataManager dataManager;
    
    public LibraryServiceImpl() {
        this.books = new HashMap<>();
        this.dataManager = new DataManager();
        loadData(); // 启动时加载数据
    }
    
    @Override
    public boolean addBook(Book book) {
        if (book == null || book.getId() == null || book.getId().isEmpty()) {
            return false;
        }
        
        // 检查是否已存在相同ID的图书
        if (books.containsKey(book.getId())) {
            return false;
        }
        
        books.put(book.getId(), book);
        saveData();
        return true;
    }
    
    @Override
    public boolean removeBook(String bookId) {
        if (bookId == null || bookId.isEmpty()) {
            return false;
        }
        
        boolean removed = books.remove(bookId) != null;
        if (removed) {
            saveData();
        }
        return removed;
    }
    
    @Override
    public boolean updateBook(Book book) {
        if (book == null || book.getId() == null || !books.containsKey(book.getId())) {
            return false;
        }
        
        books.put(book.getId(), book);
        saveData();
        return true;
    }
    
    @Override
    public Book findBookById(String bookId) {
        return books.get(bookId);
    }
    
    @Override
    public List<Book> searchBooksByTitle(String title) {
        if (title == null || title.isEmpty()) {
            return new ArrayList<>();
        }
        
        return books.values().stream()
                .filter(book -> book.getTitle() != null && 
                        book.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Book> searchBooksByAuthor(String author) {
        if (author == null || author.isEmpty()) {
            return new ArrayList<>();
        }
        
        return books.values().stream()
                .filter(book -> book.getAuthor() != null && 
                        book.getAuthor().toLowerCase().contains(author.toLowerCase()))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Book> searchBooksByCategory(String category) {
        if (category == null || category.isEmpty()) {
            return new ArrayList<>();
        }
        
        return books.values().stream()
                .filter(book -> book.getCategory() != null && 
                        book.getCategory().toLowerCase().contains(category.toLowerCase()))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Book> getAllBooks() {
        return new ArrayList<>(books.values());
    }
    
    @Override
    public List<Book> getAvailableBooks() {
        return books.values().stream()
                .filter(Book::isAvailable)
                .collect(Collectors.toList());
    }
    
    @Override
    public boolean borrowBook(String bookId) {
        Book book = books.get(bookId);
        if (book == null || !book.isAvailable()) {
            return false;
        }
        
        book.setAvailable(false);
        saveData();
        return true;
    }
    
    @Override
    public boolean returnBook(String bookId) {
        Book book = books.get(bookId);
        if (book == null) {
            return false;
        }
        
        book.setAvailable(true);
        saveData();
        return true;
    }
    
    @Override
    public int getTotalBooks() {
        return books.size();
    }
    
    @Override
    public boolean saveData() {
        try {
            return dataManager.saveBooks(new ArrayList<>(books.values()));
        } catch (Exception e) {
            System.err.println("保存数据时出错: " + e.getMessage());
            return false;
        }
    }
    
    @Override
    public boolean loadData() {
        try {
            List<Book> loadedBooks = dataManager.loadBooks();
            books.clear();
            for (Book book : loadedBooks) {
                books.put(book.getId(), book);
            }
            return true;
        } catch (Exception e) {
            System.err.println("加载数据时出错: " + e.getMessage());
            return false;
        }
    }
}