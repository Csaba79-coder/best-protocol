import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import {HttpClientModule} from "@angular/common/http";
import {ApiModule, GovernmentRepresentativeService, RepresentativeAdminModel} from "../../build/openapi/government-service";
import { RepresentativeListComponent } from './government-service/components/representative-list/representative-list.component';

@NgModule({
  declarations: [
    AppComponent,
    RepresentativeListComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    ApiModule,
  ],
  providers: [GovernmentRepresentativeService],
  bootstrap: [AppComponent]
})
export class AppModule { }
