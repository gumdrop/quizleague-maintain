package quizleague.web.site.common

trait SectionModule {
  val sideMenuService:SideMenuService
}

trait NoMenuModule{
  this:SectionModule =>
  def onInit() = sideMenuService.showMenu = false
}

trait MenuModule {
  this:SectionModule =>
  def onInit() = sideMenuService.showMenu = true
  
}