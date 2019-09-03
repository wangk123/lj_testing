package com.lj.testing;

import com.lj.testing.dao.mapper.basealtic.LjUsersMapper;
import com.lj.testing.dao.pojo.basealtic.LjUsers;
import com.lj.testing.server.TestingApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

@SpringBootTest(classes = TestingApplication.class)
public class TestingApplicationTests extends AbstractTestNGSpringContextTests {

    @Autowired
    private LjUsersMapper ljUsersMapper;

    @Test
    public void test() {
        LjUsers ljUsers = ljUsersMapper.selectByPrimaryKey(61909767);
        Assert.assertEquals(ljUsers.getPhone(), "15267109088");
    }

}
