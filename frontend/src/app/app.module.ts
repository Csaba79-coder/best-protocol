import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import {HttpClientModule} from "@angular/common/http";
import {ApiModule} from "../../build/openapi/governemnt-service";
import { RepresentativeListComponent } from './government-service/components/representative-list/representative-list.component';

@NgModule({
  declarations: [
    AppComponent,
    RepresentativeListComponent
  ],
  providers: [],
  imports: [
    BrowserModule,
    HttpClientModule,
    ApiModule
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
