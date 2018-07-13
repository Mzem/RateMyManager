import { Component } from '@angular/core';
import { NavController } from 'ionic-angular';

import { AuthPage } from "../auth/auth";

@Component({
	selector: 'page-home',
	templateUrl: 'home.html'
})

export class HomePage 
{
	authPage = AuthPage;
	
	//NavController is needed to navigate aroud the app, its a class which allows to manage the stack of pages
	constructor(public navCtrl: NavController) {}
	
	//We push a new page on the stack
	onGoToAuth(profile : string) {
		localStorage.setItem('profile', profile);
		this.navCtrl.push(AuthPage);//, {profileData: profile});
	}

}
