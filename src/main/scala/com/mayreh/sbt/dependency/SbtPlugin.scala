package com.mayreh.sbt.dependency

import sbt.Keys._
import sbt._

object SbtPlugin extends AutoPlugin {
  type DependencyMap = Map[ProjectRef, Seq[ProjectRef]]

  object autoImport extends SbtPluginKeys

  import autoImport._, AttributeKeys._

  override def trigger = allRequirements

  override def projectSettings = Seq(
    state <<= (state, buildDependencies) map { (stateValue, dependenciesValue) =>
      val dependencyMap = dependenciesValue.classpathTransitive

      val reverseDependencyMap = dependencyMap.foldLeft[DependencyMap](Map.empty) { (acc, dependency) =>
        val (projectRef, dependOns) = dependency

        dependOns.foldLeft(acc) { (mapUpdate, key) =>
          val refs = mapUpdate.getOrElse(key, Nil)
          mapUpdate + (key -> (projectRef +: refs))
        }
      }

      stateValue.put(reverseDependencyMapKey, reverseDependencyMap)
    },
    reverseDependencySeparator in Global := "\n",
    reverseDependency <<= (state, reverseDependencySeparator, thisProjectRef) map { (stateValue, separator, ref) =>
      val dependOns = (for {
        reverseDependencyMap <- stateValue.get(reverseDependencyMapKey)
        projectRefs <- reverseDependencyMap.get(ref)
      } yield {
        projectRefs
      }).getOrElse(Nil)

      dependOns.headOption.foreach { _ =>
        println(dependOns.map(_.project).mkString(separator))
      }

      dependOns
    }
  )
}
