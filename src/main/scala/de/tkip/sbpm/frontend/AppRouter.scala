package de.tkip.sbpm.frontend

import japgolly.scalajs.react.extra.router.RouterConfigDsl
import japgolly.scalajs.react.extra._
import japgolly.scalajs.react._
import vdom.html_<^._
import japgolly.scalajs.react.extra.router._
import org.scalajs.dom

import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport
/**
  * Created by Wang on 2017/3/22.
  */
object AppRouter extends JSApp  {

  sealed trait AppPage

  case object Home extends AppPage
  case class SubjectPages(id : Int) extends AppPage
  case class BehaviorPage(subjectid: String) extends AppPage
  case object Process extends AppPage


  val config = RouterConfigDsl[AppPage].buildConfig { dsl =>
    import dsl._


//    val subjectRoutes : Rule = Subjects.routes.prefixPath_/("subject").pmap[AppPage](SubjectPages){ case SubjectPages(id) => id}
    (emptyRule
      | staticRoute(root, Home) ~> render(HomePage.component(""))
      | staticRoute("#process", Process) ~> render(DocoPage.component())
//      | subjectRoutes
      |dynamicRouteCT("#subjects" / int.caseClass[SubjectPages]) ~> dynRender(SubjectViewPage.component(_))
      ).notFound(redirectToPage(Home)(Redirect.Replace))
  }
  val baseUrl = BaseUrl.fromWindowOrigin_/

  @JSExport
  override def main(): Unit = {
    dom.console.info("Router logging is enabled. Enjoy!")
    val router = Router(baseUrl, config.logToConsole)
    router() renderIntoDOM(dom.document.body)
  }

}
