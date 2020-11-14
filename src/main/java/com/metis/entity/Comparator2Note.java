package com.metis.entity;

import com.lkx.util.Excel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Description: Comparator2Note
 * @Author: YiHui
 * @Date: 2020-11-12 10:27
 * @Version: ing
 */
@Getter
@Setter
@ToString
public class Comparator2Note {
    @Excel(title = "k1")
    private String k1;
    @Excel(title = "k2")
    private String k2;
    @Excel(title = "note")
    private String note;
}
