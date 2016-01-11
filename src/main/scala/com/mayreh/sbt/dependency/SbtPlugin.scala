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
    reverseDependency <<= (
      state,
      reverseDependencySeparator,
      printBaseDirectory,
      thisProjectRef) map { (state, separator, printBaseDirectory, ref) =>

      val resolvedProjects = (for {
        reverseDependencyMap <- state.get(reverseDependencyMapKey)
        projects <- reverseDependencyMap.get(ref)
      } yield {
        projects
      }).getOrElse(Nil)

      resolvedProjects.headOption.foreach { _ =>
        val str = resolvedProjects.map { resolved =>
          if (printBaseDirectory) resolved.base.getAbsolutePath else resolved.id
        } mkString separator

        println(str)
      }

      resolvedProjects
    }
  )
}
