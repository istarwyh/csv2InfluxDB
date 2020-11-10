package com.metis.paas;

import java.util.*;

/**
 * 场景描述：由于公司需要频繁调用用户信息，每次都需要查询数据库，
 * 现要求使用hashMap做一个缓存，缓存不能无限大，需要删除掉最近最少使用数据
 */
 public class LruCache {

    Node head;
    Node end;
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

