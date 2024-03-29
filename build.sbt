name := """orchard"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.3"

resolvers += Resolver.jcenterRepo

scalacOptions ++= Seq(
  "-deprecation",
  "-feature",
  "-unchecked"
)

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-slick" % "3.0.1",
  "com.typesafe.play" %% "play-slick-evolutions" % "3.0.1",
  "com.typesafe.play" %% "play-json" % "2.6.3",
  "mysql" % "mysql-connector-java" % "5.1.36",
  //"com.typesafe.slick" %% "slick" % "3.1.1",
  //"com.typesafe.slick" % "slick-codegen_2.11" % "3.1.1",
  "org.scalatestplus.play" %% "scalatestplus-play" % "3.0.0" % Test,
  "com.mohiva" %% "play-silhouette" % "5.0.0",
  "com.mohiva" %% "play-silhouette-password-bcrypt" % "5.0.0",
  "com.mohiva" %% "play-silhouette-persistence" % "5.0.0",
  "com.mohiva" %% "play-silhouette-crypto-jca" % "5.0.0",
  "com.mohiva" %% "play-silhouette-testkit" % "5.0.0" % "test",
  filters,
  guice
)

routesGenerator := InjectedRoutesGenerator

unmanagedResourceDirectories in Assets += baseDirectory.value / "frontend" / "dist"

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.example.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.example.binders._"

/*
slick <<= slickCodeGenTask
sourceGenerators in Compile <+= slickCodeGenTask

lazy val slick = TaskKey[Seq[File]]("gen-tables")
lazy val slickCodeGenTask = (sourceManaged, dependencyClasspath in Compile, runner in Compile, streams) map { (dir, cp, r, s) =>
  val outputDir = new File(".").getAbsoluteFile.getParent
  val jdbcDriver = "com.mysql.jdbc.Driver"
  val slickDriver = "slick.driver.MySQLDriver"
  val username = "orchard"
  val password = "orchard"
  val url = "jdbc:mysql://127.0.0.1/orchard"
  val pkg = "app.models"
  toError(r.run("slick.codegen.SourceCodeGenerator", cp.files, Array(slickDriver, jdbcDriver, url, outputDir, pkg, username, password), s.log))
  val fname = outputDir + "/" + "app/models" + "/Tables.scala"
  Seq(file(fname))
}
*/