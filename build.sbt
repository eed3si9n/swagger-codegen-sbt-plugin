
name := "swagger-codegen-sbt-plugin"

organization := "io.swagger.codegen.plugin"

version := "2.1.6-SNAPSHOT"

scalaVersion := "2.10.4"

sbtPlugin := true

resolvers += Resolver.mavenLocal

libraryDependencies += "io.swagger" % "swagger-codegen" % "2.1.6-SNAPSHOT"