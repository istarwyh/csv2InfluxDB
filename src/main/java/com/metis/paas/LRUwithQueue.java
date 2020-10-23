package com.metis.paas;

import org.w3c.dom.Node;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

/**
 * 类LRUwithQueue的实现描述：哈希表快速存储和更新被缓存的内容，队列管理最近被访问的状态
 *
 * @author sx_wangyihui 2020/10/16  21:11
 */
public class LRUwithQueue{
    /**
    *   最近最少使用,类似队列的头,出队
    */
    private Node head;
    /**
     * 最近最少使用，类似队列的尾，入队
     */
    private Node tail;
    private final Map<Integer,Node> cache;
    private final int capacity;

    public LRUwithQueue( int capacity ){
        this.cache = new HashMap<>();
        this.capacity = capacity;
    }

    public int get( int key ){
        Node node = cache.get(key);
        if( node == null ){
            return -1;
        }else{
            moveNode( node );
            return node.value;
        }
    }

    public void put( int key,int value ){
        Node node = cache.get(key);
        if( node == null ){
            removeHead();
            node = new Node(key,value);
            addNode(node);
        }else{
            node.value = value;
            moveNode(node);
        }
        cache.put( key,node );
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
            tail = node;
        }else{
            addNodeToTail(node);
        }
    }
    private void addNodeToTail( Node node ){
        node.prev = tail;
        tail.next = node;
        tail = node;
    }
    private void moveNode( Node node ){
        if( head == node && node != tail ){
            head = node.next;
            head.prev = null;
            node.next = null;
            addNodeToTail( node );
        }else if( tail == node){
//            不做任何操作
        }else{
//            普遍情况,node结点移除链表，加入到尾结点后面，tail指针指向node
            node.prev.next = node.next;
            node.next.prev = node.prev;
            node.next = null;
            addNodeToTail( node );
        }
    }

    /**
     * 内部类Node作为队列（链表）的结点
     */
    static class Node{
        private Node prev;
        private Node next;
        private final int key;
        private int value;
        Node( int key,int value ){
            this.key = key;
            this.value= value;
        }
    }
}
