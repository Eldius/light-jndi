#!/bin/bash

clear
mvn clean install && \
java -jar target/light-jndi-jar-with-dependencies.jar -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005
