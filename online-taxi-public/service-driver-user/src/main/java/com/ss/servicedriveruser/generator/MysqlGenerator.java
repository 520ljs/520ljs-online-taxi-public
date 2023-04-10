package com.ss.servicedriveruser.generator;

/**
 * @Author:ljy.s
 * @Date:2023/4/10 - 04 - 10 - 17:40
 */

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

/**
 * 自动生成代码工具类
 */
public class MysqlGenerator {

    public static void main(String[] args) {

        FastAutoGenerator.create("jdbc:mysql://localhost:3306/service-driver-user?characterEncoding=utf-8&serverTimezone=GMT%2B8",
                "root",
                "root")
                .globalConfig(builder -> {
                    builder.author("520ljs").fileOverride().outputDir("D:\\Java\\SourceCode\\IDEA_workspace\\GitHub\\taxi\\520ljs-online-taxi-public\\online-taxi-public\\service-driver-user\\src\\main\\java");
                })
                .packageConfig(builder -> {
                    builder.parent("com.ss.servicedriveruser").pathInfo(Collections.singletonMap(OutputFile.mapperXml,
                            "D:\\Java\\SourceCode\\IDEA_workspace\\GitHub\\taxi\\520ljs-online-taxi-public\\online-taxi-public\\service-driver-user\\src\\main\\java\\com\\ss\\servicedriveruser\\mapper"));
                })
                .strategyConfig(builder -> {
                    builder.addInclude("car");
                })
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();

    }
}
