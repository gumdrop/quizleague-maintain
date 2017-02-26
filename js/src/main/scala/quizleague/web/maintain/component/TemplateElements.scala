package quizleague.web.maintain.component

object TemplateElements {
  val chbxRetired = """<md-checkbox [(ngModel)]="item.retired" name="retired">Retired</md-checkbox>"""
  
  val formButtons = """
      <div style="position:absolute;left:1em;bottom:2em;">
        <button md-fab type="submit" [disabled]="!fm.form.valid">
            <md-icon class="md-24">save</md-icon>
        </button>
      </div>
      <div style="position:absolute;right:1em;bottom:2em;">
        <button md-fab (click)="cancel()" type="button">
            <md-icon class="md-24">cancel</md-icon>
        </button>
      </div>
"""
  
  val addFAB = """
    <div style="position:absolute;right:1em;bottom:2em;">
      <button md-fab (click)="addNew()">
          <md-icon class="md-24">add</md-icon>
      </button>
    </div>
"""
}