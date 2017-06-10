package de.tkip.sbpm.frontend

import japgolly.scalajs.react._
import vdom.html_<^._
import de.tkip.sbpm.frontend.AppRouter.SubjectPages
import org.scalajs.dom
import org.scalajs.dom.html
import scala.collection.mutable.ListBuffer

/**
  * Created by Wang on 2017/5/2.
  */

case class Transition(successor: Int){

}

class State(id: Int, x: Int, y: Int, sType: String, transition: ListBuffer[Transition]){

  val radius = 50                                                                   //the size of the state
  val arrowLength = 100                                                             //the length of the arrow
  val shortLine = 40                                                                //the length of the polyline arrow
  val tip = 20                                                                      //the length of the arrow tip
  var selected = false
  val sID = id
  var xx = x
  var yy = y
  var ssType = sType
  val sTransition = transition
//  var ax = x + radius/2 - arrowLength/2
//  var ay = y + radius + arrowLength/2



  def state(xx: Int, yy: Int): VdomElement =
    <.div(
      ^.position.absolute,
      ^.width         := radius.px,
      ^.height        := radius.px,
      ^.left          := xx.px,
      ^.top           := yy.px,
      ^.borderRadius  := (radius/2).px,
      ^.background    := "#800",
      ^.fontSize      := "180%",
      <.p(
        ^.height      := radius.px,
        ^.lineHeight  := radius.px,
        ^.width       := radius.px,
        ^.overflow    := "hidden",
        ^.textAlign   := "center",
        sType
      ),
      ^.onClick       ==> select
    )

  def arrow(ax: Int, ay: Int): VdomElement =
    <.div(
      <.div(
        ^.position.absolute,
        ^.transform     :="rotate(90deg)",
        ^.width         := arrowLength.px,
        ^.height        := 3.px,
        ^.left          := ax.px,
        ^.top           := ay.px,
        ^.background    := "#800"
      ),
      <.div(
        ^.position.absolute,
        ^.transform     :="rotate(120deg)",
        ^.width         :=  tip.px,
        ^.height        :=  3.px,
        ^.left          :=  (ax+arrowLength/2+(math.sin(math.toRadians(30))*tip/2)-tip/2).px,
        ^.top           :=  (ay+arrowLength/2-(math.cos(math.toRadians(30))*tip/2)).px,
        ^.background    := "#800"
      ),
      <.div(
        ^.position.absolute,
        ^.transform     :="rotate(60deg)",
        ^.width         :=  tip.px,
        ^.height        :=  3.px,
        ^.left          :=  (ax+arrowLength/2-(math.sin(math.toRadians(30))*tip/2)-tip/2).px,
        ^.top           :=  (ay+arrowLength/2-(math.cos(math.toRadians(30))*tip/2)).px,
        ^.background    := "#800"
      )
    )
  private var a: html.Element = _
  def stateView(): VdomElement ={
    if(sTransition.isEmpty){
      state(xx, yy)
    }else if (sTransition.length == 1){
      <.div(
        state(xx, yy), arrow(xx + radius/2 - arrowLength/2, yy + radius + arrowLength/2)
      )
    }else if (sTransition.length == 2){
      val lpx = xx - shortLine
      val lpy = yy + radius/2
      val rpx = xx + radius
      val rpy = yy + radius/2
      <.div(
        state(xx, yy),
        arrow(lpx - arrowLength/2, lpy + arrowLength/2)(
          <.div(
            ^.position.absolute,
            ^.width         :=  shortLine.px,
            ^.height        :=  3.px,
            ^.left          :=  lpx.px,
            ^.top           :=  lpy.px,
            ^.background    := "#800"
          )
        ),
        arrow(rpx + shortLine - arrowLength/2, rpy + arrowLength/2)(
          <.div(
            ^.position.absolute,
            ^.width         :=  shortLine.px,
            ^.height        :=  3.px,
            ^.left          :=  rpx.px,
            ^.top           :=  rpy.px,
            ^.background    := "#800"
          )
        )
      )
    }else{
      <.div
    }
  }

  def select(e: ReactEventFromInput) ={
    SubjectViewPage.selectedState(sID)
    if(SubjectViewPage.isConnected){
      SubjectViewPage.connectStates(sID)
    }
    e.preventDefaultCB
  }
}


object SubjectViewPage {


