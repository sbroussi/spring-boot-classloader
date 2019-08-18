# spring-boot-classloader
Demo project for Spring Boot 2.1 with dynamic/custom classloaders.

# Compile and run
## With MAVEN
```
cd  ##_root_folder_##
mvn clean install

cd ./spring-boot-web
mvn clean spring-boot:run
```
## In IntelliJ
Menu: Run / Edit Configurations / + to Add / Application
- Class: com.sbroussi.spring2.demo.App
- Working directory: ./spring-boot-web

## URL to test
`http://localhost:8080/index.html`

# TO DO:

- need to improve the LifeCycle of JARs to be loaded at startup
 - persist the list of URLs outside memory before restarting
 - restart
 - read the list of JARS to be loaded and load them
