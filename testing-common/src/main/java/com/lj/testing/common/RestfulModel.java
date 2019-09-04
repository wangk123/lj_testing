package com.lj.testing.common;

import lombok.Data;

import java.util.HashMap;

@Data
public class RestfulModel {
    public String url;
    public String method;
    public HashMap<String, String> headers;
    public HashMap<String, String> query = new HashMap<>();
    public HashMap<String, Object> body = new HashMap<>();
}
