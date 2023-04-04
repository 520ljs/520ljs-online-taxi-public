package com.ss.internalcommon.constant;

/**
 * @Author: ljy.s
 * @Date: 2023/3/29 - 03 - 29 - 18:01
 */
public class AmapConfigConstants {

    /**
     * 路径规划地址
     */
    public static final String DIRECTION_URL = "https://restapi.amap.com/v3/direction/driving";

    /**
     * 行政区查询地址
     */
    public static final String DISTRICT_URL = "https://restapi.amap.com/v3/config/district";

    /**
     * 路径规划 json key值
     * 0：请求失败，1：请求成功
     */
    public static final String STATUS = "status";

    public static final String ROUTE = "route";

    public static final String PATHS = "paths";

    public static final String DISTANCE = "distance";

    public static final String DURATION = "duration";

}
