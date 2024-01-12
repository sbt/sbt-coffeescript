import java.util.function.Supplier

lazy val root = (project in file(".")).enablePlugins(SbtWeb)

WebKeys.reporter := {
  val logFile = target.value / "test-errors.log"
  new sbt.internal.inc.LoggedReporter(-1, new xsbti.Logger {
    override def error(msg: Supplier[String]): Unit = IO.append(logFile, msg.get() + "\n")
    override def warn(msg: Supplier[String]): Unit = ()
    override def info(msg: Supplier[String]): Unit = ()
    override def debug(msg: Supplier[String]): Unit = ()
    override def trace(exception: Supplier[Throwable]): Unit = ()
  })
}

val checkTestErrorLogContents = taskKey[Unit]("check that test log contents are correct")
checkTestErrorLogContents := {
  val contents = IO.read(target.value / "test-errors.log")
  if (!contents.endsWith("""/src/main/assets/a.coffee:0:0: unexpected %
                            |one error found
                            |""".stripMargin)) {
    sys.error(s"Unexpected contents: $contents")
  }
}
