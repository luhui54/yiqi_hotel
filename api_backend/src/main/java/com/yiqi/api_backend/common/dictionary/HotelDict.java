package com.yiqi.api_backend.common.dictionary;

import cn.hutool.core.map.MapUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName HotelDict
 * @Description 酒店的数据字典
 * @Author lh
 * @Date 2024/11/13 15:14
 * @Version 1.0
 */
public class HotelDict {

    public final static Map<Integer,String> rankDict = new HashMap<>();
    public final static Map<Integer, String> scoreDict = new HashMap<>();
    public final static Map<Integer, String> brandDict = new HashMap<>();
    public final static Map<Integer, String> priceDict = new HashMap<>();

    public final static Map<Integer, String> statusDict = new HashMap<>();

    static {
        rankDict.put(1,"民宿");
        rankDict.put(2,"经济");
        rankDict.put(3,"三星/舒适");
        rankDict.put(4,"四星/高档");
        rankDict.put(5,"五星/豪华");

        scoreDict.put(1, "4.8以上");
        scoreDict.put(2, "4.5以上");
        scoreDict.put(3, "4.0以上");

        brandDict.put(1, "汉庭");
        brandDict.put(2, "7天");
        brandDict.put(3, "速8");
        brandDict.put(4, "维也纳");
        brandDict.put(5, "希尔顿欢朋");
        brandDict.put(6, "城市便捷");
        brandDict.put(7, "IU");
        brandDict.put(8, "香格里拉");

        priceDict.put(1, "150以下");
        priceDict.put(2, "150-300");
        priceDict.put(3, "301-450");
        priceDict.put(4, "451-700");
        priceDict.put(5, "700以上");

        statusDict.put(1, "待支付");
        statusDict.put(2, "待入住");
        statusDict.put(3, "已入住");
        statusDict.put(4, "已完成");
        statusDict.put(5, "已取消");
    }

    public static List<Map<Object, Object>> getMapList(Map<Integer,String> dict) {
        return dict.entrySet()
                .stream()
                .map(e -> MapUtil
                        .builder()
                        .put("key",e.getKey())
                        .put("value",e.getValue())
                        .build())
                .collect(Collectors.toList());
    }
}
