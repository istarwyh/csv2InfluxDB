package com.metis.paas;

import java.util.*;
public class MainS {
    public static void main(String[] args) {
        LruCache lruCache = new LruCache(5);
        lruCache.put("001", "用户1信息");
        lruCache.put("002", "用户2信息");
        lruCache.put("003", "用户3信息");
        lruCache.put("004", "用户4信息");
        lruCache.put("005", "用户5信息");
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();
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

    /**
     * 场景描述：由于公司需要频繁调用用户信息，每次都需要查询数据库，
     * 现要求使用hashMap做一个缓存，缓存不能无限大，需要删除掉最近最少使用数据
     */
    static class LruCache {

        private Node head;
        private Node end;
        //缓存存储上限
        private int limit;
        private HashMap<String, Node> hashMap;
        private LinkedHashMap<String, String> lhm;
        public LruCache(int limit) {
            this.limit = limit;
            hashMap = new HashMap<>();
            this.lhm = new LinkedHashMap<String,String>(limit, (float) 0.75,true ){
                private static final long serialID = 1L;
                @Override
                protected boolean removeEldestEntry(Map.Entry eldest) {
                     return size() > limit;
                }
            };
        }

        /**
         * 获取cache中的信息
         * 核心思想：当获取节点时要刷新节点到end位置
         *
         * @param key 插入key值
         * @return
         */
        public String get(String key) {
            String res = lhm.get(key);
            Iterator iter = lhm.entrySet().iterator();
            if( iter.hasNext() ) {
                Map.Entry e = (Map.Entry) iter.next();
                this.head = new Node((String)e.getKey(),(String)e.getValue());
            }
            while( iter.hasNext() ){
                Map.Entry e = (Map.Entry) iter.next();
                if( !iter.hasNext() ){
                    this.end = new Node( (String)e.getKey(),(String)e.getValue() );
                }
            }
            return res;
        }

        /**
         * 向map中插入信息
         * 核心思想：当插入值存在则更新原节点，
         * 不存在的情况下，如果超过最大存储量则删除head并在末尾插入，否则直接插入
         *
         * @param key   插入key值
         * @param value 插入value值
         */
        public void put(String key, String value) {
            lhm.put(key, value);
        }


        /**
         * 插入新节点
         *
         * @param node 插入节点
         */
        private void addNode(Node node) {
            if (end != null) {
                end.next = node;
                node.pre = end;
                node.next = null;
            }
            end = node;
            if (head == null) {
                head = node;
            }
        }

        /**
         * 节点类 value为cache信息
         */
        class Node {
            public Node pre;
            public Node next;
            public String key;
            public String value;

            Node(String key, String value) {
                this.key = key;
                this.value = value;
            }
        }
    }
}
