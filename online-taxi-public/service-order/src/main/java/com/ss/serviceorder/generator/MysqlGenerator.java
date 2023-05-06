package com.ss.serviceorder.generator;

/**
 * @Author:ljy.s
 * @Date:2023/4/10 - 05 - 6 - 15:31
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

        FastAutoGenerator.create("jdbc:mysql://localhost:3306/service-order?characterEncoding=utf-8&serverTimezone=GMT%2B8",
                        "root",
                        "root")
                .globalConfig(builder -> {
                    builder.author("520ljs").fileOverride().outputDir("D:\\Java\\SourceCode\\IDEA_workspace\\GitHub\\taxi\\520ljs-online-taxi-public\\online-taxi-public\\service-order\\src\\main\\java");
                })
                .packageConfig(builder -> {
                    builder.parent("com.ss.serviceorder").pathInfo(Collections.singletonMap(OutputFile.mapperXml,
                            "D:\\Java\\SourceCode\\IDEA_workspace\\GitHub\\taxi\\520ljs-online-taxi-public\\online-taxi-public\\service-order\\src\\main\\java\\com\\ss\\serviceorder\\mapper"));
                })
                .strategyConfig(builder -> {
                    builder.addInclude("order_info");
                })
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();

    }
}
