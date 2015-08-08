# light-jndi #

[![Build Status](https://travis-ci.org/Eldius/light-jndi.svg?branch=java_8)](https://travis-ci.org/Eldius/light-jndi)

An alternate to use JNDI without a container.

The idea was to use Hibernate with a datasource runnung in a Spark REST application.

### Configuration ###

There are 3 example projects and each one shows a diferent  way to configure.

* light-jndi-test
-- A simple configuration pointing the root folder outside the jar file.

* light-jndi-embedded-test
-- A simple configuration pointing the root folder inside the jar file.

* light-jndi-customfactory-test
-- A more complex example defining a custom ObjectFactory (a class to create one type of object).

### Configuration fields (jndi.properties) ###
* net.eldiosantos.jndi.root: The root folder where to find the properties files with the context configuration.
* java.naming.factory.initial: The conetxt factory used by the JVM (this property is used by the JVM to define what class will be responsible for build the JNDI context).
  The value of this property is 'net.eldiosantos.tools.context.CustomContextFactory'.
* net.eldiosantos.jndi.factories: Comma separated names of the custom ObjectFactorys you need. It's an optional property.

### JNDI object configuration ###
  You will need to add a file inside the root folder to define the objects and their 'names'. For example:

        jdbc:/java/comp/env/MyDatasource.class=javax.sql.Datasource
        jdbc:/java/comp/env/MyDatasource.driver=org.h2.Driver
        jdbc:/java/comp/env/MyDatasource.url=jdbc:h2:./target/database.db
        jdbc:/java/comp/env/MyDatasource.user=database_user
        jdbc:/java/comp/env/MyDatasource.password=database_password

  Here you will have a datasource located at 'jdbc:/java/comp/env/MyDatasource'.

You can add any object in the context. You just need to define the name and the object attributes.
If you want to add an instance of a custom object (net.eldiosantos.exemple.Developer) at 'java:/context/myObject' with attributes
 'name' and 'wantABeer' as 'Eldius' and 'Y' you will need to define this way.

        java:/context/myObject.class=net.eldiosantos.exemple.Developer
        java:/context/myObject.name=Eldius
        java:/context/myObject.wantABeer=Y


Add as a Maven dependency:

        	<dependency>
                <groupId>net.eldiosantos.jndi</groupId>
                <artifactId>light-jndi-project</artifactId>
                <version>0.0.2</version>
        	</dependency>

### TODO list ###
  Try to lower the memmory usage on the startup.

