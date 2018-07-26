import { Component } from '@angular/core';
import { NavParams, NavController } from "ionic-angular";
import { AlertController } from "ionic-angular";
import { ModalController, LoadingController } from "ionic-angular";
import { ModalEvaluationsPage } from "./modalEvaluations/modalEvaluations";

//Providers
import { JwtHelperService } from "@auth0/angular-jwt";
import { FeedbackProvider } from "../../providers/feedback/feedback";
import { AuthProvider } from "../../providers/auth/auth";

@Component({
  selector: 'page-evaluations',
  templateUrl: 'evaluations.html',
})

export class EvaluationsPage
{
	username : string;
	date: String = new Date().toISOString();
	managers: any;
	rate: any = 0;
	
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
		
		this.feedbackProvider.getManagers(this.username)
			.then(data => {
				this.managers = data;
				
				for (let manager of this.managers) {
					this.feedbackProvider.getFeedback(this.username, manager.id, manager.month)
						.then(data => {
							manager.feedback = data;
						});
				}
				loading.dismiss();
			});
	}
	
	onHelp() {
		const help = this.alertCtrl.create({
			title: 'Aide',
			subTitle:'À chaque fin de mois, vous pouvez évaluer votre manager sur cette période.',
			message:'Vous avez jusqu\'à la fin du mois prochain pour faire l\'évaluation.',
			buttons: ['Ok'],
			cssClass: 'alertCustomCss'
		});
		help.present();
	}
	
	onNoter(manager: any) {
		const modal = this.modalCtrl.create(ModalEvaluationsPage, {managerData : manager});
		modal.present();
	}
}
