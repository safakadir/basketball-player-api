# Basketball Player Api

A sample task to develop a Spring Boot application with GraphQL as a basketball player registration application.


## Getting Started

### Prerequisites

- Java 17
- Maven 3.0+

### Running from terminal

> $ mvn spring-boot:run -Dspring-boot.run.profiles=dev

### Running from Intellij IDEA

Add **dev** to profiles list inside *BasketballPlayerApiApplication* run configuration.

Run it as *Spring Boot App* while *BasketballPlayerApiApplication* run configuration is selected.


## Authentication

The application uses JWTs as Bearer tokens to authenticate the requests.

- First get a token from the endpoint `POST /token`
  - **username:** "user", **password:** "password" (currently for development)
- Then use the token in other requests' header as: `Authorization: Bearer {obtained_token}`


## Tech Stack

- Spring Boot
- GraphQL
- MySQL
- H2 Database
- Maven


## GraphQL support for Intellij IDEA

If [GraphQL plugin for Intellij IDEA](https://plugins.jetbrains.com/plugin/8097-graphql) is installed,
then GraphQL language support is added to your ide. **".graphqlconfig"** file is used by this plugin.
The plugin is useful when writing tests for GraphQL controller.

Add following comment line before the literal string that should be interpreted as GraphQL:
> // language=GraphQL


## Deployment

### Configuring RSA Key Pairs

RSA keys in `rsa/private.pem` and `rsa/public.pem` files are used by the application for JWT encoding/decoding.

**!!! DO NOT use existing key pair** in the deployment because of the security reasons.
A new secret key pair should be generated and placed under `rsa/`.
Here is an [online RSA Key Generator](https://www.csfieldguide.org.nz/en/interactives/rsa-key-generator/)


## Further Readings

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.0.4/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.0.4/maven-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.0.4/reference/htmlsingle/#web)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/3.0.4/reference/htmlsingle/#data.sql.jpa-and-spring-data)
* [Spring for GraphQL](https://docs.spring.io/spring-boot/docs/3.0.4/reference/html/web.html#web.graphql)
* [Spring Security](https://docs.spring.io/spring-boot/docs/3.0.4/reference/htmlsingle/#web.security)
* [OAuth2 Resource Server](https://docs.spring.io/spring-boot/docs/3.0.4/reference/htmlsingle/#web.security.oauth2.server)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Building a GraphQL service](https://spring.io/guides/gs/graphql-server/)
* [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)
* [Securing a Web Application](https://spring.io/guides/gs/securing-web/)
* [Spring Boot and OAuth2](https://spring.io/guides/tutorials/spring-boot-oauth2/)
