# Leadiro Starter Project 🚀

This is a blank starter project that should be used as the foundation for new REST microservices.

It provides:

- A Java 13 base - var, interface enhancements, lambda enhancements
- An Auth0 JWT based authentication starter
- Lombok, Groovy, Apache commons, Guava
- Base Spring Boot configuration files 
- Mybatis and an associated data source in the spring config files

### Patterns and Practices

When adding new functionality code should follow standard industry patterns:

- The `Layer` pattern should be used to separate code into its associated concerns.
- The four main layers defined in this starter project are `Controller`, `Service`, `Data Access Object` and `Data Transfer Object`.
- Functions in a similar domain should be contained in a high level Service interface that defines the "what" - see `StarterService.java`.
- A Concrete implementation of the interface should be defined in a package below the interface - see `ConcreteStarterService.java`.
- The concrete implementation should delegate to other objects in a clean manner.
- Database or other data access should be contained in a DAO object - see `StarterMapper.java`.
- Data should be passed around in simple POJO DTO's - see `Starter.java`.
- Controllers should inject the Service interface and expose it as REST.

### Frameworks

- `SpringBoot` is used to bring the majority of the scaffolding.
- `MyBatis` is used as the database mapping layer - see `StarterMapper.java`.
- `Lombok` is used to simplify DTO implementation - see `Starter.java`.

### Groovy

Groovy 3 is available in the project setup for cross-compilation in Maven. In general Groovy should be used in only three scenarios:

- All Tests should be written in Groovy as it allows much simpler instantiation of lists and maps etc, as well as passing constructor args.
- All Controllers should be written in Groovy as they are very simple and it makes JSON manipulation easier.
- All MyBatis DAO's should be written in Groovy as it allows the annotations to be split across multiple lines with the triple quote syntax.

All Groovy classes should be marked with `@CompileStatic` to enforce static type checking.

### Authentication

A relatively complete JWT based web security filter is provided with `JwtBearerTokenAuthenticationFilter.java`. 
The filter relies on a `JwtDecoder`, and if using Auth0 one is supplied in `Auth0JwtDecoder`.

### Docker

A very simple `Dockerfile` and `build.sh` script are provided in the root of the project.