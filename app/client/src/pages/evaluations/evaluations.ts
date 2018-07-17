import { Component } from '@angular/core';
import { NavParams, NavController } from "ionic-angular";
import { AlertController } from "ionic-angular";
import { ModalController } from "ionic-angular";
import { ModalEvaluationsPage } from "./modalEvaluations/modalEvaluations";

//Auth
import { JwtHelperService } from "@auth0/angular-jwt";
import { SERVER_URL } from "../../config";
import { AuthProvider } from "../../providers/auth/auth";
import { HttpClient } from "@angular/common/http";

@Component({
  selector: 'page-evaluations',
  templateUrl: 'evaluations.html',
})

export class EvaluationsPage
{
	username : string;
	date: String = new Date().toISOString();
	rate : any = 0;
	
	constructor(public navCtrl: NavController, public navParams: NavParams, private alertCtrl: AlertController, private modalCtrl: ModalController,
				private readonly authProvider: AuthProvider,
				jwtHelper: JwtHelperService,
				private readonly httpClient: HttpClient) 
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
	
	/* fetch data from service
	ionViewWillEnter() {
		this.httpClient.get(`${SERVER_URL}/secret`, {responseType: 'text'}).subscribe(
			text => this.message = text,
			err => console.log(err)
		);
	}*/
	
	onRefreshDate() {
		this.date = new Date().toISOString();
	}
	
	onHelp() 
	{
		const help = this.alertCtrl.create({
			title: 'Aide',
			subTitle:'À chaque fin de mois, vous pouvez évaluer votre manager sur cette période.',
			message:'Vous avez jusqu\'à la fin du mois prochain pour faire l\'évaluation.',
			buttons: ['Ok'],
			cssClass: 'alertCustomCss'
		});
		help.present();
	}
	
	onNoter(manager: string) 
	{
		const modal = this.modalCtrl.create(ModalEvaluationsPage, {managerData: manager});
		modal.present();
	}
}
