package com.metis.paas;

import com.github.istarwyh.Array;
import com.github.istarwyh.ListNode;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @Description: SolutionTest
 * @author: YiHui
 * @Date: 2020-12-04 17:20
 * @version: 1.0.0
 */

public class SolutionTest {
    @Test
    void test(){
        ListNode l1 = ListNode.createListNodeByArray ( Array.getArr(1,2,4));
        ListNode l2 = ListNode.createListNodeByArray ( Array.getArr(1,3,4));

        System.out.println( mergeTwoLists(l1,l2 ) );
    }
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode p1 = l1;
        ListNode p2 = l2;
        ListNode r1 = new ListNode(-1,null);
        ListNode preResNode = r1;
        while( p1 != null && p2 != null ){
            if( p1.val > p2.val ){
                r1.next = p2;
                p2 = p2.next;
            }else{
                r1.next = p1;
                p1 = p1.next;
            }
            r1 = r1.next;
        }
        if( p1 != null ){
            while( p1 != null ){
                r1.next = p1;
                p1 = p1.next;
                r1 = r1.next;
            }
        }
        if( p2 != null ){
            while( p2 != null ){
                r1.next = p2;
                p2 =p2.next;
                r1 = r1.next;
            }
        }
        return preResNode.next;
    }
    @Test
    void t(){
        System.out.println(Arrays.toString(intersect(Array.getArr(1, 2, 2, 1), Array.getArr(2, 2))));
    }
    public int[] intersect(int[] nums1, int[] nums2) {
        HashMap<Integer,Integer> map = new HashMap<>(16);
        for (int a : nums1) {
            map.put(a, map.getOrDefault(a, 0) + 1);
        }
        int index = 0;
        for( int i=0; i< nums2.length;i++ ){
            if( map.getOrDefault( nums2[i],0 ) > 0 ) {
                if( index != i ){
                    nums2[index] = nums2[i];
                }
                index++;
                map.put( nums2[i], map.get( nums2[i] )-1 );
            }
        }
        return Arrays.copyOfRange( nums2,0,index);
    }

}
