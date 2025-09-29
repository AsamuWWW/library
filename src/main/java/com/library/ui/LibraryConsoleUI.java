package com.library.ui;

import com.library.model.Book;
import com.library.service.LibraryService;
import com.library.service.LibraryServiceImpl;
import com.library.util.IdGenerator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

/**
 * 控制台用户界面
 * Console-based user interface for the library management system
 */
public class LibraryConsoleUI {
    
    private final LibraryService libraryService;
    private final Scanner scanner;
    private final DateTimeFormatter dateFormatter;
    
    public LibraryConsoleUI() {
        this.libraryService = new LibraryServiceImpl();
        this.scanner = new Scanner(System.in);
        this.dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }
    
    /**
     * 启动主界面
     * Start the main interface
     */
    public void start() {
        System.out.println("=================================");
        System.out.println("    欢迎使用图书管理系统");
        System.out.println("  Welcome to Library Management System");
        System.out.println("=================================");
        
        while (true) {
            showMainMenu();
            int choice = getIntInput();
            
            switch (choice) {
                case 1:
                    addNewBook();
                    break;
                case 2:
                    removeBook();
                    break;
                case 3:
                    updateBook();
                    break;
                case 4:
                    searchBooks();
                    break;
                case 5:
                    showAllBooks();
                    break;
                case 6:
                    borrowBook();
                    break;
                case 7:
                    returnBook();
                    break;
                case 8:
                    showStatistics();
                    break;
                case 0:
                    System.out.println("谢谢使用，再见！");
                    return;
                default:
                    System.out.println("无效选择，请重新输入！");
            }
        }
    }
    
    private void showMainMenu() {
        System.out.println("\n==================== 主菜单 ====================");
        System.out.println("1. 添加图书");
        System.out.println("2. 删除图书");
        System.out.println("3. 修改图书");
        System.out.println("4. 搜索图书");
        System.out.println("5. 显示所有图书");
        System.out.println("6. 借阅图书");
        System.out.println("7. 归还图书");
        System.out.println("8. 统计信息");
        System.out.println("0. 退出系统");
        System.out.println("===============================================");
        System.out.print("请选择操作: ");
    }
    
    private void addNewBook() {
        System.out.println("\n===== 添加新图书 =====");
        
        Book book = new Book();
        book.setId(IdGenerator.generateSimpleId());
        
        System.out.print("书名: ");
        book.setTitle(scanner.nextLine());
        
        System.out.print("作者: ");
        book.setAuthor(scanner.nextLine());
        
        System.out.print("ISBN: ");
        book.setIsbn(scanner.nextLine());
        
        System.out.print("分类: ");
        book.setCategory(scanner.nextLine());
        
        System.out.print("出版日期 (yyyy-MM-dd): ");
        String dateStr = scanner.nextLine();
        try {
            if (!dateStr.isEmpty()) {
                book.setPublishDate(LocalDate.parse(dateStr, dateFormatter));
            }
        } catch (DateTimeParseException e) {
            System.out.println("日期格式错误，已跳过");
        }
        
        System.out.print("描述: ");
        book.setDescription(scanner.nextLine());
        
        if (libraryService.addBook(book)) {
            System.out.println("图书添加成功！图书ID: " + book.getId());
        } else {
            System.out.println("图书添加失败！");
        }
    }
    
    private void removeBook() {
        System.out.println("\n===== 删除图书 =====");
        System.out.print("请输入要删除的图书ID: ");
        String bookId = scanner.nextLine();
        
        Book book = libraryService.findBookById(bookId);
        if (book == null) {
            System.out.println("未找到ID为 " + bookId + " 的图书！");
            return;
        }
        
        System.out.println("找到图书: " + book.getTitle() + " - " + book.getAuthor());
        System.out.print("确认删除吗？(y/n): ");
        String confirm = scanner.nextLine();
        
        if ("y".equalsIgnoreCase(confirm) || "yes".equalsIgnoreCase(confirm)) {
            if (libraryService.removeBook(bookId)) {
                System.out.println("图书删除成功！");
            } else {
                System.out.println("图书删除失败！");
            }
        } else {
            System.out.println("操作已取消");
        }
    }
    
    private void updateBook() {
        System.out.println("\n===== 修改图书信息 =====");
        System.out.print("请输入要修改的图书ID: ");
        String bookId = scanner.nextLine();
        
        Book book = libraryService.findBookById(bookId);
        if (book == null) {
            System.out.println("未找到ID为 " + bookId + " 的图书！");
            return;
        }
        
        System.out.println("当前图书信息:");
        displayBookDetails(book);
        
        System.out.println("\n请输入新信息 (直接回车保持原值):");
        
        System.out.print("书名 [" + book.getTitle() + "]: ");
        String title = scanner.nextLine();
        if (!title.isEmpty()) {
            book.setTitle(title);
        }
        
        System.out.print("作者 [" + book.getAuthor() + "]: ");
        String author = scanner.nextLine();
        if (!author.isEmpty()) {
            book.setAuthor(author);
        }
        
        System.out.print("ISBN [" + book.getIsbn() + "]: ");
        String isbn = scanner.nextLine();
        if (!isbn.isEmpty()) {
            book.setIsbn(isbn);
        }
        
        System.out.print("分类 [" + book.getCategory() + "]: ");
        String category = scanner.nextLine();
        if (!category.isEmpty()) {
            book.setCategory(category);
        }
        
        if (libraryService.updateBook(book)) {
            System.out.println("图书信息更新成功！");
        } else {
            System.out.println("图书信息更新失败！");
        }
    }
    
