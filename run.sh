#!/bin/bash

export DATABASE_URL="postgres://user:pass@anyhost.amazonaws.com:999/database"

clear

mvn clean install && \
echo First test && \
java -jar light-jndi-customfactory-test/target/light-jndi-customfactory-test-jar-with-dependencies.jar
echo Second test && \
java -jar light-jndi-embedded-test/target/light-jndi-embedded-test-jar-with-dependencies.jar && \
echo Thirsd test && \
java -jar light-jndi-test/target/light-jndi-test-jar-with-dependencies.jar && \
echo Finished with success.
