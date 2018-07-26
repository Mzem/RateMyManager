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
import { FeedbackProvider } from '../providers/feedback/feedback';
import { AuthProvider } from '../providers/auth/auth';

import { FileTransfer, FileUploadOptions, FileTransferObject } from '@ionic-native/file-transfer';
import { File } from '@ionic-native/file';
import { Camera } from '@ionic-native/camera';

import { Ionic2RatingModule } from 'ionic2-rating';

import { JWT_OPTIONS, JwtModule } from '@auth0/angular-jwt';
import { CustomFormsModule } from 'ng2-validation';
import { Storage, IonicStorageModule } from "@ionic/storage";

//By default, the @auth0/angular-jwt library reads the token from the localStorage with the key id_token. 
//Because we want to use Ionic's Storage service in this example, we have to tell the library where to look for the token. 
//We do that by creating a jwtOptionsFactory method and configure the tokenGetter property.
export function jwtOptionsFactory(storage: Storage) 
{
	return {
		tokenGetter: () => storage.get('jwt_token'),
		//Because @auth0/angular-jwt attaches a HttpInterceptor to Angular's http client service, every request initiated from our application goes through that interceptor. 
		//By default, the library does not add the Authorization header to any request. 
		//We have to whitelist urls where the library is allowed to add the Authorization header with the whitelistedDomains property.
		//In our application, only request sent to localhost:8080 will contain the Authorization header.
		whitelistedDomains: ['localhost:8080']
	}
}

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
    //Then we configure the JwtModule with a config object and specify the method as the factory method for the module
    JwtModule.forRoot({
		jwtOptionsProvider: {
			provide: JWT_OPTIONS,
			useFactory: jwtOptionsFactory,
			deps: [Storage]
		}
    }),
    CustomFormsModule,
    Ionic2RatingModule,
    IonicModule.forRoot(MyApp, {
		backButtonText: '',
		backButtonIcon: 'arrow-back'
    }),
    IonicStorageModule.forRoot()
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
	FeedbackProvider,
	AuthProvider,
	FileTransfer,
	File,
	Camera
  ]
})
export class AppModule {}
