import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {OwnerComponent} from './component/owner/owner.component';
import {HorseFormComponent} from "./component/horse-form/horse-form.component";
import {OwnerDialogComponent} from "./component/owner-dialog/owner-dialog.component";


const routes: Routes = [
  {path: 'horses', component: HorseFormComponent},
  {path: 'owner', component: OwnerComponent},
  {path: 'edit/:id', component: OwnerDialogComponent},
  {path: '', redirectTo: 'horses', pathMatch: 'full'},
];

@NgModule({
  imports: [RouterModule.forRoot(routes,  { enableTracing: true })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
