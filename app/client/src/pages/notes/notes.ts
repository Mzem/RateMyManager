import { Component } from '@angular/core';
import { NavParams, NavController } from "ionic-angular";
import { AlertController } from "ionic-angular";
import { ModalController } from "ionic-angular";
import { ModalNotesPage } from "./modalNotes/modalNotes";

//Auth
import { JwtHelperService } from "@auth0/angular-jwt";
import { SERVER_URL } from "../../config";
import { AuthProvider } from "../../providers/auth/auth";
import { HttpClient } from "@angular/common/http";

@Component({
  selector: 'page-notes',
  templateUrl: 'notes.html',
})

export class NotesPage
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
	
	onRefreshDate() {
		this.date = new Date().toISOString();
	}
	
	onHelp() 
	{
		const help = this.alertCtrl.create({
			title: 'Aide',
			subTitle:'À chaque fin de mois, vos consultants auront la possibilité de vous pouvez évaluer sur cette période.',
			buttons: ['Ok'],
			cssClass: 'alertCustomCss'
		});
		help.present();
	}
	
	onDetails(periode: string) 
	{
		//date à gérer
		const modal = this.modalCtrl.create(ModalNotesPage, {periodeData: this.date});
		modal.present();
	}
}
