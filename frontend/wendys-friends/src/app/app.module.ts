import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import { FormsModule, ReactiveFormsModule }   from '@angular/forms';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HeaderComponent} from './component/header/header.component';
import {OwnerComponent} from './component/owner/owner.component';
import {HttpClientModule} from '@angular/common/http';
import { HorseFormComponent } from './component/horse-form/horse-form.component';
import {HorseService} from "./service/horse.service";
import {MatDialogModule} from '@angular/material/dialog';
import {MatButtonModule} from "@angular/material/button";
import { OwnerDialogComponent } from './component/owner-dialog/owner-dialog.component';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import { HorseDialogComponent } from './component/horse-dialog/horse-dialog.component';
import { OwnerShowHorsesComponent } from './component/owner-show-horses/owner-show-horses.component';
import { AddHorseComponent } from './component/add-horse/add-horse.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    OwnerComponent,
    HorseFormComponent,
    OwnerDialogComponent,
    HorseDialogComponent,
    OwnerShowHorsesComponent,
    AddHorseComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    MatDialogModule,
    MatButtonModule,
    BrowserAnimationsModule
  ],
  entryComponents: [
    OwnerDialogComponent
  ],
  providers: [HorseService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
