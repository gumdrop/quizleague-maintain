package quizleague.web.util

import org.scalajs.dom
import scalajs.js

object Clipboard {

  def copy(text: String) {
    val elem = dom.document.createElement("textarea")
    val fakeElem = elem.asInstanceOf[js.Dynamic];
    // Prevent zooming on iOS
    fakeElem.style.fontSize = "12pt";
    // Reset box model
    fakeElem.style.border = "0";
    fakeElem.style.padding = "0";
    fakeElem.style.margin = "0";
    // Move element out of screen horizontally
    fakeElem.style.position = "absolute";
    fakeElem.style.right = "-9999px";
    // Move element to the same position vertically
    val yPosition = if(dom.window.pageYOffset != 0) dom.window.pageYOffset else dom.document.documentElement.scrollTop;
    fakeElem.style.top = s"${yPosition}px";

    fakeElem.setAttribute("readonly", "")
    fakeElem.value = text;
    dom.document.body.appendChild(elem);
    fakeElem.select()

    dom.document.execCommand("Copy")
    
    dom.document.removeChild(elem)
    
    

  }

}