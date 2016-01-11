package com.mayreh.sbt.dependency

import sbt._

trait SbtPluginKeys {
  val reverseDependencySeparator = SettingKey[String]("reverse-dependency-separator", "separator string for printing projects.")
  val printBaseDirectory = SettingKey[Boolean]("print-base-directory", "print project base directory instead of project ID.")
  val reverseDependency = TaskKey[Seq[ResolvedProject]]("reverse-dependency", "print and return which projects are depend on current project.")
}

object AttributeKeys {
  val reverseDependencyMapKey = AttributeKey[DependencyMap]("com.mayreh.sbt.dependency.reverseDependencyMap")
}

