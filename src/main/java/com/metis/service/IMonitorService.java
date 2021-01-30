package com.metis.service;

import com.metis.entity.Monitor;

import java.util.List;

/**
 * @Description: IMonitorService
 * @Author: YiHui
 * @Date: 2021-01-29 16:49
 * @Version: ing
 */
public interface IMonitorService {
    /**
     * 查询info of monitor信息
     *
     * @param traceId info of monitorID
     * @return info of monitor信息
     */
    public Monitor selectMonitorById(Long traceId);

    /**
     * 查询info of monitor列表
     *
     * @param monitor info of monitor信息
     * @return info of monitor集合
     */
    public List<Monitor> selectMonitorList(Monitor monitor);

    /**
     * 新增info of monitor
     *
     * @param monitor info of monitor信息
     * @return 结果
     */
    public int insertMonitor(Monitor monitor);

    /**
     * 修改info of monitor
     *
     * @param monitor info of monitor信息
     * @return 结果
     */
    public int updateMonitor(Monitor monitor);

    /**
     * 删除info of monitor信息
     *
     * @param ids 需要删除的数据IDs
     * @return 结果
     */
    public int deleteMonitorByIds(String[] ids);

    /**
     * 删除info of monitor信息
     *
     * @param id 需要删除的数据ID
     * @return 结果
     */
    public int deleteMonitorById(Long id);

}
