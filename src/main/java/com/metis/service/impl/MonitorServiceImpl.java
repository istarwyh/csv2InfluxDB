package com.metis.service.impl;

import java.util.List;

import com.metis.dao.monitor.MonitorMapper;
import com.metis.entity.Monitor;
import com.metis.service.IMonitorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.hutool.core.convert.Convert;

/**
 * @Description: MonitorServiceImpl
 * @Author: YiHui
 * @Date: 2021-01-29 16:52
 * @Version: ing
 */
@Service
public class MonitorServiceImpl implements IMonitorService {
    @Autowired
    private MonitorMapper monitorMapper;

    /**
     * 新增info of monitor
     *
     * @param monitor info of monitor信息
     * @return 结果
     */
    @Override
    public int insertMonitor(Monitor monitor) {
        return monitorMapper.insertMonitor(monitor);
    }

    /**
     * 删除info of monitor对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteMonitorByIds(String[] ids) {
        return monitorMapper.deleteMonitorByIds(Convert.toStrArray(ids));
    }

    @Override
    public int deleteMonitorById(Long id) {
        return monitorMapper.deleteMonitorById(id);
    }

    /**
     * 修改info of monitor
     *
     * @param monitor info of monitor信息
     * @return 结果
     */
    @Override
    public int updateMonitor(Monitor monitor) {
        return monitorMapper.updateMonitor(monitor);
    }

    /**
     * 查询info of monitor信息
     *
     * @param traceId info of monitorID
     * @return info of monitor信息
     */
    @Override
    public Monitor selectMonitorById(Long traceId) {
        return monitorMapper.selectMonitorById(traceId);
    }

    /**
     * 查询info of monitor列表
     *
     * @param monitor info of monitor信息
     * @return info of monitor集合
     */
    @Override
    public List<Monitor> selectMonitorList(Monitor monitor) {
        return monitorMapper.selectMonitorList(monitor);
    }
}
