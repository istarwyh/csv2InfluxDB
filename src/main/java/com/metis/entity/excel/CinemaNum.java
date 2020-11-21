package com.metis.entity.excel;

import com.lkx.util.Excel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Description: CinemaNum
 * @Author: YiHui
 * @Date: 2020-11-19 16:11
 * @Version: ing
 */
@Getter
@Setter
@ToString
public class CinemaNum {
    @Excel( title = "省")
    private String province;
    @Excel( title = "地区")
    private String county;
    @Excel( title = "电影院数目")
    private Integer cinemaNum;
}
