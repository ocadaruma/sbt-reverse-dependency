lazy val root = (project in file("."))
  .settings(
    sbtPlugin := true,
    name := "sbt-reverse-dependency",
    licenses += "MIT" -> url("https://raw.githubusercontent.com/ocadaruma/sbt-reverse-dependency/master/LICENSE"),

    version := "0.1.0",
    organization := "com.mayreh",
    organizationName := "Haruki Okada",
    startYear := Some(2016),

    /**
     * scripted test settings
     */
    scriptedSettings,
    scriptedLaunchOpts += s"-Dplugin.version=${version.value}",
    scriptedBufferLog := false
  )
