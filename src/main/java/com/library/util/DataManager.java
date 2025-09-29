package com.library.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.library.model.Book;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据管理工具类
 * Data management utility class for file-based persistence
 */
public class DataManager {
    
    private static final String DATA_FILE = "library_data.json";
    private final ObjectMapper objectMapper;
    
    public DataManager() {
        this.objectMapper = new ObjectMapper();
        // 注册Java时间模块以支持LocalDate
        this.objectMapper.registerModule(new JavaTimeModule());
    }
    
    /**
     * 保存图书数据到JSON文件
     * Save books data to JSON file
     */
    public boolean saveBooks(List<Book> books) {
        try {
            File file = new File(DATA_FILE);
            objectMapper.writeValue(file, books);
            System.out.println("数据已保存到文件: " + file.getAbsolutePath());
            return true;
        } catch (IOException e) {
            System.err.println("保存数据失败: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * 从JSON文件加载图书数据
     * Load books data from JSON file
     */
    public List<Book> loadBooks() {
        try {
            File file = new File(DATA_FILE);
            if (!file.exists()) {
                System.out.println("数据文件不存在，创建新的空列表");
                return new ArrayList<>();
            }
            
            CollectionType listType = objectMapper.getTypeFactory()
                    .constructCollectionType(List.class, Book.class);
            List<Book> books = objectMapper.readValue(file, listType);
            System.out.println("从文件加载了 " + books.size() + " 本图书");
            return books;
            
        } catch (IOException e) {
            System.err.println("加载数据失败: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    /**
     * 检查数据文件是否存在
     * Check if data file exists
     */
    public boolean dataFileExists() {
        return new File(DATA_FILE).exists();
    }
    
    /**
     * 删除数据文件
     * Delete data file
     */
    public boolean deleteDataFile() {
        File file = new File(DATA_FILE);
        if (file.exists()) {
            return file.delete();
        }
        return true;
    }
}