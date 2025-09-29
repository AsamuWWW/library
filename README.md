# Java图书管理系统 - 完整教程

## 📚 项目简介

这是一个使用Java语言开发的图书管理系统，适合Java初学者学习。本项目包含完整的源代码、详细的注释和手把手的教学指南。

### 🌟 功能特性

- ✅ 图书的增删改查 (CRUD操作)
- ✅ 多种搜索方式 (按标题、作者、分类)
- ✅ 借阅和归还管理
- ✅ 数据持久化 (JSON文件存储)
- ✅ 友好的命令行界面
- ✅ 完整的单元测试
- ✅ 统计信息显示

### 🎯 学习目标

通过这个项目，你将学到：

1. **Java基础语法和面向对象编程**
2. **Maven项目管理**
3. **接口和实现类的设计模式**
4. **JSON数据序列化/反序列化**
5. **单元测试编写**
6. **代码组织和包结构**

## 🚀 快速开始

### 环境要求

- Java 11 或更高版本
- Maven 3.6 或更高版本
- 任何支持Java的IDE (推荐IntelliJ IDEA或Eclipse)

### 安装步骤

1. **克隆项目**
```bash
git clone https://github.com/AsamuWWW/library.git
cd library
```

2. **编译项目**
```bash
mvn clean compile
```

3. **运行测试**
```bash
mvn test
```

4. **运行程序**
```bash
mvn exec:java -Dexec.mainClass="com.library.LibraryApplication"
```

## 📁 项目结构详解

```
library/
├── pom.xml                    # Maven配置文件
├── README.md                  # 项目说明文档
├── .gitignore                 # Git忽略文件配置
├── src/
│   ├── main/java/com/library/
│   │   ├── LibraryApplication.java      # 主程序入口
│   │   ├── model/
│   │   │   └── Book.java                # 图书实体类
│   │   ├── service/
│   │   │   ├── LibraryService.java      # 服务接口
│   │   │   └── LibraryServiceImpl.java  # 服务实现类
│   │   ├── ui/
│   │   │   └── LibraryConsoleUI.java    # 控制台用户界面
│   │   └── util/
│   │       ├── DataManager.java         # 数据管理工具类
│   │       └── IdGenerator.java         # ID生成器
│   └── test/java/com/library/
│       ├── model/
│       │   └── BookTest.java            # Book类单元测试
│       └── service/
│           └── LibraryServiceImplTest.java # 服务类单元测试
└── target/                    # Maven构建输出目录
```

## 🎓 代码详解教程

### 第一步：理解项目架构

本项目采用经典的三层架构：

1. **表现层 (UI Layer)**: `LibraryConsoleUI.java` - 负责用户界面和交互
2. **业务逻辑层 (Service Layer)**: `LibraryService.java` 和 `LibraryServiceImpl.java` - 负责核心业务逻辑
3. **数据访问层 (Data Layer)**: `DataManager.java` - 负责数据持久化

### 第二步：核心类详解

#### 1. Book实体类 (model/Book.java)

```java
public class Book {
    private String id;           // 图书ID
    private String title;        // 书名
    private String author;       // 作者
    private String isbn;         // ISBN号
    private String category;     // 分类
    private LocalDate publishDate; // 出版日期
    private boolean available;   // 是否可借
    private String description;  // 描述
    
    // 构造函数、getter、setter、equals、hashCode、toString方法
}
```

**学习重点：**
- Java Bean的标准写法
- `LocalDate`的使用
- `equals()`和`hashCode()`的重写
- Jackson JSON注解的使用

#### 2. 服务接口 (service/LibraryService.java)

```java
public interface LibraryService {
    boolean addBook(Book book);
    boolean removeBook(String bookId);
    boolean updateBook(Book book);
    Book findBookById(String bookId);
    List<Book> searchBooksByTitle(String title);
    // 更多方法...
}
```

**学习重点：**
- 接口的定义和作用
- 方法签名设计
- 返回值类型选择

#### 3. 服务实现类 (service/LibraryServiceImpl.java)

```java
public class LibraryServiceImpl implements LibraryService {
    private final Map<String, Book> books;
    private final DataManager dataManager;
    
    @Override
    public boolean addBook(Book book) {
        // 实现添加图书逻辑
    }
    // 其他方法实现...
}
```

**学习重点：**
- 接口实现
- HashMap的使用
- Stream API的应用
- 数据验证和异常处理

### 第三步：关键技术点

#### 1. JSON数据持久化

使用Jackson库实现JSON序列化：

```java
public boolean saveBooks(List<Book> books) {
    try {
        File file = new File(DATA_FILE);
        objectMapper.writeValue(file, books);
        return true;
    } catch (IOException e) {
        System.err.println("保存数据失败: " + e.getMessage());
        return false;
    }
}
```

#### 2. Stream API应用

```java
public List<Book> searchBooksByTitle(String title) {
    return books.values().stream()
            .filter(book -> book.getTitle() != null && 
                    book.getTitle().toLowerCase().contains(title.toLowerCase()))
            .collect(Collectors.toList());
}
```

#### 3. 用户界面设计

```java
private void showMainMenu() {
    System.out.println("==================== 主菜单 ====================");
    System.out.println("1. 添加图书");
    System.out.println("2. 删除图书");
    // 更多菜单项...
}
```

## 🧪 测试指南

### 运行测试

```bash
# 运行所有测试
mvn test

# 运行特定测试类
mvn test -Dtest=BookTest

# 运行测试并生成报告
mvn test jacoco:report
```

### 测试用例示例

```java
@Test
public void testAddBook() {
    Book book = new Book("TEST001", "测试书籍", "测试作者", "123456789", "测试分类");
    
    assertTrue(libraryService.addBook(book));
    assertEquals(1, libraryService.getTotalBooks());
    
    Book foundBook = libraryService.findBookById("TEST001");
    assertNotNull(foundBook);
    assertEquals("测试书籍", foundBook.getTitle());
}
```

## 🎮 使用说明

### 启动程序

1. 编译并运行程序：
```bash
mvn clean compile exec:java -Dexec.mainClass="com.library.LibraryApplication"
```

2. 程序启动后会显示主菜单，按数字键选择功能

### 主要功能操作

#### 1. 添加图书
- 选择菜单项 `1`
- 按提示输入图书信息
- 系统自动分配图书ID

#### 2. 搜索图书
- 选择菜单项 `4`
- 选择搜索方式（标题、作者、分类）
- 输入搜索关键词

#### 3. 借阅管理
- 借书：选择菜单项 `6`，输入图书ID
- 还书：选择菜单项 `7`，输入图书ID

## 🔧 自定义扩展

### 添加新功能

1. **在接口中定义新方法**
```java
// 在 LibraryService.java 中添加
List<Book> getPopularBooks();
```

2. **在实现类中实现方法**
```java
// 在 LibraryServiceImpl.java 中添加
@Override
public List<Book> getPopularBooks() {
    // 实现逻辑
}
```

3. **在UI中添加菜单项**
```java
// 在 LibraryConsoleUI.java 中添加菜单项和处理方法
```

### 数据库集成

可以替换`DataManager`来支持数据库：

```java
public class DatabaseManager extends DataManager {
    // 实现数据库CRUD操作
}
```

## 📚 进阶学习

### 推荐学习路径

1. **掌握基础概念**
   - Java面向对象编程
   - 集合框架（List、Map、Set）
   - 异常处理

2. **学习设计模式**
   - 单例模式
   - 工厂模式
   - 观察者模式

3. **深入框架学习**
   - Spring Boot
   - Spring Data JPA
   - Thymeleaf模板引擎

4. **Web开发**
   - RESTful API设计
   - 前后端分离
   - 数据库设计

### 扩展项目建议

1. **添加Web界面** - 使用Spring Boot + Thymeleaf
2. **用户管理系统** - 实现用户注册、登录、权限管理
3. **数据库集成** - 使用MySQL或PostgreSQL
4. **API开发** - 提供REST API接口
5. **微服务架构** - 拆分为多个独立服务

## 🤝 贡献指南

欢迎提交Issue和Pull Request！

### 贡献流程

1. Fork 本项目
2. 创建功能分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 创建Pull Request

## 📄 许可证

本项目采用MIT许可证 - 查看 [LICENSE](LICENSE) 文件了解详情

## 🙋‍♂️ 常见问题

### Q: 如何修改数据存储位置？
A: 在`DataManager.java`中修改`DATA_FILE`常量值。

### Q: 如何添加新的图书字段？
A: 在`Book.java`类中添加字段，然后更新相关的getter、setter和UI代码。

### Q: 程序运行时出现乱码怎么办？
A: 确保IDE和终端的编码设置为UTF-8。

### Q: 如何备份图书数据？
A: 复制项目目录下的`library_data.json`文件即可。

## 📞 联系方式

如果你有任何问题或建议，欢迎通过以下方式联系：

- GitHub Issues: [提交问题](https://github.com/AsamuWWW/library/issues)
- 项目讨论: [GitHub Discussions](https://github.com/AsamuWWW/library/discussions)

---

**祝你学习愉快！🎉**