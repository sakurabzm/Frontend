package de.tkip.sbpm.frontend

import japgolly.scalajs.react._
import vdom.html_<^._
import de.tkip.sbpm.frontend.AppRouter.SubjectPages
import japgolly.scalajs.react.extra.router.RouterConfigDsl
import org.scalajs.dom
import org.scalajs.dom.html

import scala.collection.mutable.{ListBuffer, Map}

/**
  * Created by Wang on 2017/5/2.
  */
case class StateAttributes(st: String){
  var stateType : String = st
  def changeStateType(nst: String) = {
    stateType = nst
  }
}

case class TransitionAttributes(tt: String){
  var testType : String = tt
  def changeStateType(ntt: String) = {
    testType = ntt
  }
}

case class SubToSubMessage(subId: Int, stateId: Int, message: String = ""){
  val subid = subId
  val stateid = stateId
  var msg = message
}

class Transition(id: Int, targetId: Int, subToSubMessage: SubToSubMessage, sx: Int, sy: Int, tx: Int, ty: Int){

  val tid = id
  var target = ""
  var source = ""
  val stosMsg = subToSubMessage
  var sxc = sx
  var syc = sy
  var txc = tx
  var tyc = ty
  val tip = 20
  var colour = "#800"
  val colour2 = "#00bff3"

//  def arrowView(): VdomElement
}


class State(id: Int, sx: Int, sy: Int, sType: String){
  val radius = 50                                                                   //the size of the state
  val arrowLength = 100                                                             //the length of the arrow
  val shortLine = 40                                                                //the length of the polyline arrow
  val tip = 20                                                                      //the length of the arrow tip
  var selected = false
  var x = sx
  var y = sy
  val sID = id
  var stateType = sType
  val sTransition: ListBuffer[Transition] = new ListBuffer[Transition]()
  var anchorLeft = (x, y + radius/2)
  var anchorRight = (x + radius, y + radius/2)
  var anchorTop = (x + radius/2, y)
  var anchorBottom = (x + radius/2, y + radius)
  var colour = "#800"
  val colour2 = "#00bff3"
}






object SubjectViewPage {


  var subjectID = -1
  val OuterX    = 1280
  val OuterY    = 1800
  var stateID : Int = 3

  var newState: Int = 0

  var stateAttrMap : Map[Int, StateAttributes] = Map()
  var tranAttrMap : Map[Int, TransitionAttributes] = Map()

  var hasFocus : Boolean = true // test

  val currentValue : Option[String] = Some("test textarea")

  def onTextChange(e: ReactEventFromInput): Option[Callback] = {
    currentValue.map(before => {
      val after = e.target.value
      Callback.alert(s"Value changed from [$before] to [$after]")
    })
  }



  val OuterDiv =
    <.div(
      ^.position.relative,
      ^.backgroundColor   := "#FFE4B5",
      ^.width             := "100%",
      ^.height            := 800.px,
      ^.border            := "solid 1px #333"
    )

  val component = ScalaComponent.builder[SubjectPages]("StatePage").render
  { p =>
    subjectID = p.props.id
    Main()
  }.build





//  val component = ScalaComponent.builder[SubjectPages]("StatePage").render
//  { p =>
//    states.foreach(f =>
//      <.div(
//       <.h1("test test")
//      )
//    )
//  }


  class Backend($: BackendScope[Unit, Int]) {

    def body =
      <.div(                          // body
        ^.float          := "left",
        ^.width           := "70%",
        ^.height          := 600.px,
        ^.border          := "solid 1px #333",
        ^.backgroundColor := "#528B8B",
        ^.marginLeft      := "15%",
        ^.border          := "solid 1px #333",
        ^.overflow.scroll
      )

    def footer =
      <.div(                        // footer
        ^.position.fixed,
        ^.bottom          := 0.px,
        ^.left            := 0.px,
        ^.height          := 70.px,
        ^.width           := "100%",
        (^.backgroundColor := "#8B8B00").when(hasFocus)
      )

