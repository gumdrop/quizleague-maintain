require("core-js/es7/reflect")
require("zone.js/dist/zone")

var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
  var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
  if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
  else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
  return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
  if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
var core = require('@angular/core');
      
var s = require('./quizleague-js-fastopt.js');

s.quizleague.web.site.venue.VenueMenuComponent = __decorate(s.quizleague.web.site.venue.VenueMenuComponent_()._decorators.concat([__metadata('design:paramtypes',[s.quizleague.web.site.venue.VenueService])]),s.quizleague.web.site.venue.VenueMenuComponent);

s.quizleague.web.site.competition.CompetitionsTitleComponent = __decorate(s.quizleague.web.site.competition.CompetitionsTitleComponent_()._decorators.concat([__metadata('design:paramtypes',[s.quizleague.web.site.competition.CompetitionViewService])]),s.quizleague.web.site.competition.CompetitionsTitleComponent);

s.quizleague.web.site.leaguetable.LeagueTableService = __decorate(s.quizleague.web.site.leaguetable.LeagueTableService_()._decorators.concat([__metadata('design:paramtypes',[require('@angular/http').Http,s.quizleague.web.site.team.TeamService])]),s.quizleague.web.site.leaguetable.LeagueTableService);

s.quizleague.web.site.competition.BeerCompetitionComponent = __decorate(s.quizleague.web.site.competition.BeerCompetitionComponent_()._decorators.concat([__metadata('design:paramtypes',[require('@angular/router').ActivatedRoute,s.quizleague.web.site.competition.CompetitionService,s.quizleague.web.site.competition.CompetitionViewService,s.quizleague.web.site.global.ApplicationContextService,s.quizleague.web.site.common.TitleService,s.quizleague.web.site.common.SideMenuService])]),s.quizleague.web.site.competition.BeerCompetitionComponent);

s.quizleague.web.site.competition.CompetitionTitleComponent = __decorate(s.quizleague.web.site.competition.CompetitionTitleComponent_()._decorators.concat([__metadata('design:paramtypes',[require('@angular/router').ActivatedRoute,s.quizleague.web.site.competition.CompetitionService,s.quizleague.web.site.competition.CompetitionViewService])]),s.quizleague.web.site.competition.CompetitionTitleComponent);

s.quizleague.web.site.results.ResultsModule = __decorate(s.quizleague.web.site.results.ResultsModule_()._decorators,s.quizleague.web.site.results.ResultsModule);

s.quizleague.web.maintain.venue.VenueService = __decorate(s.quizleague.web.maintain.venue.VenueService_()._decorators.concat([__metadata('design:paramtypes',[require('@angular/http').Http])]),s.quizleague.web.maintain.venue.VenueService);

s.quizleague.web.maintain.season.SeasonComponent = __decorate(s.quizleague.web.maintain.season.SeasonComponent_()._decorators.concat([__metadata('design:paramtypes',[s.quizleague.web.maintain.season.SeasonService,require('@angular/router').ActivatedRoute,require('@angular/common').Location,require('@angular/router').Router,s.quizleague.web.maintain.competition.CompetitionService])]),s.quizleague.web.maintain.season.SeasonComponent);

s.quizleague.web.maintain.RootComponent = __decorate(s.quizleague.web.maintain.RootComponent_()._decorators,s.quizleague.web.maintain.RootComponent);

s.quizleague.web.site.fixtures.FixtureService = __decorate(s.quizleague.web.site.fixtures.FixtureService_()._decorators.concat([__metadata('design:paramtypes',[require('@angular/http').Http,s.quizleague.web.site.venue.VenueService,s.quizleague.web.site.team.TeamService])]),s.quizleague.web.site.fixtures.FixtureService);

s.quizleague.web.site.competition.CompetitionService = __decorate(s.quizleague.web.site.competition.CompetitionService_()._decorators.concat([__metadata('design:paramtypes',[require('@angular/http').Http,s.quizleague.web.site.text.TextService,s.quizleague.web.site.results.ResultsService,s.quizleague.web.site.fixtures.FixturesService,s.quizleague.web.site.leaguetable.LeagueTableService,s.quizleague.web.site.venue.VenueService])]),s.quizleague.web.site.competition.CompetitionService);

s.quizleague.web.site.leaguetable.LeagueTableModule = __decorate(s.quizleague.web.site.leaguetable.LeagueTableModule_()._decorators,s.quizleague.web.site.leaguetable.LeagueTableModule);

s.quizleague.web.maintain.globaltext.GlobalTextRoutesModule = __decorate(s.quizleague.web.maintain.globaltext.GlobalTextRoutesModule_()._decorators,s.quizleague.web.maintain.globaltext.GlobalTextRoutesModule);

s.quizleague.web.site.fixtures.FixturesComponentsModule = __decorate(s.quizleague.web.site.fixtures.FixturesComponentsModule_()._decorators,s.quizleague.web.site.fixtures.FixturesComponentsModule);

s.quizleague.web.maintain.user.UserRoutesModule = __decorate(s.quizleague.web.maintain.user.UserRoutesModule_()._decorators,s.quizleague.web.maintain.user.UserRoutesModule);
__decorate([core.Input(),__metadata('design:type',Object)],s.quizleague.web.site.fixtures.SimpleFixturesComponent.prototype,'fixtures',null);
__decorate([core.Input(),__metadata('design:type',Object)],s.quizleague.web.site.fixtures.SimpleFixturesComponent.prototype,'inlineDetails',null);
s.quizleague.web.site.fixtures.SimpleFixturesComponent = __decorate(s.quizleague.web.site.fixtures.SimpleFixturesComponent_()._decorators,s.quizleague.web.site.fixtures.SimpleFixturesComponent);
__decorate([core.Input(),__metadata('design:type',Object)],s.quizleague.web.site.season.SeasonSelectComponent.prototype,'currentSeason',null);
__decorate([core.Output(),__metadata('design:type',Object)],s.quizleague.web.site.season.SeasonSelectComponent.prototype,'onchange',null);
s.quizleague.web.site.season.SeasonSelectComponent = __decorate(s.quizleague.web.site.season.SeasonSelectComponent_()._decorators.concat([__metadata('design:paramtypes',[s.quizleague.web.site.season.SeasonService])]),s.quizleague.web.site.season.SeasonSelectComponent);

s.quizleague.web.maintain.fixtures.FixturesListComponent = __decorate(s.quizleague.web.maintain.fixtures.FixturesListComponent_()._decorators.concat([__metadata('design:paramtypes',[s.quizleague.web.maintain.fixtures.FixturesService,s.quizleague.web.maintain.competition.CompetitionService,require('@angular/router').ActivatedRoute,require('@angular/common').Location,require('@angular/router').Router])]),s.quizleague.web.maintain.fixtures.FixturesListComponent);

s.quizleague.web.site.common.SectionTitleComponent = __decorate(s.quizleague.web.site.common.SectionTitleComponent_()._decorators,s.quizleague.web.site.common.SectionTitleComponent);

s.quizleague.web.site.team.TeamFixturesComponent = __decorate(s.quizleague.web.site.team.TeamFixturesComponent_()._decorators.concat([__metadata('design:paramtypes',[require('@angular/router').ActivatedRoute,s.quizleague.web.site.team.TeamService,s.quizleague.web.site.team.TeamViewService,s.quizleague.web.site.global.ApplicationContextService,s.quizleague.web.site.common.TitleService,s.quizleague.web.site.common.SideMenuService])]),s.quizleague.web.site.team.TeamFixturesComponent);

s.quizleague.web.maintain.globaltext.GlobalTextService = __decorate(s.quizleague.web.maintain.globaltext.GlobalTextService_()._decorators.concat([__metadata('design:paramtypes',[require('@angular/http').Http,s.quizleague.web.maintain.text.TextService])]),s.quizleague.web.maintain.globaltext.GlobalTextService);

s.quizleague.web.site.fixtures.FixturesModule = __decorate(s.quizleague.web.site.fixtures.FixturesModule_()._decorators,s.quizleague.web.site.fixtures.FixturesModule);

s.quizleague.web.maintain.globaltext.GlobalTextListComponent = __decorate(s.quizleague.web.maintain.globaltext.GlobalTextListComponent_()._decorators.concat([__metadata('design:paramtypes',[s.quizleague.web.maintain.globaltext.GlobalTextService,require('@angular/router').Router])]),s.quizleague.web.maintain.globaltext.GlobalTextListComponent);

s.quizleague.web.maintain.user.UserListComponent = __decorate(s.quizleague.web.maintain.user.UserListComponent_()._decorators.concat([__metadata('design:paramtypes',[s.quizleague.web.maintain.user.UserService,require('@angular/router').Router])]),s.quizleague.web.maintain.user.UserListComponent);

s.quizleague.web.maintain.results.ResultsListComponent = __decorate(s.quizleague.web.maintain.results.ResultsListComponent_()._decorators.concat([__metadata('design:paramtypes',[s.quizleague.web.maintain.results.ResultsService,s.quizleague.web.maintain.competition.CompetitionService,require('@angular/router').ActivatedRoute,require('@angular/common').Location,require('@angular/router').Router])]),s.quizleague.web.maintain.results.ResultsListComponent);

s.quizleague.web.site.team.TeamModule = __decorate(s.quizleague.web.site.team.TeamModule_()._decorators,s.quizleague.web.site.team.TeamModule);

s.quizleague.web.site.venue.VenueTitleComponent = __decorate(s.quizleague.web.site.venue.VenueTitleComponent_()._decorators.concat([__metadata('design:paramtypes',[require('@angular/router').ActivatedRoute,s.quizleague.web.site.venue.VenueService])]),s.quizleague.web.site.venue.VenueTitleComponent);

s.quizleague.web.site.competition.CompetitionMenuComponent = __decorate(s.quizleague.web.site.competition.CompetitionMenuComponent_()._decorators.concat([__metadata('design:paramtypes',[s.quizleague.web.site.competition.CompetitionService,s.quizleague.web.site.competition.CompetitionViewService,s.quizleague.web.site.season.SeasonService])]),s.quizleague.web.site.competition.CompetitionMenuComponent);

s.quizleague.web.maintain.globaltext.GlobalTextModule = __decorate(s.quizleague.web.maintain.globaltext.GlobalTextModule_()._decorators,s.quizleague.web.maintain.globaltext.GlobalTextModule);

s.quizleague.web.site.common.TitleService = __decorate(s.quizleague.web.site.common.TitleService_()._decorators.concat([__metadata('design:paramtypes',[s.quizleague.web.site.global.ApplicationContextService,require('@angular/platform-browser').Title])]),s.quizleague.web.site.common.TitleService);

s.quizleague.web.site.calendar.CalendarModule = __decorate(s.quizleague.web.site.calendar.CalendarModule_()._decorators,s.quizleague.web.site.calendar.CalendarModule);

s.quizleague.web.site.season.SeasonModule = __decorate(s.quizleague.web.site.season.SeasonModule_()._decorators,s.quizleague.web.site.season.SeasonModule);

s.quizleague.web.site.global.ApplicationContextModule = __decorate(s.quizleague.web.site.global.ApplicationContextModule_()._decorators,s.quizleague.web.site.global.ApplicationContextModule);

s.quizleague.web.maintain.leaguetable.LeagueTableListComponent = __decorate(s.quizleague.web.maintain.leaguetable.LeagueTableListComponent_()._decorators.concat([__metadata('design:paramtypes',[s.quizleague.web.maintain.leaguetable.LeagueTableService,s.quizleague.web.maintain.competition.CompetitionService,require('@angular/router').ActivatedRoute,require('@angular/common').Location,require('@angular/router').Router])]),s.quizleague.web.maintain.leaguetable.LeagueTableListComponent);

s.quizleague.web.site.global.ApplicationContextService = __decorate(s.quizleague.web.site.global.ApplicationContextService_()._decorators.concat([__metadata('design:paramtypes',[require('@angular/http').Http,s.quizleague.web.site.text.GlobalTextService,s.quizleague.web.site.user.UserService,s.quizleague.web.site.season.SeasonService])]),s.quizleague.web.site.global.ApplicationContextService);

s.quizleague.web.maintain.applicationcontext.ApplicationContextComponent = __decorate(s.quizleague.web.maintain.applicationcontext.ApplicationContextComponent_()._decorators.concat([__metadata('design:paramtypes',[s.quizleague.web.maintain.applicationcontext.ApplicationContextService,require('@angular/router').ActivatedRoute,require('@angular/common').Location,require('@angular/router').Router])]),s.quizleague.web.maintain.applicationcontext.ApplicationContextComponent);

s.quizleague.web.maintain.globaltext.GlobalTextComponent = __decorate(s.quizleague.web.maintain.globaltext.GlobalTextComponent_()._decorators.concat([__metadata('design:paramtypes',[s.quizleague.web.maintain.globaltext.GlobalTextService,require('@angular/router').ActivatedRoute,require('@angular/common').Location,require('@angular/router').Router])]),s.quizleague.web.maintain.globaltext.GlobalTextComponent);

s.quizleague.web.maintain.competition.CupCompetitionComponent = __decorate(s.quizleague.web.maintain.competition.CupCompetitionComponent_()._decorators.concat([__metadata('design:paramtypes',[s.quizleague.web.maintain.competition.CompetitionService,require('@angular/common').Location,require('@angular/router').ActivatedRoute,require('@angular/router').Router])]),s.quizleague.web.maintain.competition.CupCompetitionComponent);

s.quizleague.web.maintain.results.ResultService = __decorate(s.quizleague.web.maintain.results.ResultService_()._decorators.concat([__metadata('design:paramtypes',[require('@angular/http').Http,s.quizleague.web.maintain.fixtures.FixtureService,s.quizleague.web.maintain.team.TeamService,s.quizleague.web.maintain.user.UserService,s.quizleague.web.maintain.text.TextService])]),s.quizleague.web.maintain.results.ResultService);

s.quizleague.web.maintain.competition.SubsidiaryCompetitionComponent = __decorate(s.quizleague.web.maintain.competition.SubsidiaryCompetitionComponent_()._decorators.concat([__metadata('design:paramtypes',[s.quizleague.web.maintain.competition.CompetitionService,require('@angular/common').Location,require('@angular/router').ActivatedRoute,require('@angular/router').Router])]),s.quizleague.web.maintain.competition.SubsidiaryCompetitionComponent);

s.quizleague.web.site.results.ResultsRoutesModule = __decorate(s.quizleague.web.site.results.ResultsRoutesModule_()._decorators,s.quizleague.web.site.results.ResultsRoutesModule);

s.quizleague.web.maintain.AppComponent = __decorate(s.quizleague.web.maintain.AppComponent_()._decorators,s.quizleague.web.maintain.AppComponent);

s.quizleague.web.site.venue.VenuesTitleComponent = __decorate(s.quizleague.web.site.venue.VenuesTitleComponent_()._decorators,s.quizleague.web.site.venue.VenuesTitleComponent);
__decorate([core.Input(),__metadata('design:type',String)],s.quizleague.web.site.text.NamedTextComponent.prototype,'name',null);
s.quizleague.web.site.text.NamedTextComponent = __decorate(s.quizleague.web.site.text.NamedTextComponent_()._decorators.concat([__metadata('design:paramtypes',[s.quizleague.web.site.global.ApplicationContextService])]),s.quizleague.web.site.text.NamedTextComponent);

s.quizleague.web.site.competition.CompetitionRoutesModule = __decorate(s.quizleague.web.site.competition.CompetitionRoutesModule_()._decorators,s.quizleague.web.site.competition.CompetitionRoutesModule);

s.quizleague.web.site.results.ResultsViewService = __decorate(s.quizleague.web.site.results.ResultsViewService_()._decorators.concat([__metadata('design:paramtypes',[s.quizleague.web.site.global.ApplicationContextService])]),s.quizleague.web.site.results.ResultsViewService);

s.quizleague.web.maintain.results.ResultsModule = __decorate(s.quizleague.web.maintain.results.ResultsModule_()._decorators,s.quizleague.web.maintain.results.ResultsModule);

s.quizleague.web.site.competition.CompetitionFixturesComponent = __decorate(s.quizleague.web.site.competition.CompetitionFixturesComponent_()._decorators.concat([__metadata('design:paramtypes',[require('@angular/router').ActivatedRoute,s.quizleague.web.site.competition.CompetitionService,s.quizleague.web.site.competition.CompetitionViewService,s.quizleague.web.site.global.ApplicationContextService,s.quizleague.web.site.common.TitleService,s.quizleague.web.site.common.SideMenuService])]),s.quizleague.web.site.competition.CompetitionFixturesComponent);

s.quizleague.web.maintain.venue.VenueRoutesModule = __decorate(s.quizleague.web.maintain.venue.VenueRoutesModule_()._decorators,s.quizleague.web.maintain.venue.VenueRoutesModule);

s.quizleague.web.maintain.fixtures.FixturesService = __decorate(s.quizleague.web.maintain.fixtures.FixturesService_()._decorators.concat([__metadata('design:paramtypes',[require('@angular/http').Http,s.quizleague.web.maintain.fixtures.FixtureService])]),s.quizleague.web.maintain.fixtures.FixturesService);

s.quizleague.web.site.user.UserService = __decorate(s.quizleague.web.site.user.UserService_()._decorators.concat([__metadata('design:paramtypes',[require('@angular/http').Http])]),s.quizleague.web.site.user.UserService);

s.quizleague.web.maintain.DepsModule = __decorate(s.quizleague.web.maintain.DepsModule_()._decorators,s.quizleague.web.maintain.DepsModule);

s.quizleague.web.maintain.leaguetable.LeagueTableService = __decorate(s.quizleague.web.maintain.leaguetable.LeagueTableService_()._decorators.concat([__metadata('design:paramtypes',[require('@angular/http').Http,s.quizleague.web.maintain.team.TeamService,s.quizleague.web.maintain.results.ResultsService])]),s.quizleague.web.maintain.leaguetable.LeagueTableService);
__decorate([core.Input(),__metadata('design:type',Object)],s.quizleague.web.site.season.SeasonNameComponent.prototype,'season',null);
s.quizleague.web.site.season.SeasonNameComponent = __decorate(s.quizleague.web.site.season.SeasonNameComponent_()._decorators,s.quizleague.web.site.season.SeasonNameComponent);

s.quizleague.web.maintain.text.TextService = __decorate(s.quizleague.web.maintain.text.TextService_()._decorators.concat([__metadata('design:paramtypes',[require('@angular/http').Http])]),s.quizleague.web.maintain.text.TextService);

s.quizleague.web.maintain.venue.VenueListComponent = __decorate(s.quizleague.web.maintain.venue.VenueListComponent_()._decorators.concat([__metadata('design:paramtypes',[s.quizleague.web.maintain.venue.VenueService,require('@angular/router').Router])]),s.quizleague.web.maintain.venue.VenueListComponent);

s.quizleague.web.maintain.venue.VenueModule = __decorate(s.quizleague.web.maintain.venue.VenueModule_()._decorators,s.quizleague.web.maintain.venue.VenueModule);

s.quizleague.web.site.root.RootModule = __decorate(s.quizleague.web.site.root.RootModule_()._decorators,s.quizleague.web.site.root.RootModule);

s.quizleague.web.site.common.CommonAppModule = __decorate(s.quizleague.web.site.common.CommonAppModule_()._decorators,s.quizleague.web.site.common.CommonAppModule);

s.quizleague.web.site.team.TeamsComponent = __decorate(s.quizleague.web.site.team.TeamsComponent_()._decorators.concat([__metadata('design:paramtypes',[s.quizleague.web.site.common.SideMenuService,s.quizleague.web.site.common.TitleService])]),s.quizleague.web.site.team.TeamsComponent);

s.quizleague.web.site.competition.CompetitionResultsComponent = __decorate(s.quizleague.web.site.competition.CompetitionResultsComponent_()._decorators.concat([__metadata('design:paramtypes',[require('@angular/router').ActivatedRoute,s.quizleague.web.site.competition.CompetitionService,s.quizleague.web.site.competition.CompetitionViewService,s.quizleague.web.site.global.ApplicationContextService,s.quizleague.web.site.common.TitleService,s.quizleague.web.site.common.SideMenuService])]),s.quizleague.web.site.competition.CompetitionResultsComponent);

s.quizleague.web.site.team.TeamService = __decorate(s.quizleague.web.site.team.TeamService_()._decorators.concat([__metadata('design:paramtypes',[require('@angular/http').Http,s.quizleague.web.site.text.TextService,s.quizleague.web.site.venue.VenueService,s.quizleague.web.site.user.UserService])]),s.quizleague.web.site.team.TeamService);

s.quizleague.web.maintain.results.ResultsService = __decorate(s.quizleague.web.maintain.results.ResultsService_()._decorators.concat([__metadata('design:paramtypes',[require('@angular/http').Http,s.quizleague.web.maintain.fixtures.FixturesService,s.quizleague.web.maintain.results.ResultService])]),s.quizleague.web.maintain.results.ResultsService);

s.quizleague.web.maintain.results.ReportListComponent = __decorate(s.quizleague.web.maintain.results.ReportListComponent_()._decorators.concat([__metadata('design:paramtypes',[s.quizleague.web.maintain.results.ResultService,require('@angular/router').ActivatedRoute,require('@angular/common').Location,require('@angular/router').Router])]),s.quizleague.web.maintain.results.ReportListComponent);

s.quizleague.web.site.fixtures.FixturesService = __decorate(s.quizleague.web.site.fixtures.FixturesService_()._decorators.concat([__metadata('design:paramtypes',[require('@angular/http').Http,s.quizleague.web.site.fixtures.FixtureService])]),s.quizleague.web.site.fixtures.FixturesService);

s.quizleague.web.maintain.applicationcontext.ApplicationContextService = __decorate(s.quizleague.web.maintain.applicationcontext.ApplicationContextService_()._decorators.concat([__metadata('design:paramtypes',[require('@angular/http').Http,s.quizleague.web.maintain.user.UserService,s.quizleague.web.maintain.globaltext.GlobalTextService,s.quizleague.web.maintain.season.SeasonService])]),s.quizleague.web.maintain.applicationcontext.ApplicationContextService);
__decorate([core.Input(),__metadata('design:type',Object)],s.quizleague.web.maintain.text.TextEditor.prototype,'text',null);
__decorate([core.Input(),__metadata('design:type',Boolean)],s.quizleague.web.maintain.text.TextEditor.prototype,'required',null);
s.quizleague.web.maintain.text.TextEditor = __decorate(s.quizleague.web.maintain.text.TextEditor_()._decorators,s.quizleague.web.maintain.text.TextEditor);

s.quizleague.web.maintain.competition.CompetitionService = __decorate(s.quizleague.web.maintain.competition.CompetitionService_()._decorators.concat([__metadata('design:paramtypes',[require('@angular/http').Http,s.quizleague.web.maintain.text.TextService,s.quizleague.web.maintain.results.ResultsService,s.quizleague.web.maintain.fixtures.FixturesService,s.quizleague.web.maintain.leaguetable.LeagueTableService,s.quizleague.web.maintain.venue.VenueService])]),s.quizleague.web.maintain.competition.CompetitionService);

s.quizleague.web.maintain.fixtures.FixturesRoutesModule = __decorate(s.quizleague.web.maintain.fixtures.FixturesRoutesModule_()._decorators,s.quizleague.web.maintain.fixtures.FixturesRoutesModule);

s.quizleague.web.site.team.TeamResultsTitleComponent = __decorate(s.quizleague.web.site.team.TeamResultsTitleComponent_()._decorators,s.quizleague.web.site.team.TeamResultsTitleComponent);

s.quizleague.web.maintain.results.ResultsComponent = __decorate(s.quizleague.web.maintain.results.ResultsComponent_()._decorators.concat([__metadata('design:paramtypes',[s.quizleague.web.maintain.results.ResultsService,s.quizleague.web.maintain.results.ResultService,require('@angular/router').ActivatedRoute,require('@angular/common').Location,require('@angular/router').Router])]),s.quizleague.web.maintain.results.ResultsComponent);

s.quizleague.web.site.competition.SingletonCompetitionComponent = __decorate(s.quizleague.web.site.competition.SingletonCompetitionComponent_()._decorators.concat([__metadata('design:paramtypes',[require('@angular/router').ActivatedRoute,s.quizleague.web.site.competition.CompetitionService,s.quizleague.web.site.competition.CompetitionViewService,s.quizleague.web.site.global.ApplicationContextService,s.quizleague.web.site.common.TitleService,s.quizleague.web.site.common.SideMenuService])]),s.quizleague.web.site.competition.SingletonCompetitionComponent);

s.quizleague.web.site.results.ResultsMenuComponent = __decorate(s.quizleague.web.site.results.ResultsMenuComponent_()._decorators,s.quizleague.web.site.results.ResultsMenuComponent);

s.quizleague.web.site.AppComponent = __decorate(s.quizleague.web.site.AppComponent_()._decorators.concat([__metadata('design:paramtypes',[s.quizleague.web.site.global.ApplicationContextService,s.quizleague.web.site.common.SideMenuService])]),s.quizleague.web.site.AppComponent);

s.quizleague.web.site.team.TeamViewService = __decorate(s.quizleague.web.site.team.TeamViewService_()._decorators.concat([__metadata('design:paramtypes',[s.quizleague.web.site.team.TeamService,s.quizleague.web.site.season.SeasonService,s.quizleague.web.site.global.ApplicationContextService])]),s.quizleague.web.site.team.TeamViewService);

s.quizleague.web.site.common.LineBreakPipe = __decorate(s.quizleague.web.site.common.LineBreakPipe_()._decorators,s.quizleague.web.site.common.LineBreakPipe);

s.quizleague.web.site.team.TeamComponent = __decorate(s.quizleague.web.site.team.TeamComponent_()._decorators.concat([__metadata('design:paramtypes',[require('@angular/router').ActivatedRoute,s.quizleague.web.site.team.TeamService,s.quizleague.web.site.team.TeamViewService,s.quizleague.web.site.global.ApplicationContextService,s.quizleague.web.site.common.TitleService,s.quizleague.web.site.common.SideMenuService])]),s.quizleague.web.site.team.TeamComponent);

s.quizleague.web.maintain.leaguetable.LeagueTableModule = __decorate(s.quizleague.web.maintain.leaguetable.LeagueTableModule_()._decorators,s.quizleague.web.maintain.leaguetable.LeagueTableModule);

s.quizleague.web.maintain.applicationcontext.ApplicationContextModule = __decorate(s.quizleague.web.maintain.applicationcontext.ApplicationContextModule_()._decorators,s.quizleague.web.maintain.applicationcontext.ApplicationContextModule);

s.quizleague.web.maintain.season.CalendarComponent = __decorate(s.quizleague.web.maintain.season.CalendarComponent_()._decorators.concat([__metadata('design:paramtypes',[s.quizleague.web.maintain.season.SeasonService,require('@angular/router').ActivatedRoute,require('@angular/common').Location,s.quizleague.web.maintain.venue.VenueService])]),s.quizleague.web.maintain.season.CalendarComponent);

s.quizleague.web.maintain.fixtures.FixtureService = __decorate(s.quizleague.web.maintain.fixtures.FixtureService_()._decorators.concat([__metadata('design:paramtypes',[require('@angular/http').Http,s.quizleague.web.maintain.venue.VenueService,s.quizleague.web.maintain.team.TeamService])]),s.quizleague.web.maintain.fixtures.FixtureService);

s.quizleague.web.maintain.fixtures.FixturesComponent = __decorate(s.quizleague.web.maintain.fixtures.FixturesComponent_()._decorators.concat([__metadata('design:paramtypes',[s.quizleague.web.maintain.fixtures.FixturesService,require('@angular/router').ActivatedRoute,require('@angular/common').Location,require('@angular/router').Router,s.quizleague.web.maintain.competition.CompetitionService,s.quizleague.web.maintain.fixtures.FixtureService,s.quizleague.web.maintain.team.TeamService,s.quizleague.web.maintain.venue.VenueService])]),s.quizleague.web.maintain.fixtures.FixturesComponent);

s.quizleague.web.maintain.season.SeasonModule = __decorate(s.quizleague.web.maintain.season.SeasonModule_()._decorators,s.quizleague.web.maintain.season.SeasonModule);

s.quizleague.web.site.team.TeamRoutesModule = __decorate(s.quizleague.web.site.team.TeamRoutesModule_()._decorators,s.quizleague.web.site.team.TeamRoutesModule);

s.quizleague.web.maintain.competition.CompetitionModule = __decorate(s.quizleague.web.maintain.competition.CompetitionModule_()._decorators,s.quizleague.web.maintain.competition.CompetitionModule);

s.quizleague.web.site.text.GlobalTextService = __decorate(s.quizleague.web.site.text.GlobalTextService_()._decorators.concat([__metadata('design:paramtypes',[require('@angular/http').Http,s.quizleague.web.site.text.TextService])]),s.quizleague.web.site.text.GlobalTextService);

s.quizleague.web.site.competition.LeagueCompetitionComponent = __decorate(s.quizleague.web.site.competition.LeagueCompetitionComponent_()._decorators.concat([__metadata('design:paramtypes',[require('@angular/router').ActivatedRoute,s.quizleague.web.site.competition.CompetitionService,s.quizleague.web.site.competition.CompetitionViewService,s.quizleague.web.site.global.ApplicationContextService,s.quizleague.web.site.common.TitleService,s.quizleague.web.site.common.SideMenuService])]),s.quizleague.web.site.competition.LeagueCompetitionComponent);

s.quizleague.web.maintain.AppRoutingModule = __decorate(s.quizleague.web.maintain.AppRoutingModule_()._decorators,s.quizleague.web.maintain.AppRoutingModule);

s.quizleague.web.site.calendar.CalendarComponent = __decorate(s.quizleague.web.site.calendar.CalendarComponent_()._decorators.concat([__metadata('design:paramtypes',[s.quizleague.web.site.calendar.CalendarViewService,s.quizleague.web.site.common.TitleService,s.quizleague.web.site.common.SideMenuService])]),s.quizleague.web.site.calendar.CalendarComponent);

s.quizleague.web.maintain.text.TextModule = __decorate(s.quizleague.web.maintain.text.TextModule_()._decorators,s.quizleague.web.maintain.text.TextModule);

s.quizleague.web.maintain.fixtures.FixturesModule = __decorate(s.quizleague.web.maintain.fixtures.FixturesModule_()._decorators,s.quizleague.web.maintain.fixtures.FixturesModule);

s.quizleague.web.site.results.AllResultsTitleComponent = __decorate(s.quizleague.web.site.results.AllResultsTitleComponent_()._decorators.concat([__metadata('design:paramtypes',[s.quizleague.web.site.results.ResultsViewService])]),s.quizleague.web.site.results.AllResultsTitleComponent);

s.quizleague.web.site.competition.CompetitionFixturesTitleComponent = __decorate(s.quizleague.web.site.competition.CompetitionFixturesTitleComponent_()._decorators.concat([__metadata('design:paramtypes',[require('@angular/router').ActivatedRoute,s.quizleague.web.site.competition.CompetitionService])]),s.quizleague.web.site.competition.CompetitionFixturesTitleComponent);

s.quizleague.web.site.fixtures.AllFixturesComponent = __decorate(s.quizleague.web.site.fixtures.AllFixturesComponent_()._decorators.concat([__metadata('design:paramtypes',[require('@angular/router').ActivatedRoute,s.quizleague.web.site.season.SeasonService,s.quizleague.web.site.results.ResultsViewService,s.quizleague.web.site.common.SideMenuService,s.quizleague.web.site.common.TitleService])]),s.quizleague.web.site.fixtures.AllFixturesComponent);

s.quizleague.web.site.calendar.CalendarTitleComponent = __decorate(s.quizleague.web.site.calendar.CalendarTitleComponent_()._decorators.concat([__metadata('design:paramtypes',[require('@angular/router').ActivatedRoute,s.quizleague.web.site.calendar.CalendarViewService])]),s.quizleague.web.site.calendar.CalendarTitleComponent);

