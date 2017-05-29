name := "Quiz League"

EclipseKeys.skipParents in ThisBuild := false
EclipseKeys.withSource := true

val circeVersion = "0.7.0"
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
    ngBootstrap := Some("quizleague.web.maintain.AppModule"),
    libraryDependencies ++= Seq(
	  "io.circe" %%% "circe-core",
	  "io.circe" %%% "circe-generic",
	  "io.circe" %%% "circe-parser"
	).map(_ % circeVersion),
	libraryDependencies += "io.github.cquiroz" %%% "scala-java-time" % "2.0.0-M8",
	libraryDependencies += "org.scalatest" %%% "scalatest" % "3.0.1" % "test").
  jvmSettings(
     name := "quizleague-jvm"
  ).
  jsSettings(
    name := "quizleague-js",
    npmDependencies in Compile += "rxjs" -> "5.0.1"
  )

lazy val server = quizleague.jvm.settings(
  scalaJSProjects := Seq(web),
  pipelineStages in Assets := Seq(scalaJSPipeline),
compile in Compile := ((compile in Compile) dependsOn scalaJSPipeline).value,
addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)
).enablePlugins(SbtWeb,JettyPlugin, WebScalaJSBundlerPlugin)
lazy val web = quizleague.js .
enablePlugins(Angulate2Plugin,ScalaJSWeb,ScalaJSBundlerPlugin)