    def leftSide =
      <.div(
        ^.position.absolute,        // left side
        ^.backgroundColor := "#ddd",
        ^.border          := "solid 1px #191970",
        ^.left            := 0.px,
        ^.top             := 0.px,
        ^.width           := "15%",
        ^.height          := 600.px,

        <.div(
          ^.position.relative,
          ^.width         := "100%",
          ^.height        := "40%",
          ^.backgroundColor := "#F5DEB3",
          <.br,
          <.br,
          <.a(
            "Dashboard",
            ^.href          := "#",
            ^.fontSize      := "120%",
            ^.marginLeft    := "10%"
          ),
          <.br,
          <.br,
          <.a(
            "Process",
            ^.href          := "#",
            ^.fontSize      := "120%",
            ^.marginLeft    := "10%"
          ),
          <.br,
          <.br,
          <.a(
            "Create new process",
            ^.href          := "#",
            ^.fontSize      := "120%",
            ^.marginLeft    := "10%"
          )
        )
      )

    def rightSide =
      <.div(                       // right side
        ^.position.absolute,
        //     ^.tabIndex := 0,
        ^.right           := 0.px,
        ^.width           := "15%",
        ^.height          := 600.px,
        ^.border          := "solid 1px #333",
        ^.background      := "#FFB6C1",

        <.div(
          ^.position.relative,
          ^.marginTop       := 0.px,
          ^.width           := "100%",
          ^.height          := "30%",
          ^.backgroundColor := "#EE7621",
          ^.overflow.scroll,

          <.label(                                    // Layout Setting
            ^.position.relative,
            ^.width         := "100%",
            ^.height        := "15%",
            ^.borderBottom  := "dashed 1px",
            <.p(
              ^.textAlign     := "center",
              ^.letterSpacing := "1px",
              ^.fontSize      := "120%",
              "Layout Setting "
            )
          ),
          <.br,
          <.select(
            ^.id             := "layout",
            ^.marginLeft     := 10.px,
            <.option(^.value := "T", "from top to bottom"),
            <.option(^.value := "L", "from left to right")
          ),
          <.br,
          <.label(                                    // Behavior Settings
            ^.position.relative,
            ^.width           := "100%",
            ^.height          := "15%",
            ^.marginTop       := "5%",
            ^.borderBottom  := "dashed 1px",
            <.p(
              ^.textAlign     := "center",
              ^.letterSpacing := "1px",
              ^.fontSize      := "120%",
              "Behavior Settings "
            )
          ),
          <.br,
          <.button(
            "Add new state",
            ^.position.relative,
            ^.marginLeft      := "10%",
            ^.width           := "80%",
            ^.height          := 20.px,
            ^.fontSize        := "90%",
            ^.borderRadius    := 6.px
          ),
          <.br,
          <.button(
            "Reset manual position",
            ^.position.relative,
            ^.marginTop       := 5.px,
            ^.marginLeft      := "10%",
            ^.width           := "80%",
            ^.height          := 20.px,
            ^.fontSize        := "85%",
            ^.borderRadius    := 6.px
          ),
          <.br,
          <.button(
            "Clear all behaviors",
            ^.position.relative,
            ^.marginTop       := 5.px,
            ^.marginLeft      := "10%",
            ^.width           := "80%",
            ^.height          := 20.px,
            ^.fontSize        := "90%",
            ^.borderRadius    := 6.px
          )
        ),

        <.div(                              // State Setting
          ^.position.relative,
          ^.width             := "100%",
          ^.height            := "70%",
          ^.backgroundColor   := "#9933FF",
          ^.overflow.scroll,

          <.label(
            ^.position.relative,
            ^.width           := "100%",
            ^.height          := "8%",
            ^.marginTop       := "5%",
            ^.borderBottom  := "dashed 1px",
            <.p(
              ^.textAlign     := "center",
              ^.fontSize      := "120%",
              "State Settings"
            )
          ),
          <.br,
          <.input.checkbox(
            ^.position.relative,
            ^.marginLeft      := "10%"
          ),
          <.label(
            ^.position.relative,
            ^.marginLeft      := 10.px,
            "Start state"
          ),
          <.br,
          <.label(
            ^.position.relative,
            ^.marginLeft      := "10%",
            "Type : "
          ),
          <.br,
          <.select(
            ^.position.relative,
            ^.marginLeft      := "10%",
            ^.onChange        ==> selectStateType,
            <.option("Action"),
            <.option("Send"),
            <.option("Receive"),
            <.option("Modal join"),
            <.option("Modal split"),
            <.option("End")
          ),
          <.br,
          <.label(
            ^.position.relative,
            ^.marginTop       := 10.px,
            ^.marginLeft      := "10%",
            "Text : "
          ),
          <.br,
          <.textarea(
            ^.position.relative,
            ^.marginLeft      := "10%",
            ^.minWidth         := "80%",
            ^.minHeight    := "10%",
            ^.maxHeight   := "10%",
            ^.maxWidth    := "80%",
            ^.borderStyle     := "solid 1px #191970",
            ^.borderRadius    := 6.px,
            ^.value  := currentValue.getOrElse(""),
            //            ^.value  := "test textarea " ,
            ^.onChange ==>? onTextChange
          ),
          <.br,
          <.label(
            ^.position.relative,
            ^.marginTop       := 10.px,
            ^.marginLeft      := "10%",
            "Comment:  "
          ),
          <.br,
          <.textarea(
            ^.position.relative,
            ^.marginLeft      := "10%",
            ^.minWidth         := "80%",
            ^.minHeight    := "15%",
            ^.maxHeight   := "15%",
            ^.maxWidth    := "80%",
            ^.borderStyle     := "solid 1px #191970",
            ^.borderRadius    := 6.px
          ),
          <.br,
          <.button(
            ^.position.relative,
            ^.marginTop   := 20.px,
            ^.width     := "80%",
            ^.height     := "5%",
            ^.marginLeft  := "10%",
            ^.fontSize        := "90%",
            ^.borderRadius    := 6.px,
            ^.onClick         ==> updateStateAttr,
            "Update state"
          ),
          <.button(
            ^.position.relative,
            ^.marginTop   := 10.px,
            ^.width     := "80%",
            ^.height     := "5%",
            ^.marginLeft  := "10%",
            ^.fontSize        := "90%",
            ^.borderRadius    := 6.px,
            "Connect state"
          ),
          <.button(
            ^.position.relative,
            ^.marginTop   := 10.px,
            ^.width     := "80%",
            ^.height     := "5%",
            ^.marginLeft  := "10%",
            ^.fontSize        := "90%",
            ^.borderRadius    := 6.px,
            "Delete state"
          )
        ),

        <.div(
          ^.position.relative,               // Transition Settings
          ^.width             := "100%",
          ^.height            := "70%",
          ^.backgroundColor   := "#B03060",
          ^.overflow.scroll,
          ^.display.none,

          <.label(
            ^.position.relative,
            ^.width           := "100%",
            ^.height          := "8%",
            ^.marginTop       := "5%",
            ^.borderBottom  := "dashed 1px",
            <.p(
              ^.textAlign     := "center",
              ^.fontSize      := "120%",
              "Transition Settings"
            )
          ),
          <.label(
            ^.position.relative,
            ^.marginLeft      := "10%",
            "Message Type: "
          ),
          <.select(
            ^.position.relative,
            ^.width := "80%",
            ^.height := "5%",
            ^.marginLeft      := "10%",
            <.option(
              "create new message type",
              ^.textOverflow.ellipsis
            ),
            <.option("select message type")
          ),
          <.br,
          <.label(
            ^.position.relative,
            ^.marginLeft    := "10%",
            "Text : "
          ),
          <.br,
          <.textarea(
            ^.position.relative,
            ^.marginLeft  := "10%",
            ^.maxHeight   := "8%",
            ^.minHeight   := "8%",
            ^.maxWidth    := "80%",
            ^.minWidth    := "80%",
            ^.borderRadius  := 6.px
          ),
          <.br,
          <.label(
            ^.position.relative,
            ^.marginLeft    := "10%",
            "Related Subject :"
          ),
          <.br,
          <.select(
            ^.position.relative,
            ^.width := "80%",
            ^.height := "5%",
            ^.marginLeft      := "10%",
            <.option("select related subject")
          ),
          <.br,
          <.label(
            ^.position.relative,
            ^.marginLeft    := "10%",
            "Comment : "
          ),
          <.br,
          <.textarea(
            ^.position.relative,
            ^.marginLeft  := "10%",
            ^.maxHeight   := "15%",
            ^.minHeight   := "15%",
            ^.maxWidth    := "80%",
            ^.minWidth    := "80%",
            ^.borderRadius  := 6.px
          ),
          <.br,
          <.button(
            "Update Transition",
            ^.position.relative,
            ^.marginTop   := "10%",
            ^.marginLeft  := "10%",
            ^.width       := "80%",
            ^.height      := "5%",
            ^.borderRadius  := 6.px
          ),
          <.br,
          <.button(
            "Remove Transition",
            ^.position.relative,
            ^.marginTop   := "5%",
            ^.marginLeft  := "10%",
            ^.width       := "80%",
            ^.height      := "5%",
            ^.borderRadius  := 6.px
          )
        )
      )
//    case class Transition(idsuccessor: Int){
//      var target = ""
//      var source = ""
//    }

    class TransitionGraph(id: Int, targetId: Int, subToSubMessage: SubToSubMessage, sx: Int, sy: Int, tx: Int, ty: Int)
      extends Transition(id, targetId, subToSubMessage, sx, sy, tx, ty){

      def arrowLength : Int = ty - sy
      def arrow(ax: Int, ay: Int, color: String): VdomElement =
        <.div(
          ^.position.absolute,
          <.div(
            ^.position.absolute,
            ^.transform     := "rotate(90deg)",
            ^.width         := arrowLength.px,
            ^.height        := 5.px,
            ^.left          := ax.px,
            ^.top           := ay.px,
            ^.background    := color
          ),
          <.div(
            ^.position.absolute,
            ^.transform     := "rotate(120deg)",
            ^.width         :=  tip.px,
            ^.height        :=  5.px,
            ^.left          :=  (ax+arrowLength/2+(math.sin(math.toRadians(30))*tip/2)-tip/2).px,
            ^.top           :=  (ay+arrowLength/2-(math.cos(math.toRadians(30))*tip/2)).px,
            ^.background    := color
          ),
          <.div(
            ^.position.absolute,
            ^.transform     := "rotate(60deg)",
            ^.width         :=  tip.px,
            ^.height        :=  5.px,
            ^.left          :=  (ax+arrowLength/2-(math.sin(math.toRadians(30))*tip/2)-tip/2).px,
            ^.top           :=  (ay+arrowLength/2-(math.cos(math.toRadians(30))*tip/2)).px,
            ^.background    := color
          ),
          polyLine(color),
          ^.onClick         ==> clickTransition
        )

      def polyLine(color: String): VdomElement ={
        if(sx > tx){
          <.div(
            ^.position.absolute,
            ^.width         :=  (sx-tx).px,
            ^.height        :=  5.px,
            ^.left          :=  tx.px,
            ^.top           :=  sy.px,
            ^.background    := color
          )
        }else if(sx < tx){
          <.div(
            ^.position.absolute,
            ^.width         :=  (tx-sx).px,
            ^.height        :=  5.px,
            ^.left          :=  sx.px,
            ^.top           :=  sy.px,
            ^.background    := color
          )
        }else{
          <.div()
        }
      }
      def convertx(x: Int): Int = x - (arrowLength + 1)/2
      def converty(y: Int): Int = y + (arrowLength + 1)/2

      def arrowView(): VdomElement ={
//        dom.console.info("test arrow: " + tx + "   " + ty + "   length: " + arrowLength)
        arrow(convertx(tx), converty(sy), colour)
      }

      def clickTransition(e: ReactEventFromInput) ={
        selectedTransition(tid)
        dom.console.info("Clicked!")
        e.preventDefaultCB >>
          $.modState(_ + 0)
      }
    }

    class StateGraph(id: Int, sx: Int, sy: Int, sType: String)
      extends State(id, sx, sy, sType){

      def stateGraph(xx: Int, yy: Int, color: String): VdomElement =
        <.div(
          ^.position.absolute,
          ^.width         := radius.px,
          ^.height        := radius.px,
          ^.lineHeight    := radius.px,
          ^.left          := xx.px,
          ^.top           := yy.px,
          ^.borderRadius  := (radius/2).px,
          ^.background    := color,
          ^.border        := 2.px,
          ^.fontSize      := "120%",
          ^.overflow      := "hidden",
          ^.textAlign     := "center",
          stateAttrMap.getOrElseUpdate(id, StateAttributes("Action")).stateType,
          ^.onClick       ==> select
        )

      def addTransition(tr: TransitionGraph): Unit ={
          sTransition += tr
      }


      def stateView(): VdomElement ={
//        dom.console.info(s"test state: id: $sID x,y: $sx, $sy   anchor L R T B: $anchorLeft, $anchorRight, $anchorTop, $anchorBottom" )
          stateGraph(x, y, colour)
      }

      def select(e: ReactEventFromInput) ={
        selectedState(sID)

        e.preventDefaultCB >>
          $.modState(_ + 0)
      }
    }


    //class backend
    var selectedStateID: Int = -1
    var selectedTransitionID: Int = -1
    var predStateID: Int = -1
    var selectedStateType: String = "Action"
    val states = ListBuffer[StateGraph]()
    val storeStates = ProcessInstance.loadSubject(subjectID)
    val transitionList = ListBuffer[TransitionGraph]()
    val state1 = new StateGraph(1, OuterX/2 - 50, 20, "A")
    val state2 = new StateGraph(2, OuterX/2 - 50, 180, "A")
    val state3 = new StateGraph(3, OuterX/2 - 150, 340, "A")
    val state4 = new StateGraph(4, OuterX/2 + 50, 340, "E")
    val tr1 = new TransitionGraph(1001,2, SubToSubMessage(0, 1), state1.anchorBottom._1, state1.anchorBottom._2, state2.anchorTop._1, state2.anchorTop._2)
    val tr2 = new TransitionGraph(2001,3, SubToSubMessage(1, 1),state2.anchorLeft._1, state2.anchorLeft._2, state3.anchorTop._1, state3.anchorTop._2)
    val tr3 = new TransitionGraph(2002,4, SubToSubMessage(2, 1),state2.anchorRight._1, state2.anchorRight._2, state4.anchorTop._1, state4.anchorTop._2)
    state1.addTransition(tr1)
    state2.addTransition(tr2)
    state2.addTransition(tr3)
    transitionList += tr1
    transitionList += tr2
    transitionList += tr3
    storeStates += state1
    storeStates += state2
    storeStates += state3
    storeStates += state4
    var stateType = "A"

    storeStates.map(s => states += s.asInstanceOf[StateGraph])  //测试能否互相转换

    private var outerRef: html.Element = _
    def init: Callback =
      Callback(outerRef.focus())

    def selectStateType(e: ReactEventFromInput) = {
      selectedStateType = e.target.value
      if(stateAttrMap.contains(selectedStateID)){
        stateAttrMap(selectedStateID).changeStateType(selectedStateType)
        dom.console.log("selected state type " + stateAttrMap(selectedStateID).stateType)
      }
      e.preventDefaultCB >>
        $.modState(_ + 0)
    }

    def updateStateAttr(e: ReactEventFromInput) = {
      dom.console.log("selected state id " + stateAttrMap)
      if(stateAttrMap.contains(selectedStateID)){
        stateAttrMap(selectedStateID).changeStateType(selectedStateType)
        dom.console.log("selected state type " + stateAttrMap(selectedStateID).stateType)
      }
      e.preventDefaultCB >>
        $.modState(_ + 0)
    }

    def addState(e: ReactEventFromInput) ={
      stateID += 1
      states.find(s => s.sID == selectedStateID) match {
        case Some(state) => {
            val nState = new StateGraph(stateID, state.x, state.y + 160, stateType)
            val newTr = new TransitionGraph(state.sID*100+state.sTransition.length+1, stateID, SubToSubMessage(0, 1),
              state.anchorBottom._1, state.anchorBottom._2, nState.anchorTop._1, nState.anchorTop._2)
            state.addTransition(newTr)
            transitionList += newTr
            states += nState
            storeStates += nState
            stateAttrMap += stateID -> StateAttributes(selectedStateType)
        }
        case None => {
          val nState = new StateGraph(stateID, OuterX/4, 160 * newState, stateType)
          states += nState
          storeStates += nState
          newState += 1
          stateAttrMap += stateID -> StateAttributes(selectedStateType)
          dom.console.info("TODO")
        }
      }


      e.preventDefaultCB >>
        $.modState(_ + 0)
    }

