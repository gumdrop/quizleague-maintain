package quizleague.web.site.venue

import angulate2.std.Component
import quizleague.web.site.common._
import angulate2.ext.classModeScala

@Component(
  template = """Venues"""    
)
@classModeScala
class VenuesComponent(
    override val titleService:TitleService,
    override val sideMenuService:SideMenuService) extends SectionComponent with MenuComponent with TitledComponent{
  
    setTitle("Venues")
}

@Component(
  template = """<ql-section-title><span>Venues</span></ql-section-title>"""    
)
class VenuesTitleComponent {
  
}