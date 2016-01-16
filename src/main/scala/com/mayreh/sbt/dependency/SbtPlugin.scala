package com.mayreh.sbt.dependency

import sbt.Keys._
import sbt._

object SbtPlugin extends AutoPlugin {

  object autoImport extends SbtPluginKeys

  import autoImport._, AttributeKeys._

  override def trigger = allRequirements

  override def projectSettings = Seq(
    state <<= (state, buildDependencies, loadedBuild) map { (state, dependencies, build) =>
      val projectMap = build.allProjectRefs.toMap

      val reverseDependencyMap = dependencies
        .classpathTransitive
        .foldLeft[DependencyMap](Map.empty) { (acc, dependency) =>

        val (ref, dependOns) = dependency

        dependOns.foldLeft(acc) { (dependencyMap, key) =>
          val resolvedProjects = dependencyMap.getOrElse(key, Nil)
          val newValue = projectMap.get(ref).fold(resolvedProjects)(_ +: resolvedProjects)
          dependencyMap + (key -> newValue)
        }
      }

      state.put(reverseDependencyMapKey, reverseDependencyMap)
    },
    reverseDependencySeparator in Global := "\n",
    printBaseDirectory in Global := false,
    printAbsolutePath in Global := false,
    reverseDependency := {
      for {
        reverseDependencyMap <- state.value.get(reverseDependencyMapKey)
        projects <- reverseDependencyMap.get(thisProjectRef.value)
      } yield {
        projects
      }
    } getOrElse Nil,
    printReverseDependency := {
      val projects = reverseDependency.value
      val buildRoot = new File(loadedBuild.value.root.getPath)

      projects.headOption.foreach { _ =>
        val str = projects.map { project =>
          if (!printBaseDirectory.value) {
            project.id
          } else if (printAbsolutePath.value) {
            project.base.getAbsolutePath
          } else {
            project.base.relativeTo(buildRoot).get.getPath
          }
        } mkString reverseDependencySeparator.value

        println(str)
      }
    }
  )
}
