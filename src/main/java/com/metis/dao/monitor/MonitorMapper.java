package com.metis.dao.monitor;

import com.metis.entity.Monitor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Description: MonitorMapper
 * @Author: YiHui
 * @Date: 2021-01-19 21:45
 * @Version: ing
 */
@Mapper
public interface MonitorMapper
{

    /**
     * 新增info of monitor
     *
     * @param monitor info of monitor信息
     * @return 结果
     */
    public int insertMonitor(Monitor monitor);

    /**
     * 删除info of monitor
     *
     * @param traceId info of monitorID
     * @return 结果
     */
    public int deleteMonitorById(Long traceId);

    /**
     * 批量删除info of monitor
     *
     * @param traceIds 需要删除的数据ID,note String[]
     * @return 结果
     */
    public int deleteMonitorByIds(String[] traceIds);

    /**
     * 修改info of monitor
     *
     * @param monitor info of monitor信息
     * @return 结果
     */
    public int updateMonitor(Monitor monitor);

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

}