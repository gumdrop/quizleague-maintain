package org.chilternquizleague.maintain.component

import angulate2.core.OnInit

trait InitCollector extends OnInit{
  
  var initList:List[()=>Unit] = Nil
   
  override def ngOnInit = initList.foreach(_())
  
  def addInit(fn:()=>Unit):Unit = {initList = initList :+ fn;Unit}
}