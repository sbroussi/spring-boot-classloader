package com.sbroussi.spring2.demo;

import com.sbroussi.spring2.demo.service.AppService;
import lombok.extern.slf4j.Slf4j;
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

    private void runCommand(final String command) {
        runCommand(command, false);
    }

    private void runCommand(final String command, final boolean waitForProcess) {

        /*
        // -- Linux --
        // processBuilder.command("bash", "-c", "ls /home/mkyong/"); // Run a command
        // processBuilder.command("path/to/hello.sh");               // Run a shell script
        // -- Windows --
        // processBuilder.command("cmd.exe", "/c", "dir C:\\Users\\mkyong"); // Run a command
        // processBuilder.command("C:\\Users\\mkyong\\hello.bat");           // Run a shell script
        */

        log.info("execute command: [" + command + "]");

        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command("bash", "-c", command);

            Process process = processBuilder.start();

            if (waitForProcess) {

                StringBuilder output = new StringBuilder(4 * 1024);
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(process.getInputStream()));

                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }

                int exitVal = process.waitFor();

                log.info("command returned exit code [{}] output is: [{}]", exitVal, output.toString());
            }

        } catch (Exception e) {
            log.error("error while executing command: [" + command + "]", e);
        }

    }

}

