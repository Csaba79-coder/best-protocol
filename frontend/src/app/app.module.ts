import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from './app.component';
import {HttpClientModule} from "@angular/common/http";
import {ApiModule, GovernmentRepresentativeService} from "../../build/openapi/government-service";
import {
  RepresentativeListComponent
} from './government-service/components/representative-list/representative-list.component';
import {RouterModule, Routes} from "@angular/router";
import {
  GovernmentListComponent
} from "./government-service/components/government-list/government-list/government-list.component";
import {FormsModule} from "@angular/forms";

const routes: Routes = [
  {path: ':languageShortName/api/admin/governments', component: GovernmentListComponent},
  {path: ':languageShortName/api/admin/gov-representatives', component: RepresentativeListComponent},
  {path: ':languageShortName/api/admin/gov-representatives/governments', component: RepresentativeListComponent},
  {
    path: ':languageShortName/api/admin/gov-representatives/governments/:governmentId',
    component: RepresentativeListComponent
  },
  {path: '', redirectTo: '/hu/api/admin/gov-representatives', pathMatch: 'full'},
  {path: '**', redirectTo: '/hu/api/admin/gov-representatives', pathMatch: 'full'}
];

@NgModule({
  declarations: [
    AppComponent,
    RepresentativeListComponent,
    GovernmentListComponent
  ],
  imports: [
    RouterModule.forRoot(routes, { useHash: true }),
    BrowserModule,
    HttpClientModule,
    ApiModule,
    FormsModule
  ],
  exports: [RouterModule],
  providers: [GovernmentRepresentativeService, RepresentativeListComponent],
  bootstrap: [AppComponent]
})
export class AppModule {
}
