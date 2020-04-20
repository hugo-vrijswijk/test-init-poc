package poc.api

import sbt.testing.{Fingerprint, SubclassFingerprint}
import sbt.testing.TaskDef
import sbt.testing.SuiteSelector
import sbt.testing.Selector

final case class Context(
    frameworkClass: String,
    taskDefs: Array[TaskDef],
    runnerOptions: RunnerOptions
)

final case class RunnerOptions(args: Array[String], remoteArgs: Array[String])

object Context {

  def default = scalatest

  def scalatest: Context =
    Context(
      "org.scalatest.tools.Framework",
      Array(
        taskDef("tst.Test", scalatestFingerprint, Array(new SuiteSelector))
      ),
      RunnerOptions(Array.empty, Array.empty)
    )

  def munit: Context =
    Context(
      "munit.Framework",
      Array(
        taskDef("tst.Test2", munitFingerprint)
      ),
      RunnerOptions(Array.empty, Array.empty)
    )

  private def taskDef(testName: String, fingerprint: Fingerprint, selectors: Array[Selector] = Array.empty): TaskDef =
    new TaskDef(testName, fingerprint, false, selectors)

  private def scalatestFingerprint: Fingerprint =
    new SubclassFingerprint {
      def superclassName          = "org.scalatest.Suite"
      def isModule                = false
      def requireNoArgConstructor = true
    }

  private def munitFingerprint: Fingerprint =
    new SubclassFingerprint {
      def superclassName          = "munit.Suite"
      def isModule                = false
      def requireNoArgConstructor = true
    }
}
