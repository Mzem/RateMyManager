import { NgModule } from '@angular/core';
import { IonicPageModule } from 'ionic-angular';
import { ManagersPage } from './managers';

@NgModule({
  declarations: [
    ManagersPage,
  ],
  imports: [
    IonicPageModule.forChild(ManagersPage),
  ],
})
export class ManagersPageModule {}
