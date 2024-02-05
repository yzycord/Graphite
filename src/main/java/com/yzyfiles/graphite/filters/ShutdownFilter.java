package com.yzyfiles.graphite.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class ShutdownFilter implements Filter {

    private final AtomicBoolean shutdownFlag;

    @Autowired
    public ShutdownFilter(AtomicBoolean shutdownFlag) {
        this.shutdownFlag = shutdownFlag;
    }

    // Code from ChatGPT be careful
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        if (shutdownFlag.get()) {
            // You can customize the response or log a message here
            response.getWriter().write("The application is shutting down. Please try again later.");
            response.flushBuffer();
        } else {
            chain.doFilter(request, response);
        }
    }
}
