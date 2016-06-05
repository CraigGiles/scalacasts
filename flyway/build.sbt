

// Database Migrations:
// run with "sbt flywayMigrate"
// http://flywaydb.org/getstarted/firststeps/sbt.html

//$ export DB_DEFAULT_URL="jdbc:h2:/tmp/example.db"
//$ export DB_DEFAULT_USER="sa"
//$ export DB_DEFAULT_PASSWORD=""

libraryDependencies += "org.flywaydb" % "flyway-core" % "4.0"

//lazy val databaseUrl = sys.env.getOrElse("DB_DEFAULT_URL", "jdbc:postgresql://localhost:5432/myapp")
//lazy val databaseUrl = sys.env.getOrElse("DB_DEFAULT_URL", "jdbc:h2:mem:test")
lazy val databaseUrl = sys.env.getOrElse("DB_DEFAULT_URL", "jdbc:mysql://192.168.99.100:32773/test")
lazy val databaseUser = sys.env.getOrElse("DB_DEFAULT_USER", "root")
lazy val databasePassword = sys.env.getOrElse("DB_DEFAULT_PASSWORD", "root")

flywayLocations := Seq("classpath:db/migration")
//flywayLocations := Seq("classpath:db/migration")

flywayUrl := databaseUrl
flywayUser := databaseUser
flywayPassword := databasePassword
