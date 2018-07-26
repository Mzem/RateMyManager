import { Component } from '@angular/core';
import { NavParams, NavController } from "ionic-angular";
import { ViewController, LoadingController } from "ionic-angular";

import { JwtHelperService } from "@auth0/angular-jwt";
import { FeedbackProvider } from "../../../providers/feedback/feedback";
import { AuthProvider } from "../../../providers/auth/auth";

@Component({
  selector: 'page-modalNotes',
  templateUrl: 'modalNotes.html',
})

export class ModalNotesPage
{
	username: string;
	month: string;
	feedbacks: any;
	
	constructor(public navCtrl: NavController, private navParams: NavParams, private viewCtrl: ViewController, private loadingCtrl: LoadingController,
				public feedbackProvider: FeedbackProvider,
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
	}
	
	ionViewCanEnter() {
		this.month = this.navParams.get('month');
		const loading = this.loadingCtrl.create({content: 'Chargement...'});
		loading.present();
		
		this.feedbackProvider.getMonthRatings(this.username, this.month)
			.then(data => {
				this.feedbacks = data;
				loading.dismiss();
			});
	}
	
	onQuitModal() {
		this.viewCtrl.dismiss();
	}
}
