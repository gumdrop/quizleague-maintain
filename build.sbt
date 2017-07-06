name := "Quiz League"

EclipseKeys.skipParents in ThisBuild := false
EclipseKeys.withSource := true

val circeVersion = "0.7.0"
val appengineVersion = "1.9.53"
addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)

lazy val commonSettings = Seq(
  organization := "quizleague",
  version := "0.0.1",
  scalaVersion := "2.11.8",
  scalacOptions ++= Seq("-deprecation","-unchecked","-feature","-Xlint"),
  resolvers += Resolver.sonatypeRepo("snapshots")
  
)

lazy val root = project.in(file(".")).
  aggregate(web, server).settings(commonSettings: _*).
  settings(
    publish := {},
    publishLocal := {},
    resolvers += Resolver.sonatypeRepo("releases")
  )

lazy val quizleague = crossProject.in(file(".")).
  settings(commonSettings: _*).
  settings( 
    name := "quizleague",
    libraryDependencies ++= Seq(
	  "io.circe" %%% "circe-core",
	  "io.circe" %%% "circe-generic",
	  "io.circe" %%% "circe-parser"
	).map(_ % circeVersion),
	libraryDependencies += "io.github.cquiroz" %%% "scala-java-time" % "2.0.0-M8",
	libraryDependencies += "org.scalatest" %%% "scalatest" % "3.0.1" % "test").
  jvmSettings(
     name := "quizleague-jvm",
     
	libraryDependencies += "com.google.appengine" % "appengine-testing" % appengineVersion % "test",
	libraryDependencies += "com.google.appengine" % "appengine-api-stubs" % appengineVersion % "test",
	libraryDependencies += "com.google.cloud" % "google-cloud-storage" % "0.4.0",
	libraryDependencies += "com.google.appengine.tools" % "appengine-gcs-client" % "0.6",
	libraryDependencies += "org.apache.directory.studio" % "org.apache.commons.io" % "2.4",
    libraryDependencies += "org.glassfish.jersey.containers" % "jersey-container-servlet-core" % "2.25.1"
    
	
  ).
  jsSettings(
    name := "quizleague-js",
    ngBootstrap := Some("quizleague.web.maintain.AppModule"),
    npmDependencies in Compile += "@angular/common" -> "~4.0.0",
	npmDependencies in Compile += "@angular/compiler" -> "~4.0.0",
	npmDependencies in Compile += "@angular/core" -> "~4.0.0",
	npmDependencies in Compile += "@angular/forms" -> "~4.0.0",
	npmDependencies in Compile += "@angular/http" -> "~4.0.0",
	npmDependencies in Compile += "@angular/platform-browser" -> "~4.0.0",
	npmDependencies in Compile += "@angular/platform-browser-dynamic" -> "~4.0.0",
	npmDependencies in Compile += "@angular/router" -> "~4.0.0",
	npmDependencies in Compile += "@angular/upgrade" -> "~4.0.0",
	npmDependencies in Compile += "@angular/animations" -> "~4.0.0",
	npmDependencies in Compile += "core-js" -> "^2.4.1",
	npmDependencies in Compile += "zone.js" -> "^0.8.4",
	npmDependencies in Compile += "rxjs" -> "~5.0.1",
	npmDependencies in Compile += "@angular/cdk" -> "github:angular/cdk-builds",
	npmDependencies in Compile += "@angular/material" -> "github:angular/material2-builds",
	npmDependencies in Compile += "@angular/flex-layout" -> "*",
    npmDependencies in Compile += "angular-in-memory-web-api" -> "~0.2.5",
    npmDependencies in Compile += "es-module-loader" -> "^1.3.5",
    npmDependencies in Compile += "reflect-metadata" -> "^0.1.8",
    npmDependencies in Compile += "hammerjs" -> "^2.0.8",
	npmDevDependencies in Compile += "angulate2-scalajs-bundler" -> "1.0.3",
	webpackConfigFile in fastOptJS := Some(baseDirectory.value  / "webpack.config.js"),
	webpackConfigFile in fullOptJS := Some(baseDirectory.value  / "webpack.prod.config.js")
  )

lazy val server = quizleague.jvm.settings(
  scalaJSProjects := Seq(web),
  pipelineStages in Assets := Seq(scalaJSPipeline),
  compile in Compile := ((compile in Compile) dependsOn scalaJSPipeline).value,
  addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)
).enablePlugins(SbtWeb,JettyPlugin, WebScalaJSBundlerPlugin)
lazy val web = quizleague.js .
enablePlugins(Angulate2Plugin,ScalaJSWeb,ScalaJSBundlerPlugin)

