package com.metis.entity.excel;


import com.lkx.util.Excel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author MBin_王艺辉istarwyh
 */
@Setter
@Getter
@ToString
public class OutOfPovertyRegion {
    @Excel(title = "省")
    private String province;
    @Excel(title = "市")
    private String city;
    @Excel(title = "区")
    private String county;
    @Excel(title = "摘帽年份")
    private String outOfPovertyYear;
}
