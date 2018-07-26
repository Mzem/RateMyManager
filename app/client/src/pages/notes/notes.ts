import { Component } from '@angular/core';
import { NavParams, NavController } from "ionic-angular";
import { AlertController, LoadingController } from "ionic-angular";
import { ModalController } from "ionic-angular";
import { ModalNotesPage } from "./modalNotes/modalNotes";

import { JwtHelperService } from "@auth0/angular-jwt";
import { FeedbackProvider } from "../../providers/feedback/feedback";
import { AuthProvider } from "../../providers/auth/auth";

@Component({
  selector: 'page-notes',
  templateUrl: 'notes.html',
})

export class NotesPage
{
	username: string;
	date: String = new Date().toISOString();
	dateNow: String = new Date().toISOString();
	gloablRating: number = 0;
	yearFeedbacks: any;
	
	constructor(public navCtrl: NavController, public navParams: NavParams, private alertCtrl: AlertController, private modalCtrl: ModalController, private loadingCtrl: LoadingController,
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
		const loading = this.loadingCtrl.create({content: 'Chargement...'});
		loading.present();
		
		this.feedbackProvider.getGlobalRating(this.username)
			.then(data => {
				this.gloablRating = +data;
				this.feedbackProvider.getYearRatings(this.username, (new Date(this.date.valueOf())).getFullYear())
					.then(data => {
					this.yearFeedbacks = data;
				});
				loading.dismiss();
			});
	}
	
	onDetails(month: string) 
	{
		const modal = this.modalCtrl.create(ModalNotesPage, {month: month});
		modal.present();
	}
	
	onRefreshDate() {
		this.date = new Date().toISOString();
		this.onChangeDate();
	}
	
	onChangeDate() {
		const loading = this.loadingCtrl.create({content: 'Chargement...'});
		loading.present();
		
		this.feedbackProvider.getGlobalRating(this.username)
			.then(data => {
				this.gloablRating = +data;
				this.feedbackProvider.getYearRatings(this.username, (new Date(this.date.valueOf())).getFullYear())
					.then(data => {
					this.yearFeedbacks = data;
				});
				loading.dismiss();
			});
	}
	
	onHelp() {
		const help = this.alertCtrl.create({
			title: 'Aide',
			subTitle:'À chaque fin de mois, vos consultants auront la possibilité de vous pouvez évaluer sur cette période.',
			buttons: ['Ok'],
			cssClass: 'alertCustomCss'
		});
		help.present();
	}
}
