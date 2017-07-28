import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.example",
      scalaVersion := "2.12.1",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "AgeLetters",
    libraryDependencies += scalaTest % Test,
    libraryDependencies += "org.scala-lang.modules" %% "scala-swing" % "2.0.0-M2",
    libraryDependencies += "org.mockito" % "mockito-all" % "1.9.5" % "test"
  )
