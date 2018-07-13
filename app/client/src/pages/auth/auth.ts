import { Component, OnInit } from '@angular/core';
import { NavParams, NavController } from "ionic-angular";
import { NgForm } from '@angular/forms';

import { EvaluationsPage } from "../evaluations/evaluations";
import { NotesPage } from "../notes/notes";


@Component({
  selector: 'page-auth',
  templateUrl: 'auth.html',
})
export class AuthPage implements OnInit
{
	profile : string;
	evaluationsPage = EvaluationsPage;
	notesPage = NotesPage;
	
	constructor (private navParams: NavParams, private navCtrl: NavController) {}
  
	onLogin(form: NgForm) 
	{
		localStorage.setItem('username',form.value.username);
		if (this.profile === 'Consultant')
			this.navCtrl.setRoot(EvaluationsPage);
		else if (this.profile === 'Manager')
			this.navCtrl.setRoot(NotesPage);
		//console.log(form.value.username);
	}
	
	ngOnInit() {
		//this.profile = this.navParams.get('profileData');
		this.profile = localStorage.getItem('profile');
	}
}
