lazy val root = (project in file(".")).settings(
  sbtPlugin := true,
  name := "sbt-reverse-dependency",
  organization := "com.mayreh",
  version := "0.1.0",
  licenses += "MIT" -> url("https://raw.githubusercontent.com/ocadaruma/sbt-reverse-dependency/master/LICENSE"),

  /**
   * scripted test settings
   */
  scriptedSettings,
  scriptedLaunchOpts += s"-Dplugin.version=${version.value}",
  scriptedBufferLog := false
)
