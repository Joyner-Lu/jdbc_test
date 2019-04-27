package com.joyner.jdbc_test.util;

import com.joyner.jdbc_test.monitor.IMonitorService;
import com.joyner.jdbc_test.monitor.MonitorInfoBean;
import com.joyner.jdbc_test.monitor.MonitorServiceImpl;

public class JVMMemoryUtil {

    public static void pritnMemoryInfo() {
        try {
            IMonitorService service = new MonitorServiceImpl();
            MonitorInfoBean monitorInfo = service.getMonitorInfoBean();
            int mb =1;
            System.out.println("剩余内存:" + monitorInfo.getFreeMemory()/mb + "KB,可使用内存:" + monitorInfo.getTotalMemory()/mb + "KB,最大可使用内存:" + monitorInfo.getMaxMemory() + "KB");
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

    }
}
