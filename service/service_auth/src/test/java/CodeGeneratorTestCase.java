import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.Test;

/**
 * @author BNTang
 */
public class CodeGeneratorTestCase {
    @Test
    public void rbacGenerator() {
        // 1、创建代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 2、全局配置
        GlobalConfig gc = new GlobalConfig();

        // 配置输出路径
        gc.setOutputDir("D:\\Java\\VIDEO_Parent\\service\\service_auth\\src\\main\\java");
        gc.setAuthor("BNTang");

        // 生成后是否打开资源管理器
        gc.setOpen(false);

        // 重新生成时文件是否覆盖
        gc.setFileOverride(false);

        // mp生成service层代码，默认接口名称第一个字母有 I
        gc.setControllerName("%sController");
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");

        // 主键策略
        gc.setIdType(IdType.ID_WORKER_STR);

        // 定义生成的实体类中日期类型
        gc.setDateType(DateType.ONLY_DATE);

        // 开启Swagger2模式
        gc.setSwagger2(true);
        mpg.setGlobalConfig(gc);

        // 3、数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/video_db?serverTimezone=GMT%2B8");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root");
        dsc.setDbType(DbType.MYSQL);
        mpg.setDataSource(dsc);

        // 4、包配置
        PackageConfig pc = new PackageConfig();

        // 模块名
        pc.setModuleName("service_auth");

        // top.it6666.模块名称.功能 → top.it6666.service_auth.controller
        pc.setParent("top.it6666");
        pc.setController("controller");
        pc.setEntity("entity");
        pc.setService("service");
        pc.setMapper("mapper");
        mpg.setPackageInfo(pc);

        // 5、策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setInclude("auth_menu", "auth_role", "auth_role_menu", "auth_user", "auth_user_role");

        // 数据库表映射到实体的命名策略 驼峰命名
        strategy.setNaming(NamingStrategy.underline_to_camel);

        // 生成实体时去掉表前缀
        strategy.setTablePrefix("auth_");

        // 数据库表字段映射到实体的命名策略
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);

        // lombok 模型 @Accessors(chain = true) setter链式操作
        strategy.setEntityLombokModel(true);

        // restful api风格控制器
        strategy.setRestControllerStyle(true);

        // url中驼峰转连字符
        strategy.setControllerMappingHyphenStyle(true);
        mpg.setStrategy(strategy);

        // 6、执行
        mpg.execute();
    }
}