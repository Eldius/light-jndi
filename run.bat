@echo off
cls
set "JAVA_HOME=%JAVA_HOME_JDK8%"
set "PATH=%JAVA_HOME%\bin;%PATH%"
call mvn clean install

echo "Starting first test"
Rem call java -agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=5005 -jar light-jndi-embedded-test/target/light-jndi-embedded-test-jar-with-dependencies.jar
call java -jar light-jndi-embedded-test/target/light-jndi-embedded-test-jar-with-dependencies.jar

echo "Starting second test"
Rem call java -agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=5005 -jar light-jndi-test/target/light-jndi-test-jar-with-dependencies.jar
call java -jar light-jndi-test/target/light-jndi-test-jar-with-dependencies.jar
