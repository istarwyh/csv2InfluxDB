package com.metis.entity.excel;

import com.lkx.util.Excel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Description: CountyOfCity
 * @Author: YiHui
 * @Date: 2020-11-10 14:35
 * @Version: ing
 */
@Getter
@Setter
@ToString
public class CountyOfCity {
    @Excel(title = "市")
    private String city;
    @Excel(title = "市辖区")
    private String county;

}
