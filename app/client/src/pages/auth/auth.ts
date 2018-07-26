import { Component, OnInit } from '@angular/core';
import { LoadingController, ToastController } from "ionic-angular";
import { NgForm } from '@angular/forms';

import { EvaluationsPage } from "../evaluations/evaluations";
import { NotesPage } from "../notes/notes";

import { AuthProvider } from "../../providers/auth/auth";
import { finalize } from 'rxjs/operators';


@Component({
  selector: 'page-auth',
  templateUrl: 'auth.html',
})
export class AuthPage implements OnInit
{
	profile : string;
	evaluationsPage = EvaluationsPage;
	notesPage = NotesPage;
	
	constructor (private readonly loadingCtrl: LoadingController,
				private readonly authProvider: AuthProvider,
				private readonly toastCtrl: ToastController) {}
	
	//When the user taps on the login button, the app opens a loading dialog and calls the function login from the authProvider.
	onLogin(form: NgForm) 
	{
		localStorage.setItem('username',form.value.username);
		
		let loading = this.loadingCtrl.create({
			spinner: 'bubbles',
			content: 'Connection...'
		});
		loading.present();

		this.authProvider
			.login(form.value, "ROLE_"+(this.profile.toUpperCase()))
			.pipe(finalize(() => loading.dismiss()))
			.subscribe(() => {}, err => this.handleError(err));
	}
	
	//If an error occurs a toast message is presented to tell the user that something went wrong. When the login succeeds the user will see the HomePage.
	handleError(error: any) 
	{
		let message: string;
		if (error.status && error.status === 401) {
			message = 'Nom d\'utilisateur ou mot de passe incorrect\.';
		} else {
			message = `Unexpected error: ${error.statusText}`;
		}

		const toast = this.toastCtrl.create({
			message,
			duration: 5000,
			position: 'bottom'
		});

		toast.present();
	}
	
	ngOnInit() {
		//this.profile = this.navParams.get('profileData');
		this.profile = localStorage.getItem('profile');
	}
}
