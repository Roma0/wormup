package com.ascending.controller;

import com.ascending.model.User;
import com.ascending.service.JwtService;
import com.ascending.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/auth")
public class Authentication {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired UserService userService;
    @Autowired JwtService jwtService;

    private String errorMsg = "The email or password is not correct.";
    private String tokenKeyWord = "Authorization";
    private String tokenType = "Bearer";

    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity authenticate(@RequestBody User user) {
        String token = "";

        try {
            logger.debug(user.toString());
            User u = userService.getUserByCredentials(user.getEmail(), user.getPassword());
            if (u == null) return ResponseEntity.status(HttpServletResponse.SC_NON_AUTHORITATIVE_INFORMATION).body(errorMsg);
            logger.debug(u.toString());
            token = jwtService.generateToken(u);
        }
        catch (Exception e) {
            String msg = e.getMessage();
            if (msg == null) msg = "BAD REQUEST!";
            logger.error(msg);
            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(msg);
        }

        return ResponseEntity.status(HttpServletResponse.SC_OK).body(tokenKeyWord + ":" + tokenType + " " + token);
    }

}
