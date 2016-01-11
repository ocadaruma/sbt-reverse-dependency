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
  TaskKey[Unit]("check-common") <<= reverseDependency in common map assertProjects(Seq("web", "batchCommon", "batch1", "batch2")),
  TaskKey[Unit]("check-web") <<= reverseDependency in web map assertProjects(Nil),
  TaskKey[Unit]("check-batch-common") <<= reverseDependency in batchCommon map assertProjects(Seq("batch1", "batch2")),
  TaskKey[Unit]("check-batch1") <<= reverseDependency in batch1 map assertProjects(Nil),
  TaskKey[Unit]("check-batch2") <<= reverseDependency in batch2 map assertProjects(Nil)
)
