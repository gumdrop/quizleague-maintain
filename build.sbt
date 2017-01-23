    
val circeVersion = "0.7.0"
    //resolvers += "mmreleases" at "https://artifactory.mediamath.com/artifactory/libs-release-global"
    //addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)

lazy val commonSettings = Seq(
  organization := "org.chilternquizleague",
  version := "0.0.1",
  scalaVersion := "2.11.8",
  scalacOptions ++= Seq("-deprecation","-unchecked","-feature","-Xlint"),
  resolvers += Resolver.sonatypeRepo("snapshots"),

  	libraryDependencies ++= Seq(
	  "io.circe" %%% "circe-core",
	  "io.circe" %%% "circe-generic",
	  "io.circe" %%% "circe-parser"
	).map(_ % circeVersion),

  scalacOptions ++= (if (isSnapshot.value) Seq.empty else Seq({
        val a = baseDirectory.value.toURI.toString.replaceFirst("[^/]+/?$", "")
        val g = "https://raw.githubusercontent.com/gumdrop/quizleague-maintain"
        s"-P:scalajs:mapSourceURI:$a->$g/v${version.value}/"
      }))
)





//for @accessor annotation support
//addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)



lazy val root = project.in(file(".")).
  enablePlugins(Angulate2Plugin).
  settings(commonSettings: _*).
  settings( 
    name := "chilternquizleague-maintain",
    ngBootstrap := Some("org.chilternquizleague.maintain.AppModule"),
    //libraryDependencies += "com.mediamath" %%% "scala-json" % "1.0",


    resolvers += Resolver.sonatypeRepo("releases")

  )


