package com.library.util;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

/**
 * ID生成器工具类
 * ID generator utility class
 */
public class IdGenerator {
    
    private static final AtomicLong counter = new AtomicLong(1);
    
    /**
     * 生成简单的递增ID
     * Generate simple incremental ID
     */
    public static String generateSimpleId() {
        return "B" + String.format("%06d", counter.getAndIncrement());
    }
    
    /**
     * 生成UUID格式的ID
     * Generate UUID format ID
     */
    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }
    
    /**
     * 重置计数器
     * Reset counter
     */
    public static void resetCounter() {
        counter.set(1);
    }
    
    /**
     * 设置计数器起始值
     * Set counter starting value
     */
    public static void setCounterStart(long start) {
        counter.set(start);
    }
}