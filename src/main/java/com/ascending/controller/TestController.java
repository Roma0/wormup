package com.ascending.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sun.jvm.hotspot.oops.ObjectHeap;

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
}