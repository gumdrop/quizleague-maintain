package quizleague.web.site.common

trait SectionComponent {
  val sideMenuService:SideMenuService
}

trait NoMenuComponent{
  this:SectionComponent =>
  sideMenuService.showMenu.next(false)
}

trait MenuComponent {
  this:SectionComponent =>
  sideMenuService.showMenu.next(true)
  
}