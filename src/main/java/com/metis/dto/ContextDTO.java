package com.metis.dto;

import com.metis.entity.UserDO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Description: ContextDTO
 * @author: YiHui
 * @Date: 2020-12-11 21:39
 * @version: 1.0.0
 */
@Getter
@Setter
@ToString
public class ContextDTO {
    private UserDO userDO;
}
