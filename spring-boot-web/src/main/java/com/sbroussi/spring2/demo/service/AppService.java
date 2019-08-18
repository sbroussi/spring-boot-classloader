package com.sbroussi.spring2.demo.service;

import com.sbroussi.spring2.demo.config.JmsProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.devtools.restart.Restarter;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.time.LocalDateTime;
import java.util.Arrays;

@Service
@Slf4j
public class AppService {


    @Autowired
    @Qualifier("jms1")
    private JmsProperties jmsProperties1;

    @Autowired
    @Qualifier("jms2")
    private JmsProperties jmsProperties2;


    public void dumpBeans() {
        log.info("dumpBeans()");
        log.info("AppService; bean jms1: " + jmsProperties1);
        log.info("AppService; bean jms2: " + jmsProperties2);
    }

    public String loadv1() {
        // trigger reload
        triggerReload();

        // load and test VERSION 1
        loadJar("file:///home/stephane/.m2/repository/com/sbroussi/springboot/classloader/module/1/module-1.jar");
        return getConfig();
    }

    public String loadv2() {
        // trigger reload
        triggerReload();

        // load and test VERSION 2
        loadJar("file:///home/stephane/.m2/repository/com/sbroussi/springboot/classloader/module/2/module-2.jar");
        return getConfig();
    }

    public String getConfig() {
        return "current version of the bean is: " + callMethod("com.sbroussi.module.Bean", "getName")
                + " at " + LocalDateTime.now().toString();
    }

    public void triggerReload() {
        log.info("triggerReload");

        try {

            // call 'spring-boot-devtools' to reload all classes
            Restarter.getInstance().restart();

        } catch (Throwable t) { // NOSONAR
            String msg = "unable to triggerReload";
            log.error(msg, t);
            throw new RuntimeException(msg, t);
        }
    }

    private String callMethod(final String className, final String methodName) {
        try {
            Class classToLoad = Class.forName(className);
            Method method = classToLoad.getDeclaredMethod(methodName);
            Object instance = classToLoad.getDeclaredConstructor().newInstance();
            String result = (String) method.invoke(instance);

            log.info("loaded class {}: result of method '{}()' is: {}", classToLoad, methodName, result);
            return result;

        } catch (Throwable t) { // NOSONAR
            String msg = "unable to call method [" + methodName + "] of class [" + className + "]";
            log.error(msg, t);
            throw new RuntimeException(msg, t);
        }
    }

    private void loadJar(final String url) {
        log.info("load JAR: {}", url);

        try {
            URLClassLoader classLoader = (URLClassLoader) this.getClass().getClassLoader();

            log.info("classLoader URLs: " + Arrays.asList(classLoader.getURLs()));

            Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
            method.setAccessible(true);
            method.invoke(classLoader, new URL(url));

        } catch (Throwable t) { // NOSONAR
            String msg = "unable to load JAR [" + url + "]";
            log.error(msg, t);
            throw new RuntimeException(msg, t);
        }
    }
}
