# JFNWAT #

## What is it ? ##

JFNWAT is the Japanese FrameNet web annotation tool. It is designed as a unified tool to replace the existing applications used at JFN. JFNWAT is organised in modules with specific goals: an embedded KWIC concordancer, the main module for frame definition and annotation, and the WebReport to display the resulting data. The ultimate purpose of the application is to perform these steps in a workflow with a user-friendly web interface.
At the moment, only the WebReport module has been finalized. Any database using the standard FrameNet model can be used to display annotated sentences and full text annotations. The KWIC concordancer development is also well advanced. See section below for how to contribute to the project.

## How To Install ##

### 1. Requirements ###
* a MySQL database using the FrameNet datamodel
* Java JDK 8
* Apache Maven (https://maven.apache.org/)

### 2. New datamodel ###
JFNWAT uses an improved datamodel based on the FN datamodel, so it is mandatory to create a new MySQL database and use SQL scripts to populate this new database with data from the existing database :
  1. Create an empty MySQL database 'jfnwat'
  2. Create a jfnwat user on the MySQL server (ex: 'jfnwat'@'localhost')
  3. Grant 'all permissions' on jfnwat db and 'select' permission on the existing database to the jfnwat user
  4. Download the SQL scripts in the project folder `dbScripts`
  5. From a terminal, in the directory containing the SQL scripts, run the following commands :
```
mysql -u jfnwat -p jfnwat < jfnwatInit.sql
```
```
mysql -u jfnwat -p jfnwat < migrateScript.sql
```

**Warning** : this operation only populates the new database once. To re-import the data, it is necessary to delete all the existing rows of the jfnwat database (using the deleteScript.sql) and extract the data again with migrateScript.sql. The jfnwatInit.sql file should be used only once, after creating the jfnwat database.

### 3. Running JFNWAT ###
  1. Download the project source code
  2. Edit the *src/main/resources/application.properties* file with the correct password for the MySQL jfnwat user. You can also modify the port and the context path of the application in this file.
  3. From a terminal, in the project directory, run the command :
```
mvn spring-boot:run
```

The application will be available from a web browser at http://<IP address>:<server port>/<context-path>/index.jsf
With the standard settings, when running the application from the localhost, the URL will be http://localhost:8888/web-report/index.jsf

**Warning** : there are currently issues when using a context path along with the Spring Security framework. You can use Apache web server as a reverse proxy as a workaround, or simply not set a context path in the application.properties file. The access URL will then be http://localhost:8888/index.jsf

### 4. Authentication ###
Credentials to access the application are stored in the new database in the `Principals` table, under the `User` and `Password` column. The password stored is the encoded version using MD5 algorithm of the user real password.

Authentication can be disabled by modifying the *src/main/java/jp/keio/jfn/wat/SecurityConfig* file,
in the `protected void configure(HttpSecurity http)` method : set `.antMatchers("/**").permitAll()`
( instead of `.antMatchers("/resources/**").permitAll()`) to disable the login check.

## Contributing ##
Here are the next steps for the development of JFNWAT :

#### WebReport ####
* Improve the format of the txt file when downloading selected sentences.

#### KWIC ####

#### Annotation Module ####
The main module will ultimately replace the current JFNDesktop to perform Frame Semantics annotation. The following features need to be implemented:
* Create new frames
  * Frame name and description
  * List of Frame Elements and their definition
  * Adding frame relations
* Add lexical units to a frame
* Annotate sentences for a target Lexical Unit
  * Label frame elements in the sentence
  * Add phrase type and grammatical function

#### General ####
* Fix issue when using a context path without a reverse proxy (caused by SpringSecurity).
* Implement role management for JFNWAT users (*read*, *vanguard*, *admin*)

