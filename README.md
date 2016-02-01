# README #

# How to run the application #
* Create a local database and import the mysqldump file (src/main/resources/mysqldb.sql).
Datasource configuration is defined in src/main/resources/application.properties, enter username and password for the database user and change the port if necessary.
* Run from IntelliJ : the main class is src/main/java/jp/keio/jfn/wat/JFNWAT
* Run from command line, using Spring Boot maven plugin : go to the project directory and execute
> mvn spring-boot:run