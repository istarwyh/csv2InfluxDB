package com.metis.controller.api;
import com.metis.entity.User;

import java.util.Map;

/**
 * @Description: BaseController
 * @Author: YiHui
 * @Date: 2021-01-30 15:17
 * @Version: ing
 */
public interface BaseController<T> extends Insert<T>, Delete<Map<String, String>>, Update<T>, Query<T> {
}
