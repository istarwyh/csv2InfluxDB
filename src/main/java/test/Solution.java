import org.springframework.util.StopWatch;
import test.Stopwatch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

class Solution {
    public static void main(String[] args) {
        int[][] strs = {{0,0},{1,0}, {2,0}};
        Stopwatch timer = new Stopwatch();
        int res = 0;
        int i=0;
        while (i++ < 10)
            res = new Solution().numberOfBoomerangs(strs);
        double time = timer.elapsedTime();
        System.out.println(res + " triples " + time + " seconds");
    }

    // 这里的i为枢纽点，这个意思围绕它设立查找表
    public int numberOfBoomerangs(int[][] points) {
        int count = 0;
        for( int i=0;i<points.length;i++){
            HashMap<Integer,Integer> map = new HashMap<>();
            for( int j =0 ;j<points.length;j++){
                if( j != i){
                    int distance = this.distance(points[i],points[j]);
                    map.put( distance, map.getOrDefault(distance,0)+1 );
                }
            }
            for( Integer a : map.values())
                count += this.Arrangement(a,2);
        }
        return count;
    }
    public int distance( int[] i,int[] j){
        return (i[0]-j[0])*(i[0]-j[0])+(i[1]-j[1])*(i[1]-j[1]);
    }
    // 计算排列数
    public int Arrangement(int n,int m){
        if( n < m ) return 0;
        int product = 1;
        while( n >= m){
            product *= n;
            n--;
        }
        return product;
    }
}