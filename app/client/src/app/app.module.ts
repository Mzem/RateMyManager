import { BrowserModule } from '@angular/platform-browser';
import { ErrorHandler, NgModule } from '@angular/core';
import { IonicApp, IonicErrorHandler, IonicModule } from 'ionic-angular';
import { SplashScreen } from '@ionic-native/splash-screen';
import { StatusBar } from '@ionic-native/status-bar';
import { MyApp } from './app.component';

import { HomePage } from '../pages/home/home';
import { AuthPage } from '../pages/auth/auth';
import { EvaluationsPage } from '../pages/evaluations/evaluations';
import { NotesPage } from '../pages/notes/notes';
import { ProfilePage } from '../pages/profile/profile';
import { AboutPage } from '../pages/about/about';
import { ModalEvaluationsPage } from '../pages/evaluations/modalEvaluations/modalEvaluations';
import { ModalNotesPage } from '../pages/notes/modalNotes/modalNotes';

//Consuming REST API
import { HttpClientModule } from '@angular/common/http';
import { RestProvider } from '../providers/rest/rest';
//import { AuthProvider } from '../providers/auth/auth';

import { FileTransfer, FileUploadOptions, FileTransferObject } from '@ionic-native/file-transfer';
import { File } from '@ionic-native/file';
import { Camera } from '@ionic-native/camera';

import { Ionic2RatingModule } from 'ionic2-rating';

@NgModule({
  declarations: [
    MyApp,
    HomePage,
    EvaluationsPage,
    NotesPage,
    AuthPage,
    ProfilePage,
    AboutPage,
    ModalEvaluationsPage,
    ModalNotesPage,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    Ionic2RatingModule,
    IonicModule.forRoot(MyApp, {
		backButtonText: '',
		backButtonIcon: 'arrow-back'
    })
  ],
  bootstrap: [IonicApp],
  entryComponents: [
    MyApp,
    HomePage,
    EvaluationsPage,
    NotesPage,
    AuthPage,
    ProfilePage,
    AboutPage,
    ModalEvaluationsPage,
    ModalNotesPage
  ],
  providers: [
	StatusBar,
	SplashScreen,
	{provide: ErrorHandler, useClass: IonicErrorHandler},
	RestProvider,
	//AuthProvider
	FileTransfer,
	File,
	Camera
  ]
})
export class AppModule {}
