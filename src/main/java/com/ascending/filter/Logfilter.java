package com.ascending.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;


@WebFilter(filterName = "loggerFilter")
public class Logfilter implements Filter {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private final List<String> excludedWords = Arrays.asList("newPasswd", "confirmPasswd", "passwd", "password");

    private boolean isIgnoredWord(String word, List<String> excludedWords) {
        for (String excludedWord : excludedWords) {
            if (word.toLowerCase().contains(excludedWord)) return true;
        }
        return false;
    }
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");


    private String logInfo(HttpServletRequest req) {
        String formData = null;
        String httpMethod = req.getMethod();
        Date startDateTime = new Date();
        String requestURL = req.getRequestURI();
        String userIP = req.getRemoteHost();
        String sessionID = req.getSession().getId();
        Enumeration<String> parameterNames = req.getParameterNames();
        List<String> parameters = new ArrayList();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            if (isIgnoredWord(paramName, excludedWords)) continue;
            String paramValues = Arrays.asList(req.getParameterValues(paramName)).toString();
            parameters.add(paramName + "=" + paramValues);
        }
        if (!parameters.isEmpty()) {
            logger.info(String.format("Parameters no exludedWords: %s", parameters.toString()));
            formData = parameters.toString().replaceAll("^.|.$", "");
        }
        return  new StringBuilder("| ")
                .append(formatter.format(startDateTime)).append(" | ")
                .append(userIP).append(" | ")
                .append(httpMethod).append(" | ")
                .append(requestURL).append(" | ")
                .append(sessionID).append(" | ")
                .append("responseTime ms").append(" | ")
                .append(formData).toString();
    }


//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//
//    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterchain) throws IOException, ServletException {
        long startTime = System.currentTimeMillis();
        HttpServletRequest req = (HttpServletRequest)servletRequest;
        logger.info(String.valueOf(System.currentTimeMillis()));
        String logInfo = logInfo(req);
        logger.info(logInfo.replace("responseTime", String.valueOf(System.currentTimeMillis() - startTime)));
        filterchain.doFilter(servletRequest, servletResponse);
    }

//    @Override
//    public void destroy() {
//
//    }
}
