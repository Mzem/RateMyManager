import { NgModule } from '@angular/core';
import { IonicPageModule } from 'ionic-angular';
import { ModalNotesPage } from './modalNotes';

@NgModule({
  declarations: [
    ModalNotesPage,
  ],
  imports: [
    IonicPageModule.forChild(ModalNotesPage),
  ],
})
export class ModalNotesPageModule {}
