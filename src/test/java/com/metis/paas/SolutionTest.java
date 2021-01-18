package com.metis.paas;

import org.junit.jupiter.api.Test;

import com.github.istarwyh.Array;
import com.github.istarwyh.ListNode;
import com.github.istarwyh.factory.ListNodes;

import lombok.var;

/**
 * @Description: SolutionTest
 * @author: YiHui
 * @Date: 2020-12-04 17:20
 * @version: 1.0.0
 */

public class SolutionTest {
    @Test
    void test() {
        ListNode l1 = ListNode.createListNodeByArray(Array.getArr(1, 2, 3, 4));
        ListNode l2 = ListNode.createListNodeByArray(Array.getArr(1, 3, 4));
        reorderList(l1);
        System.out.println(l1);
    }

    public void reorderListWithLength(ListNode head) {
        if (head == null || head.next == null)
            return;
        int nodeNo = length(head) - 1;
        int mid = (int) Math.floor(nodeNo / 2.0);
        ListNode splitNode = getSplitNode(head, mid);
        ListNode secondPartStartNode = splitNode.next;
        var p1 = getFirstPart(head, splitNode);
        var p2 = reverseListAsSecondPart(secondPartStartNode);
        head = mergeTwo(nodeNo + 1, p1, p2);
    }

    private int length(ListNode head) {
        int len = 0;
        ListNode cur = head;
        while (cur != null) {
            cur = cur.next;
            len++;
        }
        return len;
    }

    private ListNode getSplitNode(ListNode head, int mid) {
        ListNode cur = head;
        for (int i = 0; i < mid; i++) {
            cur = cur.next;
        }
        return cur;
    }

    private ListNode mergeTwo(int len, ListNode p1, ListNode p2) {
        int count = 0;
        var dummyNode = ListNodes.create(-1);
        while (count < len) {
            if (count % 2 == 0) {
                dummyNode.next = p1;
                p1 = p1.next;
            } else {
                dummyNode.next = p2;
                p2 = p2.next;
            }
            count++;
            dummyNode = dummyNode.next;
        }
        return dummyNode.next;
    }

    public void reorderList(ListNode head) {
        if (head == null || head.next == null)
            return;
        ListNode endNode1 = getSplitNode(head);
        ListNode startNode2 = endNode1.next;
        ListNode l1 = getFirstPart(head,endNode1);
        ListNode l2 = reverseListAsSecondPart(startNode2);
        merge(l1, l2);
    }
    private ListNode getSplitNode(ListNode head){
        ListNode prev = null, slow = head, fast = head;
        while (fast != null && fast.next != null) {
            prev = slow;
            slow = slow.next;
            fast = fast.next.next;
        }
        return prev;
    }

    /**
     * 切割得到第一部分链表
     * @param head 链表原始头结点
     * @param endNode 切割后第一部分链表尾结点
     * @return
     */
    private ListNode getFirstPart(ListNode head, ListNode endNode) {
//        注释上写why
//        保证第一部分与第二部分切断联系
        endNode.next = null;
        return head;
    }

    private ListNode reverseListAsSecondPart(ListNode head) {
        if (head == null)
            return null;
        ListNode prev = null;
        ListNode curr = head;
        ListNode nextTemp = null;
        while (curr != null) {
            nextTemp = curr.next;
            curr.next = prev;

            prev = curr;
            curr = nextTemp;
        }
        return prev;
    }

    /**
     *
     * @param l1 head所在的链表，同时也是切割后第一部分链表
     * @param l2 切割后第二部分链表
     */
    private void merge(ListNode l1, ListNode l2) {
        while (l1 != null) {
            ListNode n1 = l1.next, n2 = l2.next;
            l1.next = l2;
            if (n1 == null) {
                break;
            }
            l2.next = n1;
            l1 = n1;
            l2 = n2;
        }
    }
}
