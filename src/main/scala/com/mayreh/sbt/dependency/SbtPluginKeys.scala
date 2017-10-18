package com.mayreh.sbt.dependency

import sbt._

trait SbtPluginKeys {
  val reverseDependencySeparator = settingKey[String]("Specify separator string for printing projects.")
  val printBaseDirectory = settingKey[Boolean]("Print base directory instead of project ID.")
  val printAbsolutePath = settingKey[Boolean]("Print absolute path instead of related path. This option only affects when printBaseDirectory is true.")
  val printReverseDependency = taskKey[Unit]("Print which projects are depend on current project.")
  val reverseDependency = taskKey[Seq[ResolvedProject]]("Return which projects are depend on current project.")
}

object AttributeKeys {
  private[dependency] val reverseDependencyMapKey = AttributeKey[DependencyMap]("com.mayreh.sbt.dependency.reverseDependencyMap")
}

