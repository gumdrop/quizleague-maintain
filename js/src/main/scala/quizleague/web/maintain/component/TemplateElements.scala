package quizleague.web.maintain.component

object TemplateElements {
  val chbxRetired = """<md-checkbox [(ngModel)]="item.retired" name="retired">Retired</md-checkbox>"""
  
  val formButtons = """
      <div fxLayout="row">
        <button md-button type="submit" [disabled]="!fm.form.valid">Save</button>
        <button md-button (click)="cancel()" type="button">Cancel</button>
      </div>"""
  
  val addFAB = """
    <div style="position:absolute;right:1em;bottom:5em;">
      <button md-fab (click)="addNew()">
          <md-icon class="md-24">add</md-icon>
      </button>
    </div>
"""
}