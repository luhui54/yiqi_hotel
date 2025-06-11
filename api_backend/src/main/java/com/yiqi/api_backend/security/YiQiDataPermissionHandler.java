package com.yiqi.api_backend.security;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.extension.plugins.handler.MultiDataPermissionHandler;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;

import java.util.List;

/**
 * @ClassName YiQiDataPermissionHandler
 * @Description 所有SQL执行的处理器
 * @Author lh
 * @Date 2024/11/23 22:03
 * @Version 1.0
 */
@Slf4j
public class YiQiDataPermissionHandler implements MultiDataPermissionHandler {

    private final static List<String> mappers = ListUtil.of("RoomTypeMapper", "RoomMapper", "OrderMapper");

    @Override
    public Expression getSqlSegment(Table table, Expression where, String mappedStatementId) {
        log.info("table is {}", table);
        log.info("where is {}", where);
        log.info("mappedStatementId is {}", mappedStatementId);

        // 酒店管理员才需要添加数据权限
        if (!StpKit.ADMIN.isLogin()) {
            return null;
        }

        if (!StpKit.ADMIN.getExtra("role").equals("HOTEL")) {
            return null;
        }

        // 拦截房型、房间、订单的增删改查操作sql，并添加数据权限
        String[] parts = mappedStatementId.split("\\.");
        if (!mappers.contains(parts[4])) {
            return null;
        }

        String column = "hotel_id";

        // 约定hotel_id的表别名为yiQi。
        if (table.getAlias() != null) {

            if (table.getAlias().getName().equals("yiQi")) {
                column = "yiQi.hotel_id";
            } else {
                return null;
            }
        }

        EqualsTo equals = new EqualsTo(new Column(column), new LongValue(Convert.toLong(StpKit.ADMIN.getExtra("hotelId"))));

        log.info("需要添加数据权限");
        return equals;
    }

}