    private void searchBooks() {
        System.out.println("\n===== 搜索图书 =====");
        System.out.println("1. 按标题搜索");
        System.out.println("2. 按作者搜索");
        System.out.println("3. 按分类搜索");
        System.out.print("请选择搜索方式: ");
        
        int choice = getIntInput();
        System.out.print("请输入搜索关键词: ");
        String keyword = scanner.nextLine();
        
        List<Book> results = null;
        switch (choice) {
            case 1:
                results = libraryService.searchBooksByTitle(keyword);
                break;
            case 2:
                results = libraryService.searchBooksByAuthor(keyword);
                break;
            case 3:
                results = libraryService.searchBooksByCategory(keyword);
                break;
            default:
                System.out.println("无效选择！");
                return;
        }
        
        if (results.isEmpty()) {
            System.out.println("没有找到匹配的图书！");
        } else {
            System.out.println("找到 " + results.size() + " 本图书：");
            displayBookList(results);
        }
    }
    
    private void showAllBooks() {
        System.out.println("\n===== 所有图书 =====");
        List<Book> books = libraryService.getAllBooks();
        
        if (books.isEmpty()) {
            System.out.println("图书馆暂无图书！");
        } else {
            displayBookList(books);
        }
    }
    
    private void borrowBook() {
        System.out.println("\n===== 借阅图书 =====");
        
        // 显示可借图书
        List<Book> availableBooks = libraryService.getAvailableBooks();
        if (availableBooks.isEmpty()) {
            System.out.println("暂无可借图书！");
            return;
        }
        
        System.out.println("可借图书列表：");
        displayBookList(availableBooks);
        
        System.out.print("请输入要借阅的图书ID: ");
        String bookId = scanner.nextLine();
        
        if (libraryService.borrowBook(bookId)) {
            System.out.println("图书借阅成功！");
        } else {
            System.out.println("图书借阅失败！可能图书不存在或已被借出。");
        }
    }
    
    private void returnBook() {
        System.out.println("\n===== 归还图书 =====");
        System.out.print("请输入要归还的图书ID: ");
        String bookId = scanner.nextLine();
        
        Book book = libraryService.findBookById(bookId);
        if (book == null) {
            System.out.println("未找到ID为 " + bookId + " 的图书！");
            return;
        }
        
        if (book.isAvailable()) {
            System.out.println("该图书未被借出！");
            return;
        }
        
        if (libraryService.returnBook(bookId)) {
            System.out.println("图书归还成功！");
        } else {
            System.out.println("图书归还失败！");
        }
    }
    
    private void showStatistics() {
        System.out.println("\n===== 统计信息 =====");
        
        int totalBooks = libraryService.getTotalBooks();
        List<Book> availableBooks = libraryService.getAvailableBooks();
        int borrowedBooks = totalBooks - availableBooks.size();
        
        System.out.println("图书总数: " + totalBooks);
        System.out.println("可借图书: " + availableBooks.size());
        System.out.println("已借图书: " + borrowedBooks);
        
        if (totalBooks > 0) {
            double availabilityRate = (double) availableBooks.size() / totalBooks * 100;
            System.out.printf("可借率: %.2f%%\n", availabilityRate);
        }
    }
    
    private void displayBookList(List<Book> books) {
        System.out.println("------------------------------------------------------------------------");
        System.out.printf("%-10s %-20s %-15s %-10s %-8s\n", "ID", "书名", "作者", "分类", "状态");
        System.out.println("------------------------------------------------------------------------");
        
        for (Book book : books) {
            String status = book.isAvailable() ? "可借" : "已借出";
            System.out.printf("%-10s %-20s %-15s %-10s %-8s\n",
                    book.getId(),
                    truncateString(book.getTitle(), 20),
                    truncateString(book.getAuthor(), 15),
                    truncateString(book.getCategory(), 10),
                    status);
        }
        System.out.println("------------------------------------------------------------------------");
    }
    
    private void displayBookDetails(Book book) {
        System.out.println("ID: " + book.getId());
        System.out.println("书名: " + book.getTitle());
        System.out.println("作者: " + book.getAuthor());
        System.out.println("ISBN: " + book.getIsbn());
        System.out.println("分类: " + book.getCategory());
        System.out.println("出版日期: " + (book.getPublishDate() != null ? book.getPublishDate() : "未设置"));
        System.out.println("状态: " + (book.isAvailable() ? "可借" : "已借出"));
        System.out.println("描述: " + (book.getDescription() != null ? book.getDescription() : "无"));
    }
    
    private String truncateString(String str, int maxLength) {
        if (str == null) return "";
        if (str.length() <= maxLength) return str;
        return str.substring(0, maxLength - 3) + "...";
    }
    
    private int getIntInput() {
        try {
            String input = scanner.nextLine();
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}