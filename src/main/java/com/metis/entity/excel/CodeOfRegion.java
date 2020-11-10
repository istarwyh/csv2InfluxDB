package com.metis.entity.excel;

import com.lkx.util.Excel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Description: CodeOfRegion
 * @Author: YiHui
 * @Date: 2020-11-09 15:43
 * @Version: ing
 */
@Getter
@Setter
@ToString
public class CodeOfRegion {
    @Excel(title = "编码")
    private String regionCode;
    @Excel(title = "区")
    private String county;
}
