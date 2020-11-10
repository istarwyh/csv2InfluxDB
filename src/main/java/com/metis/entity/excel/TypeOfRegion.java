package com.metis.entity.excel;

import com.lkx.util.Excel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Description: TypeOfRegion
 * @Author: YiHui
 * @Date: 2020-11-10 16:21
 * @Version: ing
 */
@Getter
@Setter
@ToString
public class TypeOfRegion extends RegionOfChina {
    @Excel(title = "编码")
    private String regionCode;
    @Excel(title = "省")
    private String province;
    @Excel(title = "市")
    private String city;
    @Excel(title = "区")
    private String county;
    @Excel(title = "行政区划类型")
    private String typeOfRegion;

}
