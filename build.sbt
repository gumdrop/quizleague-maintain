name := "Quiz League"

//EclipseKeys.useProjectId := true

val circeVersion = "0.7.0"
addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)

lazy val commonSettings = Seq(
  organization := "quizleague",
  version := "0.0.1",
  scalaVersion := "2.11.8",
  scalacOptions ++= Seq("-deprecation","-unchecked","-feature","-Xlint"),
  scalacOptions ++= (if (isSnapshot.value) Seq.empty else Seq({
        val a = baseDirectory.value.toURI.toString.replaceFirst("[^/]+/?$", "")
        val g = "https://raw.githubusercontent.com/gumdrop/quizleague"
        s"-P:scalajs:mapSourceURI:$a->$g/v${version.value}/"
      })),
  resolvers += Resolver.sonatypeRepo("snapshots")
  
)

lazy val root = project.in(file(".")).
  aggregate(web, server).
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
	libraryDependencies += "io.github.cquiroz" %%% "scala-java-time" % "2.0.0-M7",
	libraryDependencies += "org.scalatest" %%% "scalatest" % "3.0.1" % "test").
  jvmSettings(
     name := "quizleague-jvm"
  ).
  jsSettings(
    name := "quizleague-js"
  )

lazy val server = quizleague.jvm
lazy val web = quizleague.js .
	enablePlugins(Angulate2Plugin)