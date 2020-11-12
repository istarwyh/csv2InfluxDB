package com.metis.entity.excel;

import com.lkx.util.Excel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Description: CountyLevelCity
 * @Author: YiHui
 * @Date: 2020-11-10 13:45
 * @Version: ing
 */
@Setter
@Getter
@ToString
public class CountyLevelCity {
    @Excel(title = "地级市")
    private String province;
    @Excel(title = "县级市")
    private String city;
}
