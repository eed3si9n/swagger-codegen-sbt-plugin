package io.swagger.codegen.plugin

import java.io.File

import io.swagger.codegen.config.CodegenConfigurator
import sbt.Keys._
import sbt._
import io.swagger.codegen.{CodegenConfig, ClientOptInput, DefaultGenerator}
import scala.collection.JavaConversions._

/**
  * Created by Jason Martens <jason.martens@3dr.com> on 2/3/16.
  */
object SwaggerCodegen extends AutoPlugin {
  object autoImport {
    val generateSources = taskKey[List[File]]("generate sources")
    val jasonCanUseSbt = settingKey[String]("just to see")
    jasonCanUseSbt := "yes"

  }

  import autoImport._

  val swaggerPackageName = settingKey[String]("The package to which the swagger definition will be compiled")
  val swaggerOutputDir = settingKey[File]("The output directory to write the generated files to")
  swaggerOutputDir := sourceManaged.value / "generated"

  sourceGenerators in Compile += Def.task {
    val file = (sourceManaged in Compile).value / "demo" / "Test.scala"
    IO.write(file, """object Test extends App { println("Hi") }""")
    println("Generating sources")
    Seq(file)
  }.taskValue

//  managedSources ++= sourceGenerators.value.flatMap(_.value)
  managedSourceDirectories <+= swaggerOutputDir
  generateSources <<=
    (sourceGenerators in Compile) { _.join.map(_.flatten.toList) }

  def runSwaggerCodegen(base: File, outputDir: String): Seq[File] = {

    val generator = new DefaultGenerator()
    val configurator = new CodegenConfigurator()
    configurator
      .setInputSpec(base.getParentFile.getParentFile.toPath.resolve("samples/yaml/pet.yml").toAbsolutePath.toString)
      .setLang("java")
      .setLibrary("ok-http")
      .setOutputDir(outputDir)

    generator.opts(configurator.toClientOptInput)
    val files = generator.generate()
    files.foreach(println)
    files
  }
}
