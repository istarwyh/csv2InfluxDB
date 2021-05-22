package com.metis.controller.api;

import java.util.Map;

/**
 * @Description: BaseController
 * @Author: YiHui
 * @Date: 2021-01-30 15:17
 * @Version: ing
 */
public interface BaseController<T> extends Create<T>, Delete<Map<String, String>>, Update<T>, Read<T> {
}
