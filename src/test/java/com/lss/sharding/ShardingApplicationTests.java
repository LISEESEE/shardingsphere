package com.lss.sharding;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lss.sharding.entity.Course;
import com.lss.sharding.entity.Udict;
import com.lss.sharding.entity.User;
import com.lss.sharding.mapper.CourseMapper;
import com.lss.sharding.mapper.UdictMapper;
import com.lss.sharding.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class ShardingApplicationTests {
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UdictMapper udictMapper;
    //===========测试公共表
    //添加
    @Test
    void addUdictDb() {
        Udict udict = new Udict();
        udict.setUvalue("2eesd");
        udict.setUstatus("tset");
        udictMapper.insert(udict);
    }
    @Test
    void deleteUdictDb() {
        QueryWrapper<Udict> wrapper = new QueryWrapper<>();
        wrapper.eq("dictid", 481839928083218433L);
        udictMapper.delete(wrapper);
    }
    //================测试垂直分库
    //添加
    @Test
    void addUserDb() {
        User user = new User();
        user.setUsername("张三");
        user.setUstatus("哈哈哈");
        userMapper.insert(user);
    }
    @Test
    void selectUserDb() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", 481831622904119297L);
        User user = userMapper.selectOne(wrapper);
        System.out.println(user);
    }

    //----------------------测试水平分库
    //添加
    @Test
    void addCourseDb() {
        Course course = new Course();
        course.setCname("java");
        course.setUserId(111L);
        course.setCstatus("normal");
        courseMapper.insert(course);
    }
    @Test
    void selectCourseDb() {
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", 100);
        wrapper.eq("cid", 481794584469307393L);
        Course course = courseMapper.selectOne(wrapper);
        System.out.println(course);
    }
    //-----------------测试水平分表
    @Test
    void add() {
        for (int i = 0; i < 10; i++) {
            Course course = new Course();
            course.setCname("w3423");
            course.setUserId(20L);
            course.setCstatus("dsa");
            courseMapper.insert(course);
        }
    }

    @Test
    void select() {
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.eq("cid", 481782811162836992L);
        Course course = courseMapper.selectOne(wrapper);
        System.out.println(course);
    }
}