    def selectedState(sid: Int): Unit ={

      if(selectedStateID == sid){
        states.find(s => s.sID == selectedStateID) match {
          case Some(state) => {
            state.colour = "#800"
          }
          case None => {}
        }
        predStateID = -1
        selectedStateID = -1
      }else{
        if(selectedStateID != -1){
          predStateID = selectedStateID
        }
        selectedStateID = sid

        states.find(s => s.sID == selectedStateID) match {
          case Some(state) => {
            state.colour = "#00bff3"
          }
          case None => {}
        }

        if(predStateID != -1){
          states.find(s => s.sID == predStateID) match {
            case Some(state) => {
              state.colour = "#800"
            }
            case None => {}
          }
        }


      }
    }

    def selectedTransition(tid: Int): Unit ={

      if(selectedTransitionID == tid){
        transitionList.find(t => t.tid == selectedTransitionID) match {
          case Some(transitionGraph) => {
            transitionGraph.colour = "#800"
          }
          case None => {}
        }
        selectedTransitionID = -1
      }else{
        if(selectedTransitionID != -1){
          transitionList.find(t => t.tid == selectedTransitionID) match {
            case Some(transitionGraph) => {
              transitionGraph.colour = "#800"
            }
            case None => {}
          }
        }

        selectedTransitionID = tid

        transitionList.find(t => t.tid == selectedTransitionID) match {
          case Some(transitionGraph) => {
            transitionGraph.colour = "#00bff3"
          }
          case None => {}
        }

      }
    }


    def stateTest(e: ReactEventFromInput) ={
      dom.console.info("self: " + this.toString)
      e.preventDefaultCB
    }

    def connState(e: ReactEventFromInput) = {
//      states.head.xx -= 200
      dom.console.info("state type: " + stateType)
      e.preventDefaultCB
    }


    def saveState(e: ReactEventFromInput) = {
      ProcessInstance.saveSubject(subjectID, storeStates)
      val url = "http://localhost:12345/#subjects"
      dom.window.location.href = url
      e.preventDefaultCB
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
            <.button("save", ^.onClick ==> saveState),
            <.br,
            <.br,
            OuterDiv.ref(outerRef = _)(
              leftSide,
              rightSide,
              footer,
              body,
//            State(OuterX/2 - 50, 20, "A").state,
//            Arrow_Vertical(OuterX/2 - 75, 120).arrow
              states.map(_.stateView()).toVdomArray,
              transitionList.map(_.arrowView()).toVdomArray
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
