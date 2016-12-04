package org.chilternquizleague.maintain.venue

import angulate2.std._
import angulate2.router.ActivatedRoute
import org.chilternquizleague.maintain.model.Venue
import angulate2.common.Location

@Component(
  selector = "ql-venue",
  template = """
  <div>
    <form>
    <md-input placeholder="Name" type="text" id="name"
         required
         [(ngModel)]="item.name" name="name">
    </md-input>
    <button md-button (click)="save()">Save</button>
    </form>
  </div>
  """    
)
class VenueComponent(
    service:VenueService,
    route: ActivatedRoute,
    location:Location) extends OnInit { 
  
  var item:Venue = Venue("", "dummy")
  
  def save(): Unit = location.back()
  
  override def ngOnInit(): Unit = route.params
    .switchMap( (params,i) => service.get(params("id")) )
    .subscribe(this.item = _)
  
}