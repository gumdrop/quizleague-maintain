name := "Quiz League"

EclipseKeys.skipParents in ThisBuild := false
EclipseKeys.withSource := true

val circeVersion = "0.7.0"
val appengineVersion = "1.9.54"
val angularVersion = "^4.0.0"
addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)

lazy val commonSettings = Seq(
  organization := "quizleague",
  version := "0.0.1",
  scalaVersion := "2.11.11",
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
    libraryDependencies += "org.glassfish.jersey.containers" % "jersey-container-servlet-core" % "2.25.1",
    libraryDependencies += "org.mortbay.jetty" % "jetty" % "6.1.22" % "container"
    
	
  ).
  jsSettings(
    name := "quizleague-js",
    ngBootstrap := Some("quizleague.web.site.AppModule"),
    npmDependencies in Compile += "@angular/common" -> angularVersion,
	npmDependencies in Compile += "@angular/compiler" -> angularVersion,
	npmDependencies in Compile += "@angular/core" -> angularVersion,
	npmDependencies in Compile += "@angular/forms" -> angularVersion,
	npmDependencies in Compile += "@angular/http" -> angularVersion,
	npmDependencies in Compile += "@angular/platform-browser" -> angularVersion,
	npmDependencies in Compile += "@angular/platform-browser-dynamic" -> angularVersion,
	npmDependencies in Compile += "@angular/router" -> angularVersion,
	npmDependencies in Compile += "@angular/upgrade" -> angularVersion,
	npmDependencies in Compile += "@angular/animations" -> angularVersion,
	npmDependencies in Compile += "core-js" -> "^2.4.1",
	npmDependencies in Compile += "zone.js" -> "^0.8.4",
	npmDependencies in Compile += "rxjs" -> "^5.2.0",
	npmDependencies in Compile += "@angular/cdk" -> "github:angular/cdk-builds",
	npmDependencies in Compile += "@angular/material" -> "github:angular/material2-builds",
	npmDependencies in Compile += "@angular/flex-layout" -> "*",
    npmDependencies in Compile += "angular-in-memory-web-api" -> "~0.2.5",
    npmDependencies in Compile += "es-module-loader" -> "^1.3.5",
    npmDependencies in Compile += "reflect-metadata" -> "^0.1.8",
    npmDependencies in Compile += "hammerjs" -> "^2.0.8",
	npmDevDependencies in Compile += "angulate2-scalajs-bundler" -> "1.0.3",
	//npmDevDependencies in Compile += "intersection-observer" -> "0.2.1",
    npmDevDependencies in Compile += "angular-inviewport" -> "*",
	
	webpackConfigFile in fastOptJS := Some(baseDirectory.value  / "webpack.config.js"),
	webpackConfigFile in fullOptJS := Some(baseDirectory.value  / "webpack.prod.config.js")
  )

lazy val server = quizleague.jvm.settings(
  scalaJSProjects := Seq(web),
  pipelineStages in Assets := Seq(scalaJSPipeline),
  compile in Compile := ((compile in Compile) dependsOn scalaJSPipeline).value,
  addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full),
  classesAsJar in Compile := true,
  webappResources in Compile += file(s"${baseDirectory.value}/../js/target/scala-2.11/scalajs-bundler/main/dist")
).enablePlugins(SbtWeb, WebScalaJSBundlerPlugin, AppenginePlugin)
lazy val web = quizleague.js .
enablePlugins(Angulate2Plugin,ScalaJSPlugin,ScalaJSWeb,ScalaJSBundlerPlugin)

