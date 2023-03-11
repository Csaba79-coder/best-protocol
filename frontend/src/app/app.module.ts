import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import {HttpClientModule} from "@angular/common/http";
import {ApiModule} from "../../build/openapi/governemnt-service";

@NgModule({
  declarations: [
    AppComponent
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