s.quizleague.web.site.competition.CompetitionResultsTitleComponent = __decorate(s.quizleague.web.site.competition.CompetitionResultsTitleComponent_()._decorators.concat([__metadata('design:paramtypes',[require('@angular/router').ActivatedRoute,s.quizleague.web.site.competition.CompetitionService])]),s.quizleague.web.site.competition.CompetitionResultsTitleComponent);

s.quizleague.web.maintain.competition.LeagueCompetitionComponent = __decorate(s.quizleague.web.maintain.competition.LeagueCompetitionComponent_()._decorators.concat([__metadata('design:paramtypes',[s.quizleague.web.maintain.competition.CompetitionService,require('@angular/common').Location,require('@angular/router').ActivatedRoute,require('@angular/router').Router,s.quizleague.web.maintain.season.SeasonService])]),s.quizleague.web.maintain.competition.LeagueCompetitionComponent);

s.quizleague.web.site.common.SideMenuService = __decorate(s.quizleague.web.site.common.SideMenuService_()._decorators.concat([__metadata('design:paramtypes',[require('@angular/flex-layout').ObservableMedia])]),s.quizleague.web.site.common.SideMenuService);

s.quizleague.web.site.ApplicationModules = __decorate(s.quizleague.web.site.ApplicationModules_()._decorators,s.quizleague.web.site.ApplicationModules);

s.quizleague.web.maintain.season.SeasonRoutesModule = __decorate(s.quizleague.web.maintain.season.SeasonRoutesModule_()._decorators,s.quizleague.web.maintain.season.SeasonRoutesModule);

s.quizleague.web.site.text.TextModule = __decorate(s.quizleague.web.site.text.TextModule_()._decorators,s.quizleague.web.site.text.TextModule);

s.quizleague.web.site.team.TeamResultsComponent = __decorate(s.quizleague.web.site.team.TeamResultsComponent_()._decorators.concat([__metadata('design:paramtypes',[require('@angular/router').ActivatedRoute,s.quizleague.web.site.team.TeamService,s.quizleague.web.site.team.TeamViewService,s.quizleague.web.site.global.ApplicationContextService,s.quizleague.web.site.common.TitleService,s.quizleague.web.site.common.SideMenuService])]),s.quizleague.web.site.team.TeamResultsComponent);

s.quizleague.web.maintain.team.TeamListComponent = __decorate(s.quizleague.web.maintain.team.TeamListComponent_()._decorators.concat([__metadata('design:paramtypes',[s.quizleague.web.maintain.team.TeamService,require('@angular/router').Router])]),s.quizleague.web.maintain.team.TeamListComponent);

s.quizleague.web.maintain.season.SeasonListComponent = __decorate(s.quizleague.web.maintain.season.SeasonListComponent_()._decorators.concat([__metadata('design:paramtypes',[s.quizleague.web.maintain.season.SeasonService,require('@angular/router').Router])]),s.quizleague.web.maintain.season.SeasonListComponent);

s.quizleague.web.site.team.TeamMenuComponent = __decorate(s.quizleague.web.site.team.TeamMenuComponent_()._decorators.concat([__metadata('design:paramtypes',[s.quizleague.web.site.team.TeamService])]),s.quizleague.web.site.team.TeamMenuComponent);

s.quizleague.web.maintain.text.TextRoutesModule = __decorate(s.quizleague.web.maintain.text.TextRoutesModule_()._decorators,s.quizleague.web.maintain.text.TextRoutesModule);

s.quizleague.web.site.root.RootComponent = __decorate(s.quizleague.web.site.root.RootComponent_()._decorators.concat([__metadata('design:paramtypes',[s.quizleague.web.site.common.SideMenuService,s.quizleague.web.site.common.TitleService,s.quizleague.web.site.global.ApplicationContextService,s.quizleague.web.site.season.SeasonService])]),s.quizleague.web.site.root.RootComponent);

s.quizleague.web.site.calendar.CalendarEventComponent = __decorate(s.quizleague.web.site.calendar.CalendarEventComponent_()._decorators,s.quizleague.web.site.calendar.CalendarEventComponent);
__decorate([core.Input(),__metadata('design:type',String)],s.quizleague.web.site.text.TextComponent.prototype,'textId',null);
s.quizleague.web.site.text.TextComponent = __decorate(s.quizleague.web.site.text.TextComponent_()._decorators.concat([__metadata('design:paramtypes',[s.quizleague.web.site.text.TextService])]),s.quizleague.web.site.text.TextComponent);
__decorate([core.Input('results'),__metadata('design:type',Object)],s.quizleague.web.site.results.SimpleResultsComponent.prototype,'results',null);
__decorate([core.Input(),__metadata('design:type',Object)],s.quizleague.web.site.results.SimpleResultsComponent.prototype,'inlineDetails',null);
s.quizleague.web.site.results.SimpleResultsComponent = __decorate(s.quizleague.web.site.results.SimpleResultsComponent_()._decorators,s.quizleague.web.site.results.SimpleResultsComponent);

s.quizleague.web.site.season.SeasonService = __decorate(s.quizleague.web.site.season.SeasonService_()._decorators.concat([__metadata('design:paramtypes',[require('@angular/http').Http,s.quizleague.web.site.text.TextService,s.quizleague.web.site.competition.CompetitionService,s.quizleague.web.site.venue.VenueService])]),s.quizleague.web.site.season.SeasonService);

s.quizleague.web.site.competition.CompetitionsComponent = __decorate(s.quizleague.web.site.competition.CompetitionsComponent_()._decorators.concat([__metadata('design:paramtypes',[s.quizleague.web.site.common.SideMenuService,s.quizleague.web.site.common.TitleService])]),s.quizleague.web.site.competition.CompetitionsComponent);

s.quizleague.web.site.venue.VenueRoutesModule = __decorate(s.quizleague.web.site.venue.VenueRoutesModule_()._decorators,s.quizleague.web.site.venue.VenueRoutesModule);

s.quizleague.web.maintain.user.UserModule = __decorate(s.quizleague.web.maintain.user.UserModule_()._decorators,s.quizleague.web.maintain.user.UserModule);

s.quizleague.web.maintain.user.UserComponent = __decorate(s.quizleague.web.maintain.user.UserComponent_()._decorators.concat([__metadata('design:paramtypes',[s.quizleague.web.maintain.user.UserService,require('@angular/router').ActivatedRoute,require('@angular/common').Location])]),s.quizleague.web.maintain.user.UserComponent);

s.quizleague.web.maintain.leaguetable.LeagueTableComponent = __decorate(s.quizleague.web.maintain.leaguetable.LeagueTableComponent_()._decorators.concat([__metadata('design:paramtypes',[s.quizleague.web.maintain.leaguetable.LeagueTableService,s.quizleague.web.maintain.team.TeamService,require('@angular/router').ActivatedRoute,require('@angular/common').Location,require('@angular/router').Router])]),s.quizleague.web.maintain.leaguetable.LeagueTableComponent);

