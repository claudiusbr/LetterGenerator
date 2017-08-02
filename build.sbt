import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.example",
      scalaVersion := "2.12.3",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "AgeLetters",
    scalacOptions ++= Seq("-feature","-unchecked","-deprecation"),
    libraryDependencies += scalaTest % Test,
    libraryDependencies += "org.scala-lang.modules" %% "scala-swing" % "2.0.0-M2",
    libraryDependencies += "org.mockito" % "mockito-all" % "1.9.5" % "test",
    libraryDependencies += "junit" % "junit" % "4.8.1" % "test",

    // docx4j for docx documents
    libraryDependencies += "org.slf4j" % "slf4j-api" % "1.7.5",
    libraryDependencies += "org.docx4j" % "docx4j" % "2.8.1"
  )
