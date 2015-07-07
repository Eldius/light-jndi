#!/bin/bash

clear
mvn clean install && \
java -jar light-jndi-embedded-test/target/light-jndi-embedded-test-jar-with-dependencies.jar && \
java -jar light-jndi-test/target/light-jndi-test-jar-with-dependencies.jar
