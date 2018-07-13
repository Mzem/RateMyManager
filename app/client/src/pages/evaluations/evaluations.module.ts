import { NgModule } from '@angular/core';
import { IonicPageModule } from 'ionic-angular';
import { EvaluationsPage } from './evaluations';

@NgModule({
  declarations: [
    EvaluationsPage,
  ],
  imports: [
    IonicPageModule.forChild(EvaluationsPage),
  ],
})
export class EvaluationsPageModule {}
