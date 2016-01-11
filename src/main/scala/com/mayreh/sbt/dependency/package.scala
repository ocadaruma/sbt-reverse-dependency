package com.mayreh.sbt

package object dependency {
  type DependencyMap = Map[sbt.ProjectRef, Seq[sbt.ProjectRef]]
}
