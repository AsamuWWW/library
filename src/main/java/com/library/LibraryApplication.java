package com.library;

import com.library.ui.LibraryConsoleUI;

/**
 * 图书管理系统主应用程序
 * Main application class for the Library Management System
 */
public class LibraryApplication {
    
    public static void main(String[] args) {
        try {
            LibraryConsoleUI ui = new LibraryConsoleUI();
            ui.start();
        } catch (Exception e) {
            System.err.println("系统启动失败: " + e.getMessage());
            e.printStackTrace();
        }
    }
}