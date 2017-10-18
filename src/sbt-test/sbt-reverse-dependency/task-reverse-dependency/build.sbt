/**
 * test projects
 */
lazy val root = (project in file("."))
  .settings(checkTasks: _*)

lazy val common = project
lazy val web = project.dependsOn(common)
lazy val batchCommon = project.dependsOn(common)
lazy val batch1 = project.dependsOn(batchCommon)
lazy val batch2 = project.dependsOn(batchCommon)

/**
 * check tasks
 */
def assertProjects(expected: Seq[String])(actual: Seq[ResolvedProject]): Unit = {
  val expectedSet = expected.toSet
  val actualSet = actual.map(_.id).toSet
  assert(expectedSet == actualSet, s"projects should be $expectedSet but were $actualSet")
}

lazy val checkTasks = Seq(
  TaskKey[Unit]("checkCommon") := { assertProjects(Seq("web", "batchCommon", "batch1", "batch2"))((reverseDependency in common).value) },
  TaskKey[Unit]("checkWeb") := { assertProjects(Nil)((reverseDependency in web).value) },
  TaskKey[Unit]("checkBatchCommon") := { assertProjects(Seq("batch1", "batch2"))((reverseDependency in batchCommon).value) },
  TaskKey[Unit]("checkBatch1") := { assertProjects(Nil)((reverseDependency in batch1).value) },
  TaskKey[Unit]("checkBatch2") := { assertProjects(Nil)((reverseDependency in batch2).value) }
)
