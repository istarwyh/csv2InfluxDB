package com.metis.entity.excel;

import com.lkx.util.Excel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Description: ICBCStaionNum
 * @Author: YiHui
 * @Date: 2020-11-19 14:28
 * @Version: ing
 */
@Getter
@Setter
@ToString
public class BankStaionNum {
    @Excel( title = "省")
    private String province;
    @Excel( title = "市或区")
    private String county;
    @Excel( title = "网点数目")
    private Integer staionNum;
}
