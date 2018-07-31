import { Component } from '@angular/core';
import { NavParams, NavController } from "ionic-angular";
import { ViewController, AlertController } from "ionic-angular";
import { NgForm } from '@angular/forms';

import { JwtHelperService } from "@auth0/angular-jwt";
import { FeedbackProvider } from "../../../providers/feedback/feedback";
import { AuthProvider } from "../../../providers/auth/auth";

@Component({
  selector: 'page-modalReset',
  templateUrl: 'modalReset.html',
})

export class ModalResetPage
{
	constructor(public navCtrl: NavController, private navParams: NavParams, private viewCtrl: ViewController, private alertCtrl: AlertController,
				public feedbackProvider: FeedbackProvider,
				private readonly authProvider: AuthProvider,
				jwtHelper: JwtHelperService)
	{				
		
	}
	
	onValidateModal(form: NgForm) {
		const help = this.alertCtrl.create({
			subTitle:'Un e-mail de réinitialisation à été envoyé à l\'adresse '+form.value.username+'.',
			buttons: ['Ok'],
			cssClass: 'alertCustomCss'
		});
		help.present();
		this.viewCtrl.dismiss();
	}
	
	onQuitModal() {
		this.viewCtrl.dismiss();
	}
}
