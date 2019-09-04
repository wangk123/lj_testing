package com.lj.testing.server.business;

import com.lj.testing.server.BaseApi;
import com.lj.testing.server.config.EnvConf;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.DataProvider;

import static io.restassured.RestAssured.given;

public class BusinessWebApi extends BaseApi {

    public RequestSpecification getDefaultRequestSpecification() {
        //todo rest-assured 打印日志到文件
        RequestSpecification requestSpecification = given().baseUri(EnvConf.getInstance().getValue("business.web.url")).log().all();
        requestSpecification.contentType(ContentType.URLENC);
        return requestSpecification;
    }

    @DataProvider
    public Object[][] 登录() {
        return getRequestData("/data/login.csv");
    }

}
