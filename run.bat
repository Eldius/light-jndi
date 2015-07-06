@echo off
cls
set "JAVA_HOME=%JAVA_HOME_JDK8%"
set "PATH=%JAVA_HOME%\bin;%PATH%"
call mvn clean install
call java -jar target/light-jndi-jar-with-dependencies.jar -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005
