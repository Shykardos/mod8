package com.goit;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/time")
public class TimeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String timezoneParam = request.getParameter("timezone");

        ZoneId zoneId;
        if (timezoneParam != null && !timezoneParam.isEmpty()) {
            try {
                zoneId = ZoneId.of(timezoneParam);
            } catch (Exception e) {
                // Invalid timezone provided, fallback to UTC
                zoneId = ZoneId.of("UTC");
            }
        } else {
            zoneId = ZoneId.of("UTC");
        }

        ZonedDateTime currentTime = ZonedDateTime.now(zoneId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
        String formattedTime = currentTime.format(formatter);

        out.println("<html><body>");
        out.println("<h1>Current Time</h1>");
        out.println("<p>" + formattedTime + "</p>");
        out.println("<p>Timezone: " + currentTime.getZone() + "</p>");
        out.println("</body></html>");
    }
}