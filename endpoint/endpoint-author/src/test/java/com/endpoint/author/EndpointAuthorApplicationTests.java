package com.endpoint.author;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;

@SpringBootTest
class EndpointAuthorApplicationTests {

    @Test
    void contextLoads() {

        String url = "jdbc:mysql://192.168.0.234:3306/endpoint_home?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai";

        FastAutoGenerator.create(url, "root", "123456")
                .globalConfig(builder -> {
                    builder.author("cr") // 设置作者
                            //.enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("E://Code//"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.endpoint") // 设置父包名
                            .moduleName("home") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "E://Code//")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("home_book","home_friendlink","home_news","home_news_category"); // 设置需要生成的表名
                            //.addTablePrefix("t_", "c_"); // 设置过滤表前缀
                })
                //.templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
