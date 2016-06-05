name := "backend"

//slick <<= slickCodeGenTask
//
//sourceGenerators in Compile <+= slickCodeGenTask
//
//lazy val slick = TaskKey[Seq[File]]("gen-tables")
//
//lazy val slickCodeGenTask = (sourceManaged, dependencyClasspath in Compile, runner in Compile, streams) map { (dir, cp, r, s) =>
//  val jdbcDriver = "com.mysql.jdbc.Driver"
//  val slickDriver = "slick.driver.MySQLDriver"
//  val url = "jdbc:mysql://192.168.99.100:32773/test"
//  val outputFolder = (dir / "slick").getPath
//  val username = "root"
//  val password = "root"
//  val outputDir = (dir / "main/slick").getPath
//  val pkg = "com.gilesc.scalacasts.dataaccess"
//  toError(r.run("slick.codegen.SourceCodeGenerator", cp.files, Array(slickDriver, jdbcDriver, url, outputDir, pkg, username, password), s.log))
//  val fname = outputDir + "/" + "com/gilesc/scalacasts/dataaccess" + "/Tables.scala"
//
//  Seq(file(fname))
//}
//








//// code generation task
//lazy val slick = TaskKey[Seq[File]]("gen-tables")
//lazy val slickCodeGenTask = (sourceManaged, dependencyClasspath in Compile, runner in Compile, streams) map { (dir, cp, r, s) =>
//  // place generated files in sbt's managed sources folder
//  val outputDir = (dir / "slick").getPath
//
//  /**
//    * slick.dbs.default.driver = "slick.driver.MySQLDriver$"
//slick.dbs.default.db.driver = "com.mysql.jdbc.Driver"
//slick.dbs.default.db.url = "jdbc:mysql://localhost/playScalaSlickExample"
//slick.dbs.default.db.user = "root"
//slick.dbs.default.db.password = ""
//    */
//
//  // connection info for a pre-populated throw-away, in-memory db for this demo, which is freshly initialized on every run
////  val url = "jdbc:h2:mem:test;INIT=runscript from 'flyway/src/main/resources/db/migration/V120160604__create_registration_system_tables.sql'"
////  val url = "jdbc:h2:mem:test;INIT=runscript from 'flyway/src/main/resources/db/migration/V120160604__create_registration_system_tables.sql'"
////  val jdbcDriver = "org.h2.Driver"
////  val slickDriver = "slick.driver.H2Driver"
//
////  val url = "jdbc:mysql://192.168.99.100:32773/test"
////  val jdbcDriver = "com.mysql.jdbc.Driver"
////  val slickDriver = "slick.driver.MySQLDriver"
////
////  val pkg = "demo"
////  toError(r.run("slick.codegen.SourceCodeGenerator", cp.files, Array(slickDriver, jdbcDriver, url, outputDir, pkg, "root", "root"), s.log))
//  val fname = outputDir + "/demo/Tables.scala"
//  val jdbcDriver = "com.mysql.jdbc.Driver"
//  val slickDriver = "slick.driver.MySQLDriver"
//  val url = "jdbc:mysql://192.168.99.100:32773/test"
//  val outputFolder = (dir / "slick").getPath
//  val pkg = "demo"
//  val user = "root"
//  val password = "root"
//
//  r.run(
//    "slick.codegen.SourceCodeGenerator",
//    cp.files,
//    Array(slickDriver, jdbcDriver, url, outputFolder, pkg, user, password),
//    s.log
//  )
//
//  Seq(file(fname))
//}
//
//slick <<= slickCodeGenTask // register manual sbt command
//sourceGenerators in Compile <+= slickCodeGenTask
//

