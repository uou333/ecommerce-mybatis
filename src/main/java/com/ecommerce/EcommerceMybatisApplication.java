package com.ecommerce;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 电商系统商品管理模块启动类
 *
 * @author 实验小组
 */
@SpringBootApplication
// 批量扫描 com.ecommerce.mapper 包下的 Mapper 接口并注册为 Bean
@MapperScan("com.ecommerce.mapper")
public class EcommerceMybatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcommerceMybatisApplication.class, args);
    }
}
