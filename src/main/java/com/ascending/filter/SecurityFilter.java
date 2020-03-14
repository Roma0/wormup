package com.ascending.filter;

import com.ascending.model.User;
import com.ascending.repository.UserDao;
import com.ascending.service.JwtService;
import com.ascending.service.UserService;
import com.github.fluent.hibernate.H;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebFilter(filterName = "SecurityFilter")
public class SecurityFilter implements Filter {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private String AUTH_URI = "/auth/signIn";
//    private static String[] IGNOREURL = {"/auth/*", "/uer"};

    @Autowired private JwtService jwtService;
    @Autowired private UserDao userDao;

    private int authorization(HttpServletRequest req){
        int statusCode = HttpServletResponse.SC_UNAUTHORIZED;
        String uri = req.getRequestURI();
        String verb = req.getMethod();
        if(uri.equalsIgnoreCase(AUTH_URI))return HttpServletResponse.SC_ACCEPTED;

        try {
            Map<String, Object> token = new HashMap<>();
           token.put("token", req.getHeader("Authorization").replaceAll("^(.*?) ", ""));
            if(token == null || token.isEmpty())return statusCode;

            Claims claims = jwtService.decryptJwtToken(token);
//            if(claims.getId() != null){
//                User u = userDao.findById(Long.valueOf(claims.getId()));
//                if(u != null)statusCode = HttpServletResponse.SC_ACCEPTED;
//            }

            String allowedResources = "/";
            switch(verb) {
                case "GET"    : allowedResources = (String)claims.get("allowedReadResources");   break;
                case "POST"   : allowedResources = (String)claims.get("allowedCreateResources"); break;
                case "PUT"    : allowedResources = (String)claims.get("allowedUpdateResources"); break;
                case "DELETE" : allowedResources = (String)claims.get("allowedDeleteResources"); break;
            }
            for (String s : allowedResources.split(",")) {
                if (uri.trim().toLowerCase().startsWith(s.trim().toLowerCase())) {
                    statusCode = HttpServletResponse.SC_ACCEPTED;
                    break;
                }
            }
            logger.debug(String.format("Verb: %s, allowed resources: %s", verb, allowedResources));
        }
        catch (Exception e) {
            logger.error("can't verify the token",e);
        }

        return statusCode;
    }


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;

        int statusCode = authorization(req);
        if(statusCode == HttpServletResponse.SC_ACCEPTED) chain.doFilter(request, response);
        else ((HttpServletResponse)response).sendError(statusCode);
    }

    @Override
    public void destroy() {

    }
}
