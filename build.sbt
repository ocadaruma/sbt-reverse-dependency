lazy val root = (project in file("."))
  .settings(
    sbtPlugin := true,
    crossSbtVersions := Vector("0.13.16", "1.0.2"),
    name := "sbt-reverse-dependency",
    licenses += "MIT" -> url("https://raw.githubusercontent.com/ocadaruma/sbt-reverse-dependency/master/LICENSE"),

    version := "0.1.2-SNAPSHOT",
    organization := "com.mayreh",
    organizationName := "Haruki Okada",
    startYear := Some(2016),

    /**
     * scripted test settings
     */
    scriptedLaunchOpts += s"-Dplugin.version=${version.value}",
    scriptedBufferLog := false
  )
