package com.lj.testing.server;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvFactory;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.lj.testing.common.RestfulModel;
import com.lj.testing.server.config.ToLoggerPrintStream;
import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import io.restassured.http.ContentType;
import io.restassured.internal.RequestSpecificationImpl;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseApi {


    public BaseApi() {
        ToLoggerPrintStream loggerPrintStream = new ToLoggerPrintStream();
        RestAssured.config = RestAssured.config().logConfig(new LogConfig(loggerPrintStream.getPrintStream(), true));
        RestAssured.useRelaxedHTTPSValidation();
    }

    public abstract RequestSpecification getDefaultRequestSpecification();

    public Response getResponseFromYaml(String path, HashMap<String, HashMap<String, Object>> map) {
        RestfulModel restfulModel = getApiFromYaml(path);
        restfulModel = updateApiFromMap(restfulModel, map);

        RequestSpecification requestSpecification = getDefaultRequestSpecification();

        if (restfulModel.query != null) {
            restfulModel.query.forEach(requestSpecification::queryParam);
        }

        if (restfulModel.headers != null) {
            restfulModel.headers.forEach((key, value) -> requestSpecification.header(key, value));
        }

        if (restfulModel.body != null) {
            String contentType = ((RequestSpecificationImpl) requestSpecification).getContentType();
            //校验Content-Type是否为application/x-www-form-urlencoded
            if (contentType.equalsIgnoreCase(ContentType.URLENC.toString())) {
                requestSpecification.formParams(restfulModel.body);
            } else {
                requestSpecification.body(restfulModel.body);
            }
        }

        return requestSpecification
                .when()
                .request(restfulModel.method, restfulModel.url)
                .then()
                .log().all()
                .extract()
                .response();

    }

    public Object[][] getRequestData(String path) {
        List<Map<String, Object>> csvData = getDataFromCsv(path);
        Object[][] testData = null;
        if (csvData.size() > 0) {
            testData = new Object[csvData.size()][];
            for (int i = 0; i < csvData.size(); i++) {
                testData[i] = new Object[]{
                        csvData.get(i).get("id"),
                        csvData.get(i).get("caseDesc"),
                        csvData.get(i)
                };
            }
        }
        return testData;
    }

    public RestfulModel getApiFromYaml(String path) {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            return mapper.readValue(RestfulModel.class.getResourceAsStream(path), RestfulModel.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Map<String, Object>> getDataFromCsv(String path) {
        ObjectMapper mapper = new ObjectMapper(new CsvFactory());
        CsvSchema schema = CsvSchema.emptySchema().withHeader();
        MappingIterator<Map> values = null;
        try {
            values = mapper.readerFor(Map.class).with(schema).readValues(Map.class.getResourceAsStream(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Map<String, Object>> result = new ArrayList<>();
        values.forEachRemaining(result::add);
        return result;
    }

    public RestfulModel updateApiFromMap(RestfulModel restfulModel, HashMap<String, HashMap<String, Object>> map) {
        if (map == null) {
            return restfulModel;
        }

        if (restfulModel.method.equalsIgnoreCase("GET") || restfulModel.method.equalsIgnoreCase("DELETE")) {
            map.forEach((key, value) -> restfulModel.query.replace(key, String.valueOf(value)));
        }

        if (restfulModel.method.equalsIgnoreCase("POST") || restfulModel.method.equalsIgnoreCase("PUT")) {
            if (map.containsKey("_body")) {
                restfulModel.body.forEach((key, value) -> {
                    if (map.get("_body").containsKey(key)) {
                        restfulModel.body.replace(key, map.get("_body").get(key));
                    }
                });
            }

            /*if (map.containsKey("_file")) {
                String filePath = map.get("_file").toString();
                map.remove("_file");
                restfulModel.body = template(filePath, map.get("_file"));
            }*/
        }
        return restfulModel;
    }

    public static String template(String path, HashMap<String, Object> map) {
        DocumentContext documentContext = JsonPath.parse(BaseApi.class.getResourceAsStream(path));
        map.forEach((key, value) -> documentContext.set(key, value));
        return documentContext.jsonString();
    }

}
