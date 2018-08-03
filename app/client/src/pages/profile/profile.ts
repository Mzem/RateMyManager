import { Component } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import { LoadingController, ToastController } from 'ionic-angular';
import { NgForm } from '@angular/forms';

import { JwtHelperService } from "@auth0/angular-jwt";
import { AuthProvider } from "../../providers/auth/auth";
import { finalize } from 'rxjs/operators';


@Component({
  selector: 'page-profile',
  templateUrl: 'profile.html',
})

export class ProfilePage
{
	username: string;
	user: any;
	profile : string;

	constructor(public navCtrl: NavController, public navParams: NavParams, 
		public loadingCtrl: LoadingController,
		public toastCtrl: ToastController,
		private readonly authProvider: AuthProvider,
		jwtHelper: JwtHelperService) 
	{				
		this.authProvider.authUser.subscribe(jwt => {
			if (jwt) {
				const decoded = jwtHelper.decodeToken(jwt);
				this.username = decoded.sub
			}
			else {
				this.username = null;
			}
		});
		
		this.profile = localStorage.getItem('profile');
	}
	
	ionViewCanEnter() {
		const loading = this.loadingCtrl.create({content: 'Chargement...'});
		loading.present();
		
		this.authProvider.getUserInfo(this.username)
			.then(data => {
				this.user = data;
				loading.dismiss();
			});
	}
	
	onChangePassword(form: NgForm) 
	{
		let loading = this.loadingCtrl.create({
			spinner: 'bubbles',
			content: 'Envoi...'
		});
		loading.present();

		this.authProvider
			.updatePassword(this.username, form.value)
			.pipe(finalize(() => loading.dismiss()))
			.subscribe(
				(resp) => this.showSuccesToast(resp), 
				err => this.handleError(err)
			);
	}

	private showSuccesToast(resp) 
	{
		if (resp == "NEW_PASSWORD") {
			const toast = this.toastCtrl.create({
				message: 'Mot de passe modifié.',
				duration: 3000,
				position: 'bottom'
			});
		  toast.present();
		} else {
			const toast = this.toastCtrl.create({
				message: 'Impossible de modifier le mot de passe.',
				duration: 3000,
				position: 'bottom'
			});
		  toast.present();
		}
	}

	
	//If an error occurs a toast message is presented to tell the user that something went wrong.
	handleError(error: any) 
	{
		const toast = this.toastCtrl.create({
			message: `Unexpected error: ${error.statusText}`,
			duration: 5000,
			position: 'bottom'
		});
		toast.present();
	}
	
	onChangeProfilePic() {
		console.log("Change pic");
	}


}
