package com.ascending.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping(value = {"/test"})
public class TestController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/path/{pathValue1}/{pathValue2}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public String getPath( @PathVariable(name = "pathValue1") String p1, @PathVariable(name = "pathValue2") String p2) {
        logger.info(String.format(">>>>>>>>>> Path value: %s, %s", p1, p2));
        return p1 + ", " + p2;
    }

    //GET /test
    /*
    GET /test
     */

    /**
     * GET /test
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Map<String, Object> getSampleJson(){
        Map<String, Object> m = new HashMap<>();
        m.put("id", 1);
        m.put("name", "HR");
        m.put("capacity", 500);
        return m;
    }

    /**
     * POST /test/example
     * @return
     */
    @RequestMapping(value = {"/example"}, method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Map<String, Object> createObject(){
        Map<String, Object> m = new HashMap<>();
        Random r = new Random();
        m.put("id", r.nextInt());
        m.put("name", "HR");
        m.put("capacity", 500);
        logger.debug("create an object with id:" + m.get("id"));
        return m;
    }

    /**
     * GET /test/all-path/{pathValue1}/{pathValue2}
     * @param allPathValues
     * @return
     */
    @RequestMapping(value = "/all-path/{pathValue1}/{pathValue2}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Map<String, String> getPath(@PathVariable Map<String, String> allPathValues) {
        logger.info(String.format(">>>>>>>>>> Path value: %s", allPathValues.entrySet()));
        return allPathValues;
    }

    /**
     * GET /test/param?param=String
     * @param param
     * @return
     */
    @RequestMapping(value = "/param", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public String getParam(@RequestParam(name = "param") String param) {
        logger.info(String.format(">>>>>>>>>> Param: %s", param));
        return param;
    }

    /**
     * GET /teat/all-param?param1=value1&param2=value2
     * @param allParams
     * @return
     */
    @RequestMapping(value = "/all-param", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Map<String, String> getAllParam(@RequestParam Map<String, String> allParams) {
        logger.info(String.format(">>>>>>>>>> Param: %s", allParams.entrySet()));
        return allParams;
    }

    /**
     * GET /test/header
     * @param token
     * @return
     */
    @RequestMapping(value = "/header", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public String getHeader(@RequestHeader(name = "token") String token) {
        logger.info(String.format(">>>>>>>>>> Token: %s", token));
        return token;
    }

    /**
     * GET /test/all-header
     * @param headers
     * @return
     */
    @RequestMapping(value = "/all-header", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Map<String, String> getAllHeader(@RequestHeader Map<String, String> headers) {
        logger.info(String.format(">>>>>>>>>> Token: %s", headers.entrySet()));
        return headers;
    }

    /**
     * GET /test/body
     * @param body
     * @return
     */
    @RequestMapping(value = "/body", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public String getBody(@RequestBody String body) {
        logger.info(String.format(">>>>>>>>>> Body: %s", body));
        return body;
    }
}
