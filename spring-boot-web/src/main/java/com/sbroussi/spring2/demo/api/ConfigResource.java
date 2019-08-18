package com.sbroussi.spring2.demo.api;

import com.sbroussi.spring2.demo.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class ConfigResource {

    private AppService service;

    @Autowired
    public ConfigResource(final AppService service) {
        this.service = service;
    }

    @GetMapping("/config")
    public String getConfig() {
        return service.getConfig();
    }

    @GetMapping("/reload")
    public String reload() {
        service.triggerReload();
        return "reload requested";
    }

    @GetMapping("/loadv1")
    public String loadv1() {
        return service.loadv1();
    }

    @GetMapping("/loadv2")
    public String loadv2() {
        return service.loadv2();
    }

}
