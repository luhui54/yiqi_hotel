package com.yiqi.api_backend.common.lang;

/**
 * 订单状态常量
 */
public class OrderStatusConstants {

    public static final int PENDING_PAYMENT = 1;
    public static final String PENDING_PAYMENT_DESC = "待支付";

    public static final int PAID_AWAITING_CHECKIN = 2;
    public static final String PAID_AWAITING_CHECKIN_DESC = "已支付待入住";

    public static final int CHECKED_IN = 3;
    public static final String CHECKED_IN_DESC = "已入住";

    public static final int COMPLETED = 4;
    public static final String COMPLETED_DESC = "已完成";

    public static final int CANCELLED = 5;
    public static final String CANCELLED_DESC = "已取消";

    public static final int REFUNDED = 6;
    public static final String REFUNDED_DESC = "已退款";

    // 根据code获取对应的描述
    public static String getDescription(int code) {
        return switch (code) {
            case PENDING_PAYMENT -> PENDING_PAYMENT_DESC;
            case PAID_AWAITING_CHECKIN -> PAID_AWAITING_CHECKIN_DESC;
            case CHECKED_IN -> CHECKED_IN_DESC;
            case COMPLETED -> COMPLETED_DESC;
            case CANCELLED -> CANCELLED_DESC;
            case REFUNDED -> REFUNDED_DESC;
            default -> throw new IllegalArgumentException("Invalid order status code: " + code);
        };
    }
}
