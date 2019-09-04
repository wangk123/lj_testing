package com.lj.testing;

import com.lj.testing.dao.mapper.basealtic.LjUsersMapper;
import com.lj.testing.dao.pojo.basealtic.LjUsers;
import com.lj.testing.server.TestingApplication;
import com.lj.testing.server.business.BusinessWebApi;
import com.lj.testing.server.business.user.Login;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(classes = TestingApplication.class)
public class TestingApplicationTests extends AbstractTestNGSpringContextTests {

    @Autowired
    private Login login;

    @Autowired
    private LjUsersMapper ljUsersMapper;

    @Test
    public void test() {
        LjUsers ljUsers = ljUsersMapper.selectByPrimaryKey(61909767);
        Assert.assertEquals(ljUsers.getPhone(), "15267109088");
    }

    @Test(dataProvider = "登录", dataProviderClass = BusinessWebApi.class)
    public void 登录(String id, String caseDesc, Map<String, Object> caseData) {
        Response loginResult = login.login(caseData.get("account").toString(), caseData.get("password").toString()).then().statusCode(200).extract().response();
        JsonPath jsonPath = loginResult.body().jsonPath();
        assertThat(jsonPath.get("data.name"), equalTo("等待"));
        assertThat(jsonPath.get("data.phone"), equalTo("15267109086"));
        assertThat(jsonPath.get("data.role"), equalTo("business"));
    }

}
