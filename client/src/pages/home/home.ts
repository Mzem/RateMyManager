import { Component } from '@angular/core';
//import { NavController } from 'ionic-angular';

import { LoginPage } from "../login/login";
import { SignupPage } from "../signup/signup";

@Component({
	selector: 'page-home',
	templateUrl: 'home.html'
})

export class HomePage 
{
	loginPage = LoginPage;
	signupPage = SignupPage;
	/*
	//NavController is needed to navigate aroud the app, its a class which allows to manage the stack of pages
	constructor(public navCtrl: NavController) {}
	
	//We push a new page on the stack
	onGoToManagers() {
		this.navCtrl.push(ManagersPage)
	}*/

}
