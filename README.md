# sbt-reverse-dependency

Show which projects are depend on a specific project in your multi-project build.

[![Circle CI](https://circleci.com/gh/ocadaruma/sbt-reverse-dependency.svg?style=shield)](https://circleci.com/gh/ocadaruma/sbt-reverse-dependency)

## Usage

### Installation

Add the plugin in project/plugins.sbt:

```scala
addSbtPlugin("com.mayreh" % "sbt-reverse-dependency" % "0.2.0")
```

### Print reverse dependency

For example, suppose that there is a multi-project sbt build defined by following `build.sbt`

```scala
lazy val root = project in file(".")

lazy val core = project
lazy val web = project.dependsOn(core)
lazy val batch = project.dependsOn(core)
```

Then, `printReverseDependency` task works as below.

```bash
$ sbt core/printReverseDependency
web
batch
$ sbt web/printReverseDependency    # print nothing
$ sbt batch/printReverseDependency  # print nothing
```

There is also `reverseDependency` task, which just return `Seq[ResolvedProject]`.

### Configurations

- `reverseDependencySeparator` : Specify separator string for printing projects. `\n` as default.
- `printBaseDirectory` : Print base directory instead of project ID. `false` as default.
- `printAbsolutePath` : Print absolute path instead of related path. (only affects when `printBaseDirectory` is true) `false` by default.

## License

Published under the MIT License.
