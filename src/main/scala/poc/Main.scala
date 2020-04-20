package poc

import scala.reflect.runtime.universe
import sbt.testing.Framework
import org.scalatools.testing.Runner2
import sbt.testing.{Event, EventHandler, Fingerprint, SubclassFingerprint, TaskDef}
import poc.api.{Context, RunnerOptions}
import sbt.testing.Runner
import sbt.testing.Task
import scala.annotation.tailrec
import org.scalatest.run

object Main {
  def main(args: Array[String]): Unit = {
    def context                         = Context.scalatest
    val RunnerOptions(args, remoteArgs) = context.runnerOptions

    val cl        = getClass().getClassLoader()
    val framework = cl.loadClass(context.frameworkClass).getConstructor().newInstance().asInstanceOf[Framework]
    val runner    = framework.runner(args, remoteArgs, cl)

    val testTasks = context.taskDefs

    val eventHandler: EventHandler = event => {
      println(s"Finished test suite: ${event.fullyQualifiedName()}")
      println(s"duration: ${event.duration()}")
      println(s"selector: ${event.selector()}")
      println(s"fingerprint: ${event.fingerprint()}")
      println(s"status: ${event.status()}")
      println(s"throwable defined: ${event.throwable().isDefined()}")
    }

    println(s"starting test tasks for ${framework.name()}")
    runTasks(eventHandler, runner.tasks(testTasks))
    runTasks(eventHandler, runner.tasks(testTasks))
    runner.done()
  }

  @tailrec
  def runTasks(handler: EventHandler, tasks: Array[Task]): Unit = {
    val newTasks = tasks.flatMap(task => task.execute(handler, Array.empty))
    if (newTasks.nonEmpty) runTasks(handler, newTasks)
  }

}
