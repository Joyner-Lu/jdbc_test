package com.joyner.jdbc_test;

import com.joyner.jdbc_test.monitor.IMonitorService;
import com.joyner.jdbc_test.monitor.MonitorInfoBean;
import com.joyner.jdbc_test.monitor.MonitorServiceImpl;
import com.joyner.jdbc_test.util.JVMMemoryUtil;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
        JVMMemoryUtil.pritnMemoryInfo();

    }
}
