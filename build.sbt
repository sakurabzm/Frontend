
enablePlugins(ScalaJSPlugin)
enablePlugins(WorkbenchPlugin)

name := "Scala.js Tutorial"

scalaVersion := "2.12.1"

skip in packageJSDependencies := false


libraryDependencies ++= Seq(

  "org.scala-js" %%% "scalajs-dom" % "0.9.1",
  "be.doeraene" %%% "scalajs-jquery" % "0.9.1",
  "com.lihaoyi" %%% "utest" % "0.4.4" % "test",
  "com.github.japgolly.scalajs-react" %%% "core" % "1.0.0-RC1",
  "com.github.japgolly.scalajs-react" %%% "extra" % "1.0.0-RC2",
  "com.olvind" %%% "scalajs-react-components" % "0.8.0",
  "com.github.japgolly.scalacss" %%% "core" % "0.5.1",
  "com.github.japgolly.scalacss" %%% "ext-react" % "0.5.1"
)

//testFrameworks += new TestFramework("utest.runner.Framework")
//
//jsDependencies +=
//  "org.webjars" % "jquery" % "2.1.4" / "2.1.4/jquery.js"
//
//jsDependencies += RuntimeDOM

jsDependencies ++= Seq(

  "org.webjars.bower" % "react" % "15.4.2"
    /        "react-with-addons.js"
    minified "react-with-addons.min.js"
    commonJSName "React",

  "org.webjars.bower" % "react" % "15.4.2"
    /         "react-dom.js"
    minified  "react-dom.min.js"
    dependsOn "react-with-addons.js"
    commonJSName "ReactDOM",

  "org.webjars.bower" % "react" % "15.4.2"
    /         "react-dom-server.js"
    minified  "react-dom-server.min.js"
    dependsOn "react-dom.js"
    commonJSName "ReactDOMServer"

//"org.webjars.npm" % "react"     % "0.14.2" / "react-with-addons.js" commonJSName "React"    minified "react-with-addons.min.js",
//"org.webjars.npm" % "react-dom" % "0.14.2" / "react-dom.js"         commonJSName "ReactDOM" minified "react-dom.min.js" dependsOn "react-with-addons.js"


)

//import com.lihaoyi.workbench.WorkbenchPlugin._
//
//workbenchSettings

//updateBrowsers <<= updateBrowsers.triggeredBy(fastOptJS in Compile)
//
//refreshBrowsers <<= refreshBrowsers.triggeredBy(fastOptJS in Compile)
//
//bootSnippet := "scalajsreact.template.ReactApp().main();"