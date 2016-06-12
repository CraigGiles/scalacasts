name := "backend"

lazy val databaseUrl = sys.env.getOrElse("DB_DEFAULT_URL", "jdbc:mysql://192.168.99.100:32779/test")
lazy val databaseUser = sys.env.getOrElse("DB_DEFAULT_USER", "root")
lazy val databasePassword = sys.env.getOrElse("DB_DEFAULT_PASSWORD", "root")

lazy val jdbcDriver = "com.mysql.jdbc.Driver"
lazy val slickDriver = slick.driver.MySQLDriver
lazy val outputPackage = "com.gilesc.scalacasts.dataaccess"

slickCodegenSettings
slickCodegenDatabaseUrl := databaseUrl
slickCodegenDatabaseUser := databaseUser
slickCodegenDatabasePassword := databasePassword
slickCodegenDriver := slickDriver
slickCodegenJdbcDriver := jdbcDriver
slickCodegenOutputPackage := outputPackage
slickCodegenExcludedTables := Seq("schema_version")

sourceGenerators in Compile <+= slickCodegen
