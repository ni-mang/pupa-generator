package com.nimang.pupa.business.controller;

import cn.hutool.core.codec.Base64;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.jfinal.kit.Kv;
import com.jfinal.template.Engine;
import com.jfinal.template.Template;
import com.nimang.pupa.dbExtends.mysql.entity.MysqlColumns;
import com.nimang.pupa.dbExtends.mysql.entity.MysqlTables;
import com.nimang.pupa.dbExtends.mysql.mapper.MysqlColumnsMapper;
import com.nimang.pupa.dbExtends.mysql.mapper.MysqlTablesMapper;
import com.nimang.pupa.common.annotation.IgnoreAuth;
import com.nimang.pupa.common.tool.webTool.BaseModel;
import com.nimang.pupa.common.tool.webTool.BizModel;
import com.nimang.pupa.common.tool.webTool.R;
import com.nimang.pupa.common.util.ZipUtil;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代码生成/测试
 * @module pupa
 * @date 2023-04-12
 */

@RestController
@RequestMapping(value = "/pupa/td")
@RequiredArgsConstructor
public class TestDatesource {

    /**
     * 测试动态数据库连接查询
     * @param bizModel
     * @return
     */
    @IgnoreAuth
    @PostMapping("/link")
    public R<Boolean> link(@Validated @RequestBody BizModel<BaseModel> bizModel) {

        PooledDataSource dataSource = new PooledDataSource();
        dataSource.setDriver("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/information_schema?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8&useSSL=true");
        dataSource.setUsername("root");
        dataSource.setPassword("root123");
        try {
            ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource[] resource = resolver.getResources("classpath:/mapper/**.xml");
            MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
            sqlSessionFactoryBean.setMapperLocations(resource);
            sqlSessionFactoryBean.setDataSource(dataSource);
            sqlSessionFactoryBean.setConfiguration(new MybatisConfiguration());
            SqlSessionFactory sessionFactory = sqlSessionFactoryBean.getObject();
            assert sessionFactory != null;
            SqlSession sqlSession = sessionFactory.openSession();

            MysqlTablesMapper tbMapper = sqlSession.getMapper(MysqlTablesMapper.class);
            List<MysqlTables> mysqlTablesList = tbMapper.selectList(new LambdaQueryWrapper<MysqlTables>().eq(MysqlTables::getTableSchema, "pupa"));

            MysqlColumnsMapper colMapper = sqlSession.getMapper(MysqlColumnsMapper.class);
            List<MysqlColumns> mysqlColumnsList = colMapper.selectList(new LambdaQueryWrapper<MysqlColumns>().eq(MysqlColumns::getTableSchema, "pupa").eq(MysqlColumns::getTableName, "sys_user"));

            sqlSession.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return R.ok();
    }

    /**
     * 测试代码生成
     */
    @IgnoreAuth
    @GetMapping("/velocity")
    public void velocity() {
        Map<String, Object> map = new HashMap<>();
        map.put("ifTrue", 1);
        map.put("test1", "123456");
        map.put("test2", "789");

        String base64Str = "Cei/meaYr+S4gOS4qumAu+i+kea1i+ivle+8mgojaWYoJHtpZlRydWV9ID09IDApCgnmtYvor5XmlofmnKwxJHt0ZXN0MX07CiNlbHNlCgnmtYvor5XmlofmnKwyJHt0ZXN0Mn07CiNlbmQ=";
        String text = Base64.decodeStr(base64Str);

        StringWriter sw = new StringWriter();

        //渲染模板
        VelocityContext context = new VelocityContext(map);
        Velocity.evaluate(context, sw, "mystring", text);

        //下载
        try {
            List<ZipUtil.ZipByteInfo> byteInfoList = new ArrayList<>();
            byteInfoList.add(new ZipUtil.ZipByteInfo("common/entity/test.txt", sw.toString().getBytes(StandardCharsets.UTF_8)));
            InputStream inputStream = ZipUtil.toZipForBytes(byteInfoList);
            ZipUtil.downloadZip(inputStream, "testCode.zip");
        } catch (Exception e) {
            throw new RuntimeException("下载ZIP压缩包异常", e);
        }
    }

    /**
     * 测试代码生成
     */
    @IgnoreAuth
    @GetMapping("/enjoy")
    public void enjoy() {
        Map<String, Object> map = new HashMap<>();
        map.put("ifTrue", 1);
        map.put("test1", "123456");
        map.put("test2", "789");

        String base64Str = "CQnov5nmmK/kuIDkuKrpgLvovpHmtYvor5XvvJoKCSNpZihpZlRydWUgPT0gMCkKCea1i+ivleaWh+acrDEjKHRlc3QxKTsKCSNlbHNlCgnmtYvor5XmlofmnKwyIyh0ZXN0Mik7CgkjZW5k";
        String text = Base64.decodeStr(base64Str);

        StringWriter sw = new StringWriter();

        //初始化参数
        Kv kv = new Kv();
        kv.set(map);

        //渲染模板
        Engine engine = Engine.use();
        Template template = engine.getTemplateByString(text);
        template.render(kv, sw);
        String result = sw.toString();
        System.out.println(result);
    }

    /**
     * 测试代码生成
     */
    @IgnoreAuth
    @GetMapping("/freemarker")
    public void freemarker() {
        Map<String, Object> map = new HashMap<>();
        map.put("ifTrue", 1);
        map.put("test1", "123456");
        map.put("test2", "789");

        String base64Str = "CQnov5nmmK/kuIDkuKrpgLvovpHmtYvor5XvvJoKCTwjaWYgaWZUcnVlID09IDA+CgnmtYvor5XmlofmnKwxJHt0ZXN0MX07Cgk8I2Vsc2U+CgnmtYvor5XmlofmnKwyJHt0ZXN0Mn07Cgk8LyNpZj4=";
        String text = Base64.decodeStr(base64Str);

        StringWriter sw = new StringWriter();

        try {
            //设置模板
            Configuration configuration = new Configuration(Configuration.VERSION_2_3_32);
            configuration.setTemplateLoader(new StringTemplateLoader());
            configuration.setDefaultEncoding("utf-8");

            //构建
            freemarker.template.Template template = new freemarker.template.Template("You", text, configuration);

            //输出
            template.process(map, sw);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        String result = sw.toString();
        System.out.println(result);
    }

}
