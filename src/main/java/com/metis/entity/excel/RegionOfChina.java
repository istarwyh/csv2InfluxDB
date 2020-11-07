package com.metis.entity.excel;


import com.lkx.util.Excel;

public class RegionOfChina {
    @Excel(title = "省")
    private String province;
    @Excel(title = "市")
    private String city;
    @Excel(title = "县")
    private String county;
    @Excel(title = "脱贫摘帽年份")
    private String outOfPovertyYear;
}