s.quizleague.web.site.venue.VenueService = __decorate(s.quizleague.web.site.venue.VenueService_()._decorators.concat([__metadata('design:paramtypes',[require('@angular/http').Http])]),s.quizleague.web.site.venue.VenueService);

s.quizleague.web.maintain.applicationcontext.ApplicationContextRoutesModule = __decorate(s.quizleague.web.maintain.applicationcontext.ApplicationContextRoutesModule_()._decorators,s.quizleague.web.maintain.applicationcontext.ApplicationContextRoutesModule);

s.quizleague.web.site.user.UserModule = __decorate(s.quizleague.web.site.user.UserModule_()._decorators,s.quizleague.web.site.user.UserModule);

s.quizleague.web.maintain.competition.SingletonCompetitionComponent = __decorate(s.quizleague.web.maintain.competition.SingletonCompetitionComponent_()._decorators.concat([__metadata('design:paramtypes',[s.quizleague.web.maintain.competition.CompetitionService,require('@angular/common').Location,require('@angular/router').ActivatedRoute,require('@angular/router').Router,s.quizleague.web.maintain.venue.VenueService])]),s.quizleague.web.maintain.competition.SingletonCompetitionComponent);

s.quizleague.web.site.calendar.ResultsEventComponent = __decorate(s.quizleague.web.site.calendar.ResultsEventComponent_()._decorators,s.quizleague.web.site.calendar.ResultsEventComponent);

s.quizleague.web.site.venue.VenueModule = __decorate(s.quizleague.web.site.venue.VenueModule_()._decorators,s.quizleague.web.site.venue.VenueModule);

s.quizleague.web.site.calendar.CalendarRoutesModule = __decorate(s.quizleague.web.site.calendar.CalendarRoutesModule_()._decorators,s.quizleague.web.site.calendar.CalendarRoutesModule);

s.quizleague.web.maintain.AppModule = __decorate(s.quizleague.web.maintain.AppModule_()._decorators,s.quizleague.web.maintain.AppModule);

s.quizleague.web.site.text.TextService = __decorate(s.quizleague.web.site.text.TextService_()._decorators.concat([__metadata('design:paramtypes',[require('@angular/http').Http])]),s.quizleague.web.site.text.TextService);
__decorate([core.Input(),__metadata('design:type',String)],s.quizleague.web.site.team.TeamSubTitleComponent.prototype,'text',null);
s.quizleague.web.site.team.TeamSubTitleComponent = __decorate(s.quizleague.web.site.team.TeamSubTitleComponent_()._decorators.concat([__metadata('design:paramtypes',[require('@angular/router').ActivatedRoute,s.quizleague.web.site.team.TeamService,s.quizleague.web.site.team.TeamViewService])]),s.quizleague.web.site.team.TeamSubTitleComponent);

s.quizleague.web.site.competition.CompetitionViewService = __decorate(s.quizleague.web.site.competition.CompetitionViewService_()._decorators.concat([__metadata('design:paramtypes',[s.quizleague.web.site.global.ApplicationContextService])]),s.quizleague.web.site.competition.CompetitionViewService);

s.quizleague.web.site.venue.VenuesComponent = __decorate(s.quizleague.web.site.venue.VenuesComponent_()._decorators.concat([__metadata('design:paramtypes',[s.quizleague.web.site.common.TitleService,s.quizleague.web.site.common.SideMenuService])]),s.quizleague.web.site.venue.VenuesComponent);

s.quizleague.web.site.fixtures.AllFixturesTitleComponent = __decorate(s.quizleague.web.site.fixtures.AllFixturesTitleComponent_()._decorators.concat([__metadata('design:paramtypes',[s.quizleague.web.site.results.ResultsViewService])]),s.quizleague.web.site.fixtures.AllFixturesTitleComponent);

s.quizleague.web.maintain.team.TeamComponent = __decorate(s.quizleague.web.maintain.team.TeamComponent_()._decorators.concat([__metadata('design:paramtypes',[s.quizleague.web.maintain.team.TeamService,require('@angular/router').ActivatedRoute,require('@angular/common').Location,require('@angular/router').Router])]),s.quizleague.web.maintain.team.TeamComponent);

s.quizleague.web.site.results.ReportTitleComponent = __decorate(s.quizleague.web.site.results.ReportTitleComponent_()._decorators.concat([__metadata('design:paramtypes',[require('@angular/router').ActivatedRoute,s.quizleague.web.site.results.ResultService])]),s.quizleague.web.site.results.ReportTitleComponent);

s.quizleague.web.site.AppModule = __decorate(s.quizleague.web.site.AppModule_()._decorators.concat([__metadata('design:paramtypes',[require('@angular/router').Router])]),s.quizleague.web.site.AppModule);

s.quizleague.web.maintain.season.SeasonService = __decorate(s.quizleague.web.maintain.season.SeasonService_()._decorators.concat([__metadata('design:paramtypes',[require('@angular/http').Http,s.quizleague.web.maintain.text.TextService,s.quizleague.web.maintain.competition.CompetitionService,s.quizleague.web.maintain.venue.VenueService])]),s.quizleague.web.maintain.season.SeasonService);

s.quizleague.web.maintain.team.TeamRoutesModule = __decorate(s.quizleague.web.maintain.team.TeamRoutesModule_()._decorators,s.quizleague.web.maintain.team.TeamRoutesModule);

s.quizleague.web.site.results.ReportComponent = __decorate(s.quizleague.web.site.results.ReportComponent_()._decorators.concat([__metadata('design:paramtypes',[require('@angular/common').Location,require('@angular/router').ActivatedRoute,s.quizleague.web.site.results.ResultService,s.quizleague.web.site.common.SideMenuService,s.quizleague.web.site.common.TitleService])]),s.quizleague.web.site.results.ReportComponent);

s.quizleague.web.site.team.TeamTitleComponent = __decorate(s.quizleague.web.site.team.TeamTitleComponent_()._decorators.concat([__metadata('design:paramtypes',[require('@angular/router').ActivatedRoute,s.quizleague.web.site.team.TeamService])]),s.quizleague.web.site.team.TeamTitleComponent);

s.quizleague.web.site.team.TeamsTitleComponent = __decorate(s.quizleague.web.site.team.TeamsTitleComponent_()._decorators,s.quizleague.web.site.team.TeamsTitleComponent);

