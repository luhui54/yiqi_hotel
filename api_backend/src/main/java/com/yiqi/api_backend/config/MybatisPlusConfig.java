package com.yiqi.api_backend.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.DataPermissionInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.yiqi.api_backend.security.YiQiDataPermissionHandler;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MybatisPlus配置
 */
@Configuration
@MapperScan("com.yiqi.api_backend.mapper")
public class MybatisPlusConfig {

	@Bean
	MybatisPlusInterceptor mybatisPlusInterceptor() {
		MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

		DataPermissionInterceptor dataPermissionInterceptor = new DataPermissionInterceptor(new YiQiDataPermissionHandler());
		/**
		 * 数据权限插件
		 */
		interceptor.addInnerInterceptor(dataPermissionInterceptor);
		/**
		 * 分页插件
		 */
		interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
		/**
		 * 阻止恶意的全表更新删除
		 */
		interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
		return interceptor;
	}
}
