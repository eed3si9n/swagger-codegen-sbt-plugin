lazy val root = (project in file("."))
    .enablePlugins(SwaggerCodegen)

name := "swagger-codegen-sbt-plugin-test"

version := "1.0"

scalaVersion := "2.11.7"

resolvers += Resolver.mavenLocal
