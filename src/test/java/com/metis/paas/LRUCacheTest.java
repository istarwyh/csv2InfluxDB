package com.metis.paas;

import org.junit.jupiter.api.Test;

import java.util.Scanner;

/**
 * @Description: LRUCacheTest
 * @Author: YiHui
 * @Date: 2020-11-10 17:29
 * @Version: ing
 */
public class LRUCacheTest {
    @Test
    void test1() {
        LruCache lruCache = new LruCache(5);
        lruCache.put("001", "用户1信息");
        lruCache.put("002", "用户2信息");
        lruCache.put("003", "用户3信息");
        lruCache.put("004", "用户4信息");
        lruCache.put("005", "用户5信息");

        String input = "0";
        switch (input) {
            //调用get（001）方法，检验队首变为002，队尾变成001
            case "0":
                System.out.println(lruCache.get("001"));
                System.out.println(lruCache.head.value);
                System.out.println(lruCache.end.value);
                break;
            case "1":
                //调用put（001）方法，检验队首变为002，队尾变成001，且值改变
                lruCache.put("001", "用户1信息已更改");
                System.out.println(lruCache.head.value);
                System.out.println(lruCache.end.value);
                break;
            case "2":
                //调用get（003）方法，检验队首为001，队尾变成003
                System.out.println(lruCache.get("003"));
                System.out.println(lruCache.head.value);
                System.out.println(lruCache.end.value);
                break;
            case "3":
                //调用put（003）方法，检验队首为001，队尾变成003，且值改变
                lruCache.put("003", "用户3信息已更改");
                System.out.println(lruCache.head.value);
                System.out.println(lruCache.end.value);
                break;
            case "4":
                //调用get（005）方法，检验队首为001，队尾为005
                System.out.println(lruCache.get("005"));
                System.out.println(lruCache.head.value);
                System.out.println(lruCache.end.value);
                break;
            case "5":
                //调用put（005）方法，检验队首为001，队尾为005，值改变
                lruCache.put("005", "用户5信息已更改");
                System.out.println(lruCache.head.value);
                System.out.println(lruCache.end.value);
                break;
            case "6":
                //调用get（006）方法，检验006值为空，队首为001，队尾为005
                System.out.println(lruCache.get("006"));
                System.out.println(lruCache.head.value);
                System.out.println(lruCache.end.value);
                break;
            case "7":
                //调用put（006）方法，队首为002，队尾变成006,001为空
                lruCache.put("006", "用户6信息");
                System.out.println(lruCache.head.value);
                System.out.println(lruCache.end.value);
                System.out.println(lruCache.get("001"));
                break;
            case "8":
                //调用get（001）方法，put（006）方法，检验队首为003，队尾变成006,002为空
                lruCache.get("001");
                lruCache.put("006", "用户6信息");
                System.out.println(lruCache.head.value);
                System.out.println(lruCache.end.value);
                System.out.println(lruCache.get("002"));
                break;
            case "9":
                //调用put（001）方法，put（006）方法，检验队首为003，队尾变成006,002为空
                lruCache.put("001", "用户1信息已更改");
                lruCache.put("006", "用户6信息");
                System.out.println(lruCache.head.value);
                System.out.println(lruCache.end.value);
                System.out.println(lruCache.get("002"));
                break;
            default:
                System.out.println("wrong");
                break;
        }
    }
}