import { Component, ViewChild } from '@angular/core';
import { Platform, NavController, MenuController } from 'ionic-angular';
import { StatusBar } from '@ionic-native/status-bar';
import { SplashScreen } from '@ionic-native/splash-screen';

import { HomePage } from "../pages/home/home";
import { ProfilePage } from "../pages/profile/profile";
import { AboutPage } from "../pages/about/about";

@Component({
  templateUrl: 'app.html'
})
export class MyApp 
{
	rootPage = HomePage;
	profilePage = ProfilePage;
	aboutPage = AboutPage;
	
	//To acces the ion-nav and the navigation stack (not with injecting navController cuz this is app component)
	@ViewChild('nav') nav: NavController;

	constructor(platform: Platform, statusBar: StatusBar, splashScreen: SplashScreen, private menuCtrl: MenuController) {
		platform.ready().then(() => {
			statusBar.styleDefault();
			splashScreen.show();
		});
	}
	
	onLoad(page: any)
	{
		console.log(page.name);
		if (page.name == 'HomePage')
			this.nav.setRoot(page);
		else
			this.nav.push(page);
		this.menuCtrl.close();
	}
}

