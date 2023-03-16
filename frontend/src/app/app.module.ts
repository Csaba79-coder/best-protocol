import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { HttpClientModule} from "@angular/common/http";
import { ApiModule, GovernmentRepresentativeService } from "../../build/openapi/government-service";
import { RepresentativeListComponent } from './government-service/components/representative-list/representative-list.component';
import {RouterModule, Routes} from "@angular/router";
import { GovernmentListComponent } from "./government-service/components/government-list/government-list/government-list.component";

const routes: Routes = [
    // first match wins top down!
    {path: 'api/admin/gov-representatives/government/:governmentId', component: RepresentativeListComponent },
    {path: 'api/admin/gov-representatives/government', component: RepresentativeListComponent },
    {path: 'api/admin/gov-representatives', component: RepresentativeListComponent },
    // {path: 'api/admin/governments', component: GovernmentListComponent},
    {path: '', redirectTo: '/api/admin/gov-representatives', pathMatch: 'full' },
    {path: '**', redirectTo: '/api/admin/gov-representatives', pathMatch: 'full' }
];

@NgModule({
    declarations: [
        AppComponent,
        RepresentativeListComponent,
        GovernmentListComponent
    ],
    imports: [
        RouterModule.forRoot(routes),
        BrowserModule,
        HttpClientModule,
        ApiModule,
    ],
    exports: [RouterModule],
    providers: [GovernmentRepresentativeService],
    bootstrap: [AppComponent]
})
export class AppModule { }
