//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.insworks.lib_net.net.utils;

import com.insworks.lib_net.NetApplication;
import com.ta.utdid2.device.UTDevice;
public class UdidUtil {
    private static String CHANNEL = null;
    private static String UDID = null;

    public UdidUtil() {
    }

    public static String getUdid() {
        if (UDID == null) {
            UDID = UTDevice.getUtdid(NetApplication.getInstance());
        }

        return UDID;
    }


}
