scalaVersion in ThisBuild := "2.13.2"

libraryDependencies ++= Seq(
  "org.scala-sbt"  % "test-interface" % "1.0" % Provided,
  "org.scalameta" %% "munit"          % "0.7.5",
  "org.scalatest" %% "scalatest"      % "3.1.2"
)