s.quizleague.web.site.venue.VenueComponent = __decorate(s.quizleague.web.site.venue.VenueComponent_()._decorators.concat([__metadata('design:paramtypes',[require('@angular/router').ActivatedRoute,s.quizleague.web.site.venue.VenueService,require('@angular/platform-browser').DomSanitizer,s.quizleague.web.site.common.TitleService,s.quizleague.web.site.common.SideMenuService])]),s.quizleague.web.site.venue.VenueComponent);

s.quizleague.web.site.calendar.FixturesEventComponent = __decorate(s.quizleague.web.site.calendar.FixturesEventComponent_()._decorators,s.quizleague.web.site.calendar.FixturesEventComponent);

s.quizleague.web.site.calendar.CalendarViewService = __decorate(s.quizleague.web.site.calendar.CalendarViewService_()._decorators.concat([__metadata('design:paramtypes',[s.quizleague.web.site.global.ApplicationContextService,s.quizleague.web.site.season.SeasonService])]),s.quizleague.web.site.calendar.CalendarViewService);

s.quizleague.web.maintain.team.TeamModule = __decorate(s.quizleague.web.maintain.team.TeamModule_()._decorators,s.quizleague.web.maintain.team.TeamModule);
__decorate([core.Input(),__metadata('design:type',Object)],s.quizleague.web.site.leaguetable.LeagueTableComponent.prototype,'table',null);
s.quizleague.web.site.leaguetable.LeagueTableComponent = __decorate(s.quizleague.web.site.leaguetable.LeagueTableComponent_()._decorators.concat([__metadata('design:paramtypes',[s.quizleague.web.site.leaguetable.LeagueTableService])]),s.quizleague.web.site.leaguetable.LeagueTableComponent);

s.quizleague.web.site.results.ResultsComponentsModule = __decorate(s.quizleague.web.site.results.ResultsComponentsModule_()._decorators,s.quizleague.web.site.results.ResultsComponentsModule);

s.quizleague.web.maintain.team.TeamService = __decorate(s.quizleague.web.maintain.team.TeamService_()._decorators.concat([__metadata('design:paramtypes',[require('@angular/http').Http,s.quizleague.web.maintain.text.TextService,s.quizleague.web.maintain.venue.VenueService,s.quizleague.web.maintain.user.UserService])]),s.quizleague.web.maintain.team.TeamService);

s.quizleague.web.site.results.AllResultsComponent = __decorate(s.quizleague.web.site.results.AllResultsComponent_()._decorators.concat([__metadata('design:paramtypes',[require('@angular/router').ActivatedRoute,s.quizleague.web.site.season.SeasonService,s.quizleague.web.site.results.ResultsViewService,s.quizleague.web.site.common.SideMenuService,s.quizleague.web.site.common.TitleService])]),s.quizleague.web.site.results.AllResultsComponent);

s.quizleague.web.site.competition.PlateCompetitionComponent = __decorate(s.quizleague.web.site.competition.PlateCompetitionComponent_()._decorators.concat([__metadata('design:paramtypes',[require('@angular/router').ActivatedRoute,s.quizleague.web.site.competition.CompetitionService,s.quizleague.web.site.competition.CompetitionViewService,s.quizleague.web.site.global.ApplicationContextService,s.quizleague.web.site.common.TitleService,s.quizleague.web.site.common.SideMenuService])]),s.quizleague.web.site.competition.PlateCompetitionComponent);

s.quizleague.web.maintain.venue.VenueComponent = __decorate(s.quizleague.web.maintain.venue.VenueComponent_()._decorators.concat([__metadata('design:paramtypes',[s.quizleague.web.maintain.venue.VenueService,require('@angular/router').ActivatedRoute,require('@angular/common').Location])]),s.quizleague.web.maintain.venue.VenueComponent);

s.quizleague.web.site.team.TeamFixturesTitleComponent = __decorate(s.quizleague.web.site.team.TeamFixturesTitleComponent_()._decorators,s.quizleague.web.site.team.TeamFixturesTitleComponent);

s.quizleague.web.site.results.ResultService = __decorate(s.quizleague.web.site.results.ResultService_()._decorators.concat([__metadata('design:paramtypes',[require('@angular/http').Http,s.quizleague.web.site.user.UserService,s.quizleague.web.site.fixtures.FixtureService,s.quizleague.web.site.text.TextService,s.quizleague.web.site.team.TeamService])]),s.quizleague.web.site.results.ResultService);

s.quizleague.web.site.competition.CupCompetitionComponent = __decorate(s.quizleague.web.site.competition.CupCompetitionComponent_()._decorators.concat([__metadata('design:paramtypes',[require('@angular/router').ActivatedRoute,s.quizleague.web.site.competition.CompetitionService,s.quizleague.web.site.competition.CompetitionViewService,s.quizleague.web.site.global.ApplicationContextService,s.quizleague.web.site.common.TitleService,s.quizleague.web.site.common.SideMenuService])]),s.quizleague.web.site.competition.CupCompetitionComponent);

s.quizleague.web.maintain.user.UserService = __decorate(s.quizleague.web.maintain.user.UserService_()._decorators.concat([__metadata('design:paramtypes',[require('@angular/http').Http])]),s.quizleague.web.maintain.user.UserService);

s.quizleague.web.site.results.ResultsService = __decorate(s.quizleague.web.site.results.ResultsService_()._decorators.concat([__metadata('design:paramtypes',[require('@angular/http').Http,s.quizleague.web.site.results.ResultService,s.quizleague.web.site.fixtures.FixturesService])]),s.quizleague.web.site.results.ResultsService);

s.quizleague.web.site.AppRoutingModule = __decorate(s.quizleague.web.site.AppRoutingModule_()._decorators,s.quizleague.web.site.AppRoutingModule);

s.quizleague.web.site.competition.CompetitionModule = __decorate(s.quizleague.web.site.competition.CompetitionModule_()._decorators,s.quizleague.web.site.competition.CompetitionModule);

s.quizleague.web.site.calendar.CompetitionEventComponent = __decorate(s.quizleague.web.site.calendar.CompetitionEventComponent_()._decorators,s.quizleague.web.site.calendar.CompetitionEventComponent);

s.quizleague.web.maintain.text.TextComponent = __decorate(s.quizleague.web.maintain.text.TextComponent_()._decorators.concat([__metadata('design:paramtypes',[s.quizleague.web.maintain.text.TextService,require('@angular/router').ActivatedRoute,require('@angular/common').Location])]),s.quizleague.web.maintain.text.TextComponent);
require('@angular/platform-browser-dynamic').platformBrowserDynamic().bootstrapModule(s.quizleague.web.site.AppModule);
           