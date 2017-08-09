<img src="http://static.jboss.org/hibernate/images/hibernate_logo_whitebkg_200px.png" />


Hibernate ORM is a component/library providing Object/Relational Mapping (ORM) support
to applications and other components/libraries.  It is also provides an implementation of the
JPA specification, which is the standardized Java specification for ORM.  See 
[Hibernate.org](http://hibernate.org/orm/) for additional information. 

[![Build Status](http://ci.hibernate.org/job/hibernate-orm-master-h2-main/badge/icon)](http://ci.hibernate.org/job/hibernate-orm-master-h2-main/)


Resources
=========

The build requires a Java 8 JDK as JAVA_HOME.

You will need http://git-scm.com/[git] to obtain the http://github.com/hibernate/hibernate-orm/[source].

Hibernate uses [Gradle](http://gradle.org) as its build tool.  See the _Gradle Primer_ section below if you are new to
Gradle.

Contributors should read the [Contributing Guide](CONTRIBUTING.md)

See the guides for setting up [IntelliJ](https://developer.jboss.org/wiki/ContributingToHibernateUsingIntelliJ) or
[Eclipse](https://developer.jboss.org/wiki/ContributingToHibernateUsingEclipse) as your development environment.

Check out the _Getting Started_ section in CONTRIBUTING.md for getting started working on Hibernate source.


CI Builds
=========

Hibernate makes use of [Jenkins](http://jenkins-ci.org) for its CI needs.  The project is built continuous on each 
push to the upstream repository.   Overall there are a few different jobs, all of which can be seen at 
[http://ci.hibernate.org/view/ORM/](http://ci.hibernate.org/view/ORM/)


Gradle primer
=========

This section describes some of the basics developers and contributors new to Gradle might 
need to know to get productive quickly.  The Gradle documentation is very well done; 2 in 
particular that are indispensable:

* [Gradle User Guide](https://docs.gradle.org/current/userguide/userguide_single.html) is a typical user guide in that
it follows a topical approach to describing all of the capabilities of Gradle.
* [Gradle DSL Guide](https://docs.gradle.org/current/dsl/index.html) is quite unique and excellent in quickly
getting up to speed on certain aspects of Gradle.


Using the Gradle Wrapper
------------------------

For contributors who do not otherwise use Gradle and do not want to install it, Gradle offers a very cool
features called the wrapper.  It lets you run Gradle builds without a previously installed Gradle distro in 
a zero-conf manner.  Hibernate configures the Gradle wrapper for you.  If you would rather use the wrapper and 
not install Gradle (or to make sure you use the version of Gradle intended for older builds) you would just use
the command `gradlew` (or `gradlew.bat`) rather than `gradle` (or `gradle.bat`) in the following discussions.  
Note that `gradlew` is only available in the project's root dir, so depending on your `pwd` you may need to adjust 
the path to `gradlew` as well.

Examples use the `gradle` syntax, but just swap `gradlew` (properly relative) for `gradle` if you wish to use 
the wrapper.

_Note that another reason to use `gradlew` is that it uses the exact version of Gradle that the build is 
defined to work with.


Executing Tasks
------------------------

Gradle uses the concept of build tasks (equivalent to Ant targets or Maven phases/goals). You can get a list of
available tasks via 

    gradle tasks

To execute a task across all modules, simply perform that task from the root directory.  Gradle will visit each
sub-project and execute that task if the sub-project defines it.  To execute a task in a specific module you can 
either:

1. `cd` into that module directory and execute the task
2. name the "task path".  For example, in order to run the tests for the _hibernate-core_ module from the root directory you could say `gradle hibernate-core:test`

Common Java related tasks
------------------------

* _build_ - Assembles (jars) and tests this project
* _buildDependents_ - Assembles and tests this project and all projects that depend on it.  So think of running this in hibernate-core, Gradle would assemble and test hibernate-core as well as hibernate-envers (because envers depends on core)
* _classes_ - Compiles the main classes
* _testClasses_ - Compiles the test classes
* _compile_ (Hibernate addition) - Performs all compilation tasks including staging resources from both main and test
* _jar_ - Generates a jar archive with all the compiled classes
* _test_ - Runs the tests
* _publish_ - Think Maven deploy
* _publishToMavenLocal_ - Installs the project jar to your local maven cache (aka ~/.m2/repository).  Note that Gradle 
never uses this, but it can be useful for testing your build with other local Maven-based builds.
* _eclipse_ - Generates an Eclipse project
* _idea_ - Generates an IntelliJ/IDEA project (although the preferred approach is to use IntelliJ's Gradle import).
* _clean_ - Cleans the build directory


Testing and databases
=====================

Testing against a specific database can be achieved in 2 different ways:


Using the "Matrix Testing Plugin" for Gradle.
---------------------------------------------

Coming soon...


Using "profiles"
------------------------

The Hibernate build defines a number of database testing "profiles" in `databases.gradle`.  These
profiles can be activated by name using the `db` build property which can be passed either as
a JVM system prop (`-D`) or as a Gradle project property (`-P`).  Examples below use the Gradle
project property approach.

[source]
----
gradle clean build -Pdb=pgsql
----

To run a test from your IDE, you need to ensure the property expansions happen.
Use the following command:

[source]
----
gradle clean compile -Pdb=pgsql
----

[NOTE]
====
To run the tests from your IDEs for Oracle, DB2 and other non-OSS JDBC drivers, it is a bit different.
You also need to edit `build.gradle` and change the following (e.g for Oracle DB).

[source]
----
// from
if (db.equalsIgnoreCase("oracle")) {
  dependencies {
    testRuntime( libraries.oracle )
  }
}

//to
dependencies {
  testRuntime( libraries.oracle )
}
----

Also remember to add the Oracle driver to your local Maven repository.
Oracle drivers are not on Maven central.
====
