package com.metis.entity.excel;

import com.lkx.util.Excel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Description: ProvinceCityStation
 * @Author: YiHui
 * @Date: 2020-11-16 17:41
 * @Version: ing
 */
@Getter
@Setter
@ToString
public class ProvinceCityStation {
    @Excel( title = "省")
    private String province;
    @Excel( title = "火车站")
    private String station;
    @Excel( title = "经过列车次数")
    private String railwayNum;
}
