import sbt._
import sbt.Keys._
import complete.DefaultParsers._
import com.typesafe.sbt.SbtScalariform
import com.typesafe.sbt.SbtScalariform.ScalariformKeys

import scalariform.formatter.preferences._

object ScalaFormatter extends sbt.AutoPlugin {
  lazy val settings =
    ScalariformKeys.preferences := ScalariformKeys.preferences.value
      .setPreference(DanglingCloseParenthesis, Prevent)
      .setPreference(AlignParameters, false)
      .setPreference(CompactStringConcatenation, false)
      .setPreference(DoubleIndentClassDeclaration, true)
      .setPreference(IndentSpaces, 2)
      .setPreference(PlaceScaladocAsterisksBeneathSecondAsterisk, true)
      .setPreference(PreserveSpaceBeforeArguments, false)
      .setPreference(RewriteArrowSymbols, false)
      .setPreference(SpaceBeforeColon, false)
      .setPreference(SpaceInsideBrackets, false)
      .setPreference(SpaceInsideParentheses, false)
      .setPreference(SpacesAroundMultiImports, false)

  override def trigger = Plugins.allRequirements
  override def requires = SbtScalariform
}
