package com.metis.infrastructure;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

import com.github.istarwyh.Array;
import com.github.istarwyh.ListNode;
import com.metis.annotation.log.ExeTimeLog;

/**
 * @Description: SolutionTest
 * @author: YiHui
 * @Date: 2020-12-04 17:20
 * @version: 1.0.0
 */
@Service
@SpringBootTest
public class SolutionTest {

    @Test
    void test() {
        ListNode l1 = ListNode.createListNodeByArray(Array.getArr(1));
        ListNode l2 = ListNode.createListNodeByArray(Array.getArr(1, 3, 4));
        //        Error: Set 'exposeProxy' property on Advised to 'true' to make it available, and ensure that
        //              AopContext.currentProxy() is invoked in the same thread as the AOP invocation context.
        //        ((SolutionTest) AopContext.currentProxy()).reorderList(l1);

        //        by this way, Exception:
        //          in thread "task-2" java.lang.IllegalStateException: EntityManagerFactory is closed
        //          The reason may be that the thread invoked by test() is closed compulsorily when test() closed meanwhile the JPA EntityManager is closed,
        //              but due to @SpringBootTest Spring want to initialize EntityManagerFactory after that.
        //        SpringUtil.getProxyObj(this.getClass()).reorderList(l1);
        System.out.println(rotateRight(l1,1));
    }

    public  ListNode rotateRight(ListNode head, int k) {
            assert(k>=0);
            if( k == 0 || head == null) return head;
            ListNode dummyNode = new ListNode(-1);
            dummyNode.next = head;
            ListNode pre = dummyNode;
            ListNode cur = dummyNode;
            ListNode end = dummyNode;
            ListNode tmp = head;

            int len = 0;
            while(tmp != null){
                tmp = tmp.next;
                len++;
            }
            k = k> len ? k%len : k;

//            让end先多走一步
            end = end.next;
            for( int i=0;i< k-1;i++){
                cur = cur.next;
                end = end.next;
            }

            while( end != null ){
//                旋转元素的头一个结点
                pre = pre.next;
//                尾结点
                cur = cur.next;
//                空结点
                end = end.next;
            }
            ListNode retNode;
            assert (pre != null);
            if( pre.next == null )
                retNode = dummyNode.next;
            else{
                retNode = pre.next;
            }
            if( cur != null ) {
                cur.next = dummyNode.next;
                pre.next = null;
            }
            return retNode;
    }

    public int findKthLargest(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k < 1)
            return -1;
        int lo = 0;
        int hi = nums.length - 1;
        int requiredIndex = nums.length - k;
        while (true) {
            int j = partition(nums, lo, hi);
            if (j > requiredIndex) {
                hi = j - 1;
            } else if (j < requiredIndex) {
                lo = j + 1;
            } else {
                return nums[j];
            }
        }
    }

    public int partition(int[] nums, int lo, int hi) {
        if (lo >= hi) {
            return lo;
        }
        int i = lo;
        int j = hi + 1;
        int pivot = nums[lo];
        while (true) {
            while (less(nums[++i], pivot)) {
                if (i == hi)
                    break;
            }
            while (less(pivot, nums[--j])) {
                if (j == lo)
                    break;
            }
            if(i >= j){
                break;
            }
            swap(nums,i,j);
        }
        swap(nums,lo,j);
        return j;
    }
    public void swap(int[] nums,int i,int j ){
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
    public boolean less(int a, int b) {
        return a < b;
    }

    public ArrayList<Integer> GetLeastNumbers_Solution(int[] input, int k) {
        if (k > input.length)
            return new ArrayList<>();
        Arrays.sort(input);
        ArrayList<Integer> l = new ArrayList<Integer>(16);
        for (int i : Arrays.copyOfRange(input, 0, 4)) {
            l.add(i);
        }
        return l;
    }

    public ListNode swapPairs(ListNode head) {
        if (head == null)
            return null;
        ListNode dummyNode = new ListNode();
        dummyNode.next = head;
        ListNode pre = dummyNode;
        while (pre.next != null && pre.next.next != null) {
            ListNode cur = pre.next;
            ListNode next = cur.next;
            //
            cur.next = next.next;
            next.next = cur;
            pre.next = next;

            pre = cur;
        }
        return dummyNode.next;
    }

    @ExeTimeLog
    public void reorderList(ListNode head) {
        if (head == null || head.next == null)
            return;
        ListNode endNode1 = getSplitNode(head);
        ListNode startNode2 = endNode1.next;
        ListNode l1 = getFirstPart(head, endNode1);
        ListNode l2 = reverseListAsSecondPart(startNode2);
        merge(l1, l2);
    }

    private ListNode getSplitNode(ListNode head) {
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
     * 
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
