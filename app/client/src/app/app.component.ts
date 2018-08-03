import { Component, ViewChild } from '@angular/core';
import { Platform, NavController, MenuController } from 'ionic-angular';
import { StatusBar } from '@ionic-native/status-bar';
import { SplashScreen } from '@ionic-native/splash-screen';

import { HomePage } from "../pages/home/home";
import { ProfilePage } from "../pages/profile/profile";
import { AboutPage } from "../pages/about/about";
import { EvaluationsPage } from "../pages/evaluations/evaluations";
import { NotesPage } from "../pages/notes/notes";

import { AuthProvider } from "../providers/auth/auth";

import { Events } from 'ionic-angular';

@Component({
  templateUrl: 'app.html'
})
export class MyApp 
{
	rootPage;
	profilePage = ProfilePage;
	aboutPage = AboutPage;
	username: string;
	
	//To acces the ion-nav and the navigation stack (not with injecting navController cuz this is app component)
	@ViewChild('nav') nav: NavController;

	constructor(platform: Platform, statusBar: StatusBar, splashScreen: SplashScreen, private menuCtrl: MenuController, private authProvider: AuthProvider, public events: Events) 
	{
		platform.ready().then(() => {
			statusBar.styleDefault();
			splashScreen.show();
		});
		this.rootPage = HomePage;
		//When the subscribe function receives a JWT it will change the rootPage to ... and if the input is null it will present the HomePage to the user.
		authProvider.authUser.subscribe(jwt => {
			if (jwt) {
				if (localStorage.getItem('profile') === 'Consultant')
					this.rootPage = EvaluationsPage;
				else if (localStorage.getItem('profile') === 'Manager')
					this.rootPage = NotesPage;
			} else
				this.rootPage = HomePage;
		});
		
		//Every time the application starts up it calls the checkLogin() function of the authProvider. This function checks if a JWT is stored locally.
		authProvider.checkLogin();
		
		//Pour pouvoir afficher l'utilisateur dans le sidemenu
		events.subscribe('user:created', (username) => {
			this.username = username;
		});
	}
	
	onMenuLoad(page: any) {
		this.nav.push(page);
		this.menuCtrl.close();
	}
	
	onLogout() {
		this.authProvider.logout();
		this.menuCtrl.close();
	}
}

