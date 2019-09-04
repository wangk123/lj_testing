package com.lj.testing.server.business.user;

import com.lj.testing.server.business.BusinessWebApi;
import io.restassured.response.Response;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class Login extends BusinessWebApi {

    public Response login(String account, String password) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("account", account);
        map.put("password", password);
        HashMap<String, HashMap<String, Object>> body = new HashMap<>();
        body.put("_body", map);
        return getResponseFromYaml("/api/login.yaml", body);
    }

}
