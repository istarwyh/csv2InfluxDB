package com.metis.paas;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sx_wangyihui 2020/10/16  21:11
 */
public class LRUWithQueue {
    /**
     * 哈希表快速存储和更新被缓存的内容
     * 队列管理最近被访问的状态,队列使用链表实现
     */
    private final Map<String,Node> cache;
    /**
    *   最近最少使用,类似队列的头,出队
    */
    public Node head;
    /**
     * 最近最少使用，类似队列的尾，入队
     * 队列的方向是头指向尾,先进先出
     */
    public Node end;
    private final int capacity;

    public LRUWithQueue(int capacity ){
        this.cache = new HashMap<>();
        this.capacity = capacity;
    }

    public String get( String key ){
        Node node = cache.get(key);
        if( node == null ){
            return "-1";
        }else{
            moveNode( node );
            return node.value;
        }
    }

    /**
     * 每次的put操作,会先访问队列中是否有结点已对应这个数据
     *      如果不是则创建结点并将其加入队列尾部
     *      如果已存在,则替换原有的结点
     * @param key
     * @param value
     */
    public void put( String key,String value ){
        Node node = cache.get(key);
        if( node == null ){
//            清理cache中最久未使用的结点,留出空间给新结点
            removeHead();
            node = new Node(key,value);
//            因为之前访问过,管理访问状态,入队到尾部
            addNode(node);
//            将新建结点放入cache
            cache.put( key,node );

        }else if( node.value.equals( value)){
//            因为之前访问过,管理访问状态
            moveNode(node);
        }else{
            node.value = value;
//            因为之前访问过,管理访问状态
            moveNode(node);
//            更新cache中的缓存,原本的<key,value>被覆盖
            cache.put( key,node );
        }
    }

    private void removeHead(){
        if( cache.size() == capacity ){
            Node tempNode = head;
            cache.remove( head.key);
            head = head.next;
            tempNode.next = null;
            if( head != null ){
                head.prev = null;
            }
        }
    }
    private void addNode( Node node ){
        if( head == null ){
            head = node;
            end = node;
        }else{
            addNodeToEnd(node);
        }
    }
    private void addNodeToEnd(Node node ){
        node.prev = end;
        end.next = node;
        end = node;
    }
    private void moveNode( Node node ){
        if( head == node && node != end){
            head = node.next;
//            使原本的结点成为孤立的结点-->被踢出了缓存
            head.prev = null;
            node.next = null;
//            再将新结点加入到队列尾部,远离被踢出缓存的命运
            addNodeToEnd( node );
        }else if( end == node){
//            不做任何操作
        }else{
//            普遍情况,node结点移除链表，加入到尾结点后面，tail指针指向node
            node.prev.next = node.next;
            node.next.prev = node.prev;
            node.next = null;
            addNodeToEnd( node );
        }
    }

    /**
     * 内部类Node作为队列（链表）的结点
     */
    static class Node{
        private Node prev;
        private Node next;
        public final String key;
        public String value;
        Node( String key,String value ){
            this.key = key;
            this.value= value;
        }
    }
}
