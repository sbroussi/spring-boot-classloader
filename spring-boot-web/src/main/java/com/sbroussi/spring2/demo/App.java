package com.sbroussi.spring2.demo;

import com.sbroussi.spring2.demo.service.AppService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@SpringBootApplication
@Slf4j
public class App {

    @Autowired
    private AppService service;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        log.info("hello world, I have just started up");


        runCommand("rm /tmp/dummyfile.txt", true);
        /*
        runCommand("echo \"hello1\" >> /tmp/dummyfile.txt &");
        runCommand("echo \"hello2\" >> /tmp/dummyfile.txt &");
        runCommand("echo \"hello3\" >> /tmp/dummyfile.txt &");
        runCommand("echo \"hello4\" >> /tmp/dummyfile.txt; ls /tmp >> /tmp/dummyfile.txt; cat /tmp/dummyfile.txt", true);
        */

        service.dumpBeans();
    }

    public static int runCommand(final String command) {
        return runCommand(command, false);
    }

    /**
     * @param command        The shell command line to execute
     * @param waitForProcess TRUE to wait for the end of the shell command, log the output and return the exit code
     * @return the exit value of the process (or '0' if no wait)
     */
    public static int runCommand(final String command, final boolean waitForProcess) {

        log.info("execute [" + SystemUtils.OS_NAME + "] command: [" + command + "]");

        int exitCode = 0;
        try {
            ProcessBuilder processBuilder = new ProcessBuilder();

            if (SystemUtils.IS_OS_LINUX) {
                processBuilder.command("bash", "-c", command);
            } else if (SystemUtils.IS_OS_WINDOWS) {
                processBuilder.command("cmd.exe", "/c", command);
            } else {
                throw new IllegalStateException("OS is not supported: " + SystemUtils.OS_NAME);
            }

            Process process = processBuilder.start();

            if (waitForProcess) {

                StringBuilder output = new StringBuilder(4 * 1024);
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }

                exitCode = process.waitFor();

                if (exitCode == 0) {
                    log.info("SUCCESS: command returned exit code [{}] output is: [{}]", exitCode, output.toString());
                } else {
                    log.error("ERROR: command returned exit code [{}] output is: [{}]", exitCode, output.toString());
                }
            }

        } catch (Exception e) {
            log.error("error while executing command: [" + command
                    + "] on OS [" + SystemUtils.OS_NAME + "]", e);
        }
        return exitCode;

    }

}