  val OuterX    = 1280
  val OuterY    = 1800
  val states = ListBuffer[State]()
  var stateID : Int = 3
  var selectedStateID: Int = -1
  var connect = false
  var predStateID: Int = -1
  var newState: Int = 0


  val OuterDiv =
    <.div(
      ^.position.relative,
      ^.tabIndex   := 0,
      ^.width      := OuterX.px,
      ^.height     := OuterY.px,
      ^.border     := "solid 1px #333",
      ^.background := "#ddd"
    )

  val component = ScalaComponent.builder[SubjectPages]("StatePage").render
  { p =>
    dom.console.info("subject id: " + p.props.id)
    Main()
  }.build

  def selectedState(s: Int): Unit ={
    selectedStateID = s
  }

  def isConnected(): Boolean = {
    return connect
  }

  def connectStates(stateID: Int): Unit ={
    if(predStateID == -1){
      predStateID = stateID
    }else{
      states.find(s => s.sID == predStateID) match {
        case Some(state) => {
          val s = states.find(s => s.sID == stateID).get
          s.xx = state.xx
          s.yy = state.yy + 160
          state.sTransition += new Transition(stateID)
          connect = false
          predStateID = -1
        }
        case None => {
          //TODO
        }
      }
    }
  }



//  val component = ScalaComponent.builder[SubjectPages]("StatePage").render
//  { p =>
//    states.foreach(f =>
//      <.div(
//       <.h1("test test")
//      )
//    )
//  }


  class Backend($: BackendScope[Unit, Int]) {

    //test
    val tr0 = ListBuffer(Transition(1))
    val tr1 = ListBuffer(Transition(2), Transition(3))
    val state0 = new State(0, OuterX/2 - 50, 20, "A", tr0)
    val state1 = new State(1, OuterX/2 - 50, 180, "A", tr1)
    val state2 = new State(22, OuterX/2 - 50, 340, "E", ListBuffer())
    states += state0
    states += state1
    states += state2
    var stateType = "A"

    private var outerRef: html.Element = _
    def init: Callback =
      Callback(outerRef.focus())


    def addState(e: ReactEventFromInput) ={
      stateID += 1
      states.find(s => s.sID == selectedStateID) match {
        case Some(state) => {
          if(state.sTransition.isEmpty)
          {
            states += new State(stateID, state.xx, state.yy + 160, stateType, ListBuffer())
            state.sTransition += new Transition(stateID)
          }else{
            dom.console.info("TODO....")
          }

        }
        case None => {
          states += new State(stateID, OuterX/4, 160 * newState, stateType, ListBuffer())
          newState += 1
          dom.console.info("TODO")
        }
      }



      e.preventDefaultCB >>
        $.modState(_ + 0)
    }

    def stateTest(e: ReactEventFromInput) ={
      dom.console.info("self: " + this.toString)
      e.preventDefaultCB
    }

    def connState(e: ReactEventFromInput) = {
      connect = true
//      states.head.xx -= 200
      dom.console.info("state type: " + stateType)
      e.preventDefaultCB
    }

    def connStateConfirm(e: ReactEventFromInput) = {
      e.preventDefaultCB >>
        $.modState(_ + 0)
    }



    def stateChange(e: ReactEventFromInput) = {
      stateType = e.target.value
//      states.head.xx -= 200
      dom.console.info("state type: " + stateType)
      e.preventDefaultCB >>
        $.modState(_ + 0)
    }

    def render() = {
          <.div(
            <.select(
              ^.id := "stateType",
              ^.onChange ==> stateChange,
              <.option(^.value := "A", "Action"),
              <.option(^.value := "S", "Send"),
              <.option(^.value := "R", "Receive"),
              <.option(^.value := "E", "End")
            ),
            <.button("Add state", ^.onClick ==> addState),
            <.button("Connect", ^.onClick ==> connState),
            <.button("confirm", ^.onClick ==> connStateConfirm),
            <.br,
            <.br,
            OuterDiv.ref(outerRef = _)(
//            State(OuterX/2 - 50, 20, "A").state,
//            Arrow_Vertical(OuterX/2 - 75, 120).arrow
              states.map(_.stateView()).toVdomArray
          )
        )
      }
  }

  val Main = ScalaComponent.builder[Unit]("states view")
    .initialState(stateID)
    .renderBackend[Backend]
    .componentDidMount(_.backend.init)
    .build
}
