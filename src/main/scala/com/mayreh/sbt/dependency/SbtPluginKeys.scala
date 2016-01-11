package com.mayreh.sbt.dependency

import sbt._

trait SbtPluginKeys {
  val reverseDependencySeparator = SettingKey[String]("reverse-dependency-separator",
    "Specify separator string for printing projects.")
  val printBaseDirectory = SettingKey[Boolean]("print-base-directory",
    "Print base directory instead of project ID.")
  val printAbsolutePath = SettingKey[Boolean]("print-absolute-path",
    "Print absolute path instead of related path. This option only affects when printBaseDirectory is true.")
  val reverseDependency = TaskKey[Seq[ResolvedProject]]("reverse-dependency",
    "Print and return which projects are depend on current project.")
}

object AttributeKeys {
  private[dependency] val reverseDependencyMapKey = AttributeKey[DependencyMap]("com.mayreh.sbt.dependency.reverseDependencyMap")
}

