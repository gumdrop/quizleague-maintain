package quizleague.web.maintain.season

import quizleague.web.maintain.component.ItemComponent
import quizleague.web.maintain.component._
import quizleague.web.model._

import scala.scalajs.js
import js.JSConverters._
import quizleague.web.maintain.venue.VenueService
import TemplateElements._
import quizleague.web.maintain.text.TextService

import js.Dynamic.{global => g}
import quizleague.web.util.Logging
import quizleague.web.model.CompetitionType
import quizleague.web.maintain.competition.CompetitionService
import quizleague.web.maintain.component.ItemComponent
import quizleague.web.maintain.component.ItemComponentConfig
import quizleague.web.core.RouteComponent
import quizleague.web.util.component.SelectUtils


//@Component(
//  selector = "ql-season",
//  template = s"""
//  <div>
//    <h2>Calendar {{item.startYear}}/{{item.endYear}}</h2>
//    <form #fm="ngForm" (submit)="save()">
//      <div fxLayout="column">
//        <button md-icon-button (click)="addEvent()" type="button"><md-icon>mdi-plus</md-icon></button>
//        <div *ngFor="let event of items;let i = index" fxLayout="row">
//          <div fxLayout="column">
//            <button md-icon-button (click)="deleteEvent(event)" type="button"><md-icon>delete</md-icon></button>
//          </div>  
//          <div fxLayout="column">
//          <md-input-container>        
//            <input mdInput placeholder="Description" type="text" 
//            required
//               [(ngModel)]="event.description" name="description{{i}}">
//          </md-input-container>
//
//          <div fxLayout="row">
//            <md-input-container>        
//              <input mdInput placeholder="Date" type="date"
//                 required
//                 [(ngModel)]="event.date" name="date{{i}}">
//            </md-input-container>
//            <md-input-container>        
//              <input mdInput placeholder="Time" type="time"
//                 required
//                 [(ngModel)]="event.time" name="time{{i}}">
//            </md-input-container>
//            <md-input-container>        
//              <input mdInput placeholder="Duration" type="number" step="0.1"
//                 required
//                 [(ngModel)]="event.duration" name="duration{{i}}">
//            </md-input-container>
//          </div>
//            <md-select placeholder="Venue" name="venue{{i}}" [(ngModel)]="event.venue" required >
//              <md-option *ngFor="let venue of venues" [value]="venue" >
//                {{venue.name}}
//              </md-option>
//            </md-select>
//        </div>
//        </div>
//      </div>
//     $formButtons
//    </form>
//  </div>
//  """    
//)
object CalendarComponent extends ItemComponentConfig[Season] with RouteComponent{

  val service = SeasonService
  def parentKey(c:facade) = null
    
   
  val template = s"""
  <v-container v-if="item">
    <h2>Calendar {{item.startYear}}/{{item.endYear}}</h2>
    <v-form v-model="valid" >
      <v-layout column>
        <v-btn icon v-on:click="addEvent()"><v-icon>mdi-plus</v-icon></v-btn>
        <div v-for="event in item.calendar" :key="event.id">
          <v-layout column>
            <v-layout row>
             <v-btn icon v-on:click="deleteEvent(event)"><v-icon>mdi-delete</v-icon></v-btn>
             <v-text-field  label="Description" type="text" 
              required
                 v-model="event.description" ></v-text-field>
            </v-layout>
            <v-layout row>
              <v-text-field label="Date" v-model="event.date" type="date" required :rules=${valRequired("Date")}></v-text-field>
              <v-text-field label="Time" v-model="event.time" type="time" required :rules=${valRequired("Time")}></v-text-field>
              <v-text-field label="Duration" v-model.number="event.duration" type="number" step="0.5" required :rules=${valRequired("Duration")}></v-text-field>
            </v-layout>
            <v-select label="Venue" :items="venues" v-model="event.venue"></v-select>
          </v-layout>
        </div>
      </v-layout>
     $formButtons
    </v-form>
  </v-container>
  """    
    def venues() = SelectUtils.model[Venue](VenueService)(_.name)
     
    def addEvent(c:facade) = c.item.calendar += CalendarEvent(null,null,null,0,null)
    def deleteEvent(c:facade, event:CalendarEvent) = c.item.calendar -= event
    
    override def save(c:facade) = {service.cache(c.item);c.$router.back()}
    
    subscription("venues"){c:facade => venues()}

    method("addEvent")({addEvent _}:js.ThisFunction)
    method("deleteEvent")({deleteEvent _}:js.ThisFunction)
    
}
    