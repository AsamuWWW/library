# Java图书管理系统 - 详细教程

## 🎯 教程目标

本教程将手把手教你如何从零开始构建一个完整的Java图书管理系统。通过本教程，你将：

1. 掌握Java面向对象编程的核心概念
2. 学会使用Maven管理项目依赖
3. 理解分层架构设计思想
4. 学会编写单元测试
5. 掌握JSON数据持久化技术

## 📋 准备工作

### 环境搭建

1. **安装Java开发环境**
   - 下载并安装JDK 11或更高版本
   - 设置`JAVA_HOME`环境变量
   - 验证安装：`java -version`

2. **安装Maven**
   - 下载并安装Maven 3.6+
   - 设置`MAVEN_HOME`环境变量
   - 验证安装：`mvn -version`

3. **选择IDE**
   - 推荐使用IntelliJ IDEA Community Edition（免费）
   - 或者使用Eclipse IDE for Java Developers

### 创建项目结构

```bash
# 创建项目目录
mkdir library-management-system
cd library-management-system

# 创建Maven标准目录结构
mkdir -p src/main/java/com/library/{model,service,ui,util}
mkdir -p src/test/java/com/library/{model,service,util}
mkdir -p src/main/resources
```

## 📝 步骤一：创建Maven项目配置

创建`pom.xml`文件：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
         http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    
    <!-- 项目基本信息 -->
    <groupId>com.library</groupId>
    <artifactId>library-management-system</artifactId>
    <version>1.0.0</version>
    <packaging>jar</packaging>
    
    <name>Library Management System</name>
    <description>一个简单的图书管理系统</description>
    
    <!-- Java版本配置 -->
    <properties>
        <maven.compiler.source>11</maven.compiler.source>
        <maven.compiler.target>11</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>
    
    <!-- 项目依赖 -->
    <dependencies>
        <!-- JUnit测试框架 -->
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-engine</artifactId>
            <version>5.8.2</version>
            <scope>test</scope>
        </dependency>
        
        <!-- Jackson JSON处理库 -->
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>2.13.4</version>
        </dependency>
        
        <!-- Jackson Java 8时间支持 -->
        <dependency>
            <groupId>com.fasterxml.jackson.datatype</groupId>
            <artifactId>jackson-datatype-jsr310</artifactId>
            <version>2.13.4</version>
        </dependency>
    </dependencies>
    
    <!-- Maven插件配置 -->
    <build>
        <plugins>
            <!-- 编译插件 -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.8.1</version>
                <configuration>
                    <source>11</source>
                    <target>11</target>
                </configuration>
            </plugin>
            
            <!-- 测试插件 -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>3.0.0-M7</version>
            </plugin>
            
            <!-- 运行插件 -->
            <plugin>
                <groupId>org.codehaus.mojo</groupId>
                <artifactId>exec-maven-plugin</artifactId>
                <version>3.1.0</version>
                <configuration>
                    <mainClass>com.library.LibraryApplication</mainClass>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

### 关键概念解释

- **groupId**: 项目组织的唯一标识，通常使用反向域名
- **artifactId**: 项目的唯一标识
- **version**: 项目版本号
- **dependencies**: 项目依赖的外部库
- **plugins**: Maven构建过程中使用的插件

## 📚 步骤二：创建图书实体类

创建`src/main/java/com/library/model/Book.java`：

```java
package com.library.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDate;
import java.util.Objects;

/**
 * 图书实体类
 * 这个类代表系统中的一本图书
 */
@JsonIgnoreProperties(ignoreUnknown = true)  // Jackson注解：忽略未知字段
public class Book {
    // 字段定义
    private String id;           // 图书唯一标识
    private String title;        // 书名
    private String author;       // 作者
    private String isbn;         // 国际标准书号
    private String category;     // 分类
    private LocalDate publishDate; // 出版日期
    private boolean available;   // 是否可借
    private String description;  // 图书描述
    
    // 默认构造函数
    public Book() {
        this.available = true; // 新书默认可借
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
    
    // Getter和Setter方法
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    
    public LocalDate getPublishDate() { return publishDate; }
    public void setPublishDate(LocalDate publishDate) { this.publishDate = publishDate; }
    
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    // 重写equals方法：根据ID判断两本书是否相同
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id);
    }
    
    // 重写hashCode方法：与equals方法保持一致
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    // 重写toString方法：用于调试和日志输出
    @Override
    public String toString() {
        return String.format("Book{id='%s', title='%s', author='%s', isbn='%s', category='%s', available=%s}",
                id, title, author, isbn, category, available);
    }
}
```

### 关键概念解释

1. **封装性**: 使用private字段和public方法控制访问
2. **Jackson注解**: `@JsonIgnoreProperties`用于JSON序列化控制
3. **LocalDate**: Java 8时间API，比Date更好用
4. **equals和hashCode**: 对象比较和集合操作的基础
5. **toString**: 便于调试和日志输出

## 🔧 步骤三：定义服务接口

创建`src/main/java/com/library/service/LibraryService.java`：

```java
package com.library.service;

import com.library.model.Book;
import java.util.List;

/**
 * 图书馆服务接口
 * 定义了图书管理系统的核心功能
 */
public interface LibraryService {
    
    // 图书基础操作
    boolean addBook(Book book);              // 添加图书
    boolean removeBook(String bookId);       // 删除图书
    boolean updateBook(Book book);           // 更新图书信息
    Book findBookById(String bookId);        // 根据ID查找图书
    
    // 搜索功能
    List<Book> searchBooksByTitle(String title);       // 按标题搜索
    List<Book> searchBooksByAuthor(String author);     // 按作者搜索
    List<Book> searchBooksByCategory(String category); // 按分类搜索
    
    // 图书列表
    List<Book> getAllBooks();         // 获取所有图书
    List<Book> getAvailableBooks();   // 获取可借图书
    
    // 借阅管理
    boolean borrowBook(String bookId);  // 借书
    boolean returnBook(String bookId);  // 还书
    
    // 统计信息
    int getTotalBooks();  // 获取图书总数
    
    // 数据持久化
    boolean saveData();   // 保存数据
    boolean loadData();   // 加载数据
}
```

### 接口设计原则

1. **单一职责**: 接口只关注图书管理功能
2. **方法命名**: 使用清晰的动词+名词组合
3. **返回值**: 布尔值表示操作成功与否，对象或列表返回具体数据
4. **参数设计**: 使用具体类型，避免Object

## ⚙️ 步骤四：实现服务类

创建`src/main/java/com/library/service/LibraryServiceImpl.java`：

```java
package com.library.service;

import com.library.model.Book;
import com.library.util.DataManager;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 图书馆服务实现类
 * 实现LibraryService接口中定义的所有方法
 */
public class LibraryServiceImpl implements LibraryService {
    
    // 使用HashMap存储图书，key为图书ID，value为图书对象
    private final Map<String, Book> books;
    
    // 数据管理器，负责数据持久化
    private final DataManager dataManager;
    
    // 构造函数
    public LibraryServiceImpl() {
        this.books = new HashMap<>();
        this.dataManager = new DataManager();
        loadData(); // 启动时自动加载数据
    }
    
    @Override
    public boolean addBook(Book book) {
        // 参数验证
        if (book == null || book.getId() == null || book.getId().isEmpty()) {
            return false;
        }
        
        // 检查是否已存在相同ID的图书
        if (books.containsKey(book.getId())) {
            return false;
        }
        
        // 添加图书并保存数据
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
        
        // 使用Stream API进行搜索
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
                .filter(Book::isAvailable)  // 方法引用的使用
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
```

### 实现要点

1. **数据结构选择**: HashMap提供O(1)的查找效率
2. **Stream API**: 简化集合操作和过滤逻辑
3. **异常处理**: 使用try-catch捕获和处理异常
4. **数据验证**: 在每个方法中进行必要的参数检查
5. **方法引用**: `Book::isAvailable`是lambda表达式的简化写法

## 💾 步骤五：实现数据管理器

创建`src/main/java/com/library/util/DataManager.java`：

```java
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
 * 负责图书数据的持久化存储
 */
public class DataManager {
    
    private static final String DATA_FILE = "library_data.json";
    private final ObjectMapper objectMapper;
    
    public DataManager() {
        this.objectMapper = new ObjectMapper();
        // 注册Java 8时间模块，支持LocalDate序列化
        this.objectMapper.registerModule(new JavaTimeModule());
    }
    
    /**
     * 保存图书数据到JSON文件
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
     */
    public List<Book> loadBooks() {
        try {
            File file = new File(DATA_FILE);
            if (!file.exists()) {
                System.out.println("数据文件不存在，创建新的空列表");
                return new ArrayList<>();
            }
            
            // 定义集合类型，用于反序列化
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
     */
    public boolean dataFileExists() {
        return new File(DATA_FILE).exists();
    }
    
    /**
     * 删除数据文件
     */
    public boolean deleteDataFile() {
        File file = new File(DATA_FILE);
        if (file.exists()) {
            return file.delete();
        }
        return true;
    }
}
```

### JSON序列化要点

1. **ObjectMapper**: Jackson库的核心类，负责Java对象和JSON之间的转换
2. **JavaTimeModule**: 支持Java 8时间API的模块
3. **CollectionType**: 指定集合的泛型类型，确保正确反序列化
4. **文件操作**: 基本的文件读写和检查操作

## 🔢 步骤六：创建ID生成器

创建`src/main/java/com/library/util/IdGenerator.java`：

```java
package com.library.util;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

/**
 * ID生成器工具类
 * 提供多种ID生成策略
 */
public class IdGenerator {
    
    // 使用原子长整型确保线程安全
    private static final AtomicLong counter = new AtomicLong(1);
    
    /**
     * 生成简单的递增ID
     * 格式：B000001, B000002...
     */
    public static String generateSimpleId() {
        return "B" + String.format("%06d", counter.getAndIncrement());
    }
    
    /**
     * 生成UUID格式的ID
     * 格式：xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx
     */
    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }
    
    /**
     * 重置计数器（主要用于测试）
     */
    public static void resetCounter() {
        counter.set(1);
    }
    
    /**
     * 设置计数器起始值
     */
    public static void setCounterStart(long start) {
        counter.set(start);
    }
}
```

### 设计要点

1. **静态方法**: 工具类通常使用静态方法，无需实例化
2. **线程安全**: AtomicLong确保多线程环境下的安全性
3. **格式化**: 使用String.format生成固定格式的ID
4. **UUID**: 提供全局唯一标识符的选择

## 🖥️ 步骤七：创建用户界面

创建`src/main/java/com/library/ui/LibraryConsoleUI.java`（关键部分）：

```java
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
 * 提供命令行交互界面
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
     * 主程序入口
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
                case 1: addNewBook(); break;
                case 2: removeBook(); break;
                case 3: updateBook(); break;
                case 4: searchBooks(); break;
                case 5: showAllBooks(); break;
                case 6: borrowBook(); break;
                case 7: returnBook(); break;
                case 8: showStatistics(); break;
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
    
    // 其他方法...
}
```

### UI设计要点

1. **菜单驱动**: 使用数字菜单提供清晰的操作选择
2. **输入验证**: 对用户输入进行格式检查和异常处理
3. **用户反馈**: 及时提供操作结果反馈
4. **格式化输出**: 使用表格形式展示数据列表

## 🚀 步骤八：创建主程序

创建`src/main/java/com/library/LibraryApplication.java`：

```java
package com.library;

import com.library.ui.LibraryConsoleUI;

/**
 * 图书管理系统主应用程序
 * 程序入口点
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
```

## 🧪 步骤九：编写单元测试

创建`src/test/java/com/library/model/BookTest.java`：

```java
package com.library.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Book类的单元测试
 */
public class BookTest {
    
    @Test
    public void testBookCreation() {
        // 测试图书创建
        Book book = new Book("B001", "Java编程思想", "Bruce Eckel", "978-0131872486", "编程");
        
        // 断言验证
        assertEquals("B001", book.getId());
        assertEquals("Java编程思想", book.getTitle());
        assertEquals("Bruce Eckel", book.getAuthor());
        assertEquals("978-0131872486", book.getIsbn());
        assertEquals("编程", book.getCategory());
        assertTrue(book.isAvailable());  // 新书应该可借
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
}
```

### 测试要点

1. **测试命名**: 使用`test`前缀和描述性名称
2. **断言使用**: `assertEquals`, `assertTrue`, `assertFalse`等
3. **边界测试**: 测试默认值、空值等边界情况
4. **业务逻辑**: 测试equals方法等业务规则

## 🏃‍♂️ 步骤十：构建和运行

### 构建项目

```bash
# 清理并编译
mvn clean compile

# 运行测试
mvn test

# 打包项目
mvn package
```

### 运行程序

```bash
# 使用Maven运行
mvn exec:java -Dexec.mainClass="com.library.LibraryApplication"

# 或直接运行JAR文件
java -jar target/library-management-system-1.0.0.jar
```

## 🎯 学习总结

通过完成这个项目，你应该掌握了：

### 核心概念
- Java面向对象编程（封装、继承、多态）
- 接口和实现类的设计模式
- 集合框架的使用（List、Map、Set）
- Stream API的应用
- 异常处理机制

### 开发工具
- Maven项目管理
- JUnit单元测试
- Jackson JSON处理
- IDE使用技巧

### 设计原则
- 单一职责原则
- 接口隔离原则
- 依赖倒置原则
- 代码分层架构

## 🚀 进阶建议

1. **添加更多功能**：用户管理、图书分类管理、借阅记录等
2. **改进UI**：使用JavaFX或Web界面
3. **数据库集成**：使用MySQL或H2数据库
4. **网络功能**：实现REST API
5. **部署优化**：使用Docker进行容器化部署

恭喜你完成了这个Java图书管理系统的学习！这个项目为你后续的Java学习奠定了坚实的基础。