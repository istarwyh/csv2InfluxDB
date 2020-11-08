package com.metis.entity.excel;

import com.lkx.util.Excel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.websocket.server.ServerEndpoint;

/**
 * @author MBin_王艺辉istarwyh
 */
@Getter
@Setter
@ToString
public class FromExcel1 {
    @Excel(title = "省份")
    private String province;
    @Excel(title = "名单")
    private String item;
}
