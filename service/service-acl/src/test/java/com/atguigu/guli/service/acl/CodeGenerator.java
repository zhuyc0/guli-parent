package com.atguigu.guli.service.acl;

import com.atguigu.guli.service.base.model.BaseEntity;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;


/**
 * CodeGenerator mybatis-plus 代码生成器
 * @author <a href="zyc199777@gmail.com">Zhu yc</a>
 * @version 1.0
 * @date 2020年05月13日
 */
public class CodeGenerator {

    public static void main(String[] args) {
        String author = "zhuyc";
        String dbUrl = "www.java-mysql-dev.com:3306/guli";
        String dbName = "root";
        String dbPass = "1";
        String path = System.getProperty("user.dir")+"\\service\\service-acl\\src\\main\\java";
        String parentName = "com.atguigu.guli.service";
        String moduleName = "acl";
        String[] tables = {"acl_permission","acl_role_permission","acl_role","acl_user","acl_user_role"};
        execute(author,dbUrl,dbName,dbPass,path,parentName,moduleName,tables);
    }

    public static void execute(
            String author,
            String dbUrl,
            String dbName,
            String dbPass,
            String path,
            String parentName,
            String moduleName,
            String[] tables
    ) {
        // 代码生成器
        AutoGenerator generator = new AutoGenerator();

        // 全局配置
        GlobalConfig config = new GlobalConfig();

        config.setOutputDir(path)
                .setFileOverride(false)
                .setOpen(false)
                .setEnableCache(false)
                .setAuthor(author)
                .setSwagger2(true)
                .setBaseResultMap(true)
                .setBaseColumnList(true)
                .setIdType(IdType.ASSIGN_ID)
                .setDateType(DateType.TIME_PACK)
                .setEntityName("%sEntity")
                .setMapperName("%sMapper")
                .setXmlName("%sMapper")
                .setServiceName("%sService")
                .setServiceImplName("%sServiceImpl")
                .setControllerName("%sController");

        // 数据源配置
        DataSourceConfig dataSource = new DataSourceConfig();
        dataSource.setDbType(DbType.MYSQL)
                .setUrl("jdbc:mysql://" + dbUrl + "?useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&useSSL=false&serverTimezone=Asia/Shanghai")
                .setDriverName("com.mysql.cj.jdbc.Driver")
                .setUsername(dbName)
                .setPassword(dbPass);

        PackageConfig pc = new PackageConfig();
        pc.setParent(parentName)
                .setModuleName(moduleName)
                .setEntity("entity")
                .setService("service")
                .setServiceImpl("service.impl")
                .setMapper("mapper")
                .setXml("xml")
                .setController("controller");

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel)
                .setColumnNaming(NamingStrategy.underline_to_camel)
                .setTablePrefix(moduleName+"_")
                .setChainModel(true)
                .setEntityLombokModel(true)
                .setEntityBooleanColumnRemoveIsPrefix(true)
                .setRestControllerStyle(true)
                .setEntityTableFieldAnnotationEnable(true)
                .setInclude(tables)
                .setLogicDeleteFieldName("is_deleted")
                .setControllerMappingHyphenStyle(true);

        //自动填充
        TableFill gmtCreate = new TableFill("gmt_create", FieldFill.INSERT);
        TableFill gmtModified = new TableFill("gmt_modified", FieldFill.INSERT_UPDATE);
        ArrayList<TableFill> tableFills = new ArrayList<>();
        tableFills.add(gmtCreate);
        tableFills.add(gmtModified);
        strategy.setTableFillList(tableFills);

        //设置BaseEntity
        strategy.setSuperEntityClass(BaseEntity.class);
        // 填写BaseEntity中的公共字段
        strategy.setSuperEntityColumns("id", "gmt_create", "gmt_modified");

        generator.setGlobalConfig(config)
                .setDataSource(dataSource)
                .setStrategy(strategy)
                .setPackageInfo(pc);

        generator.execute();
    }
}
