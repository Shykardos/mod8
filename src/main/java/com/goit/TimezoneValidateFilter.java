package com.goit;

import java.io.IOException;
import java.util.TimeZone;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter("/time")
public class TimezoneValidateFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String timezoneParam = httpRequest.getParameter("timezone");

        if (timezoneParam != null && !timezoneParam.isEmpty()) {
            if (isValidTimezone(timezoneParam)) {
                chain.doFilter(request, response);
            } else {
                httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid timezone");
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    private boolean isValidTimezone(String timezone) {
        String[] availableTimezones = TimeZone.getAvailableIDs();
        for (String availableTimezone : availableTimezones) {
            if (availableTimezone.equals(timezone)) {
                return true;
            }
        }
        return false;
    }
}