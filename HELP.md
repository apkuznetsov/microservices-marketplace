# Read Me First

The following was discovered as part of building this project:

* No Docker Compose services found. As of now, the application won't start! Please add at least one
  service to the `compose.yaml` file.

# Getting Started

### Reference Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.1.0/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.1.0/maven-plugin/reference/html/#build-image)
* [GraalVM Native Image Support](https://docs.spring.io/spring-boot/docs/3.1.0/reference/html/native-image.html#native-image)
* [Docker Compose Support](https://docs.spring.io/spring-boot/docs/3.1.0/reference/htmlsingle/#features.docker-compose)

### Additional Links

These additional references should also help you:

* [Configure AOT settings in Build Plugin](https://docs.spring.io/spring-boot/docs/3.1.0/maven-plugin/reference/htmlsingle/#aot)

### Docker Compose support

This project contains a Docker Compose file named `compose.yaml`.

However, no services were found. As of now, the application won't start!

Please make sure to add at least one service in the `compose.yaml` file.

## GraalVM Native Support

This project has been configured to let you generate either a lightweight container or a native
executable.
It is also possible to run your tests in a native image.

### Lightweight Container with Cloud Native Buildpacks

If you're already familiar with Spring Boot container images support, this is the easiest way to get
started.
Docker should be installed and configured on your machine prior to creating the image.

To create the image, run the following goal:

```
$ ./mvnw spring-boot:build-image -Pnative
```

Then, you can run the app like any other container:

```
$ docker run --rm marketplace-microservices:0.0.1-SNAPSHOT
```

### Executable with Native Build Tools

Use this option if you want to explore more options such as running your tests in a native image.
The GraalVM `native-image` compiler should be installed and configured on your machine.

NOTE: GraalVM 22.3+ is required.

To create the executable, run the following goal:

```
$ ./mvnw native:compile -Pnative
```

Then, you can run the app as follows:

```
$ target/marketplace-microservices
```

You can also run your existing tests suite in a native image.
This is an efficient way to validate the compatibility of your application.

To run your existing tests in a native image, run the following goal:

```
$ ./mvnw test -PnativeTest
```

