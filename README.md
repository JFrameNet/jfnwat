# JFNWAT #

## What is it ? ##

JFNWAT is the Japanese FrameNet web annotation tool. It is designed as a unified tool to replace the existing applications used at JFN. It is currently under development.
Its ultimate goal is to enable users to annotate sentences extracted from corpora and to display the result of this annotation. The annotation process include different operations : selecting the sentences, creating new frames, adding lexical units to a frame, labelling words in a sentence.
At the moment, only the WebReport module has been finalized. Any database using the standard FrameNet model can be used to display annotated sentences and full texts. The KWIC concordancer is also under development. See section below to contribute to the project.

## How To Install ##

### 1. Requirements ###
* a MySQL database using the FrameNet datamodel
* Java JDK 8
* Apache Maven

### 2. New datamodel ###
JFNWAT uses an improved datamodel based on the FN datamodel, so it is mandatory to create a new MySQL database and use sql scripts to populate this new database with data from the existing database :
  1. Create an empty MySQL database 'jfnwat'
  2. Create a jfnwat user on the MySQL server
  3. Grant 'all permissions' on jfnwat and 'select' on the existing database to the jfnwat user
  4. Download the sql scripts in the project folder
  5. From a terminal, in the directory containing the SQL scripts, run the following commands :
```
mysql -u jfnwat -p jfnwat < jfnwatInit.sql
```
```
mysql -u jfnwat -p jfnwat < migrateScript.sql
```

**Warning** : this operation only populates the new database once. To re-import the data, it is necessary to delete all the existing rows of the jfnwat database (using the deleteScript.sql) and extract the data again with migrateScript.sql.

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
in the `protected void configure(HttpSecurity http)` method : set .antMatchers("/**").permitAll()
( instead of .antMatchers("/resources/**").permitAll()) to disable the login check.

## Contributing ##
Here are the next steps for the development of JFNWAT :

