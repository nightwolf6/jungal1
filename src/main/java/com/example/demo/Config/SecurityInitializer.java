package com.example.demo.Config;

import com.example.demo.DemoApplication;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class SecurityInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] { DemoApplication.class, ConfigJungal.class };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }
}
