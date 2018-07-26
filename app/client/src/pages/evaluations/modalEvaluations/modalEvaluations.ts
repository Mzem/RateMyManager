import { Component } from '@angular/core';
import { NavParams, NavController } from "ionic-angular";
import { ViewController } from "ionic-angular";

import { JwtHelperService } from "@auth0/angular-jwt";
import { FeedbackProvider } from "../../../providers/feedback/feedback";
import { AuthProvider } from "../../../providers/auth/auth";

@Component({
  selector: 'page-modalEvaluations',
  templateUrl: 'modalEvaluations.html',
})

export class ModalEvaluationsPage
{
	username: string;
	manager: any;
	rate = 0;
	comment: string;
	
	constructor(public navCtrl: NavController, private navParams: NavParams, private viewCtrl: ViewController,
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
		this.manager = this.navParams.get('managerData');
		this.rate = this.manager.feedback.note; 
		this.comment = this.manager.feedback.comment;
	}
	
	onValidateModal(newFeedback: boolean) {
		this.manager.feedback.note = this.rate;
		this.manager.feedback.comment = this.comment;
		if (this.manager.feedback.id == null) {
			this.feedbackProvider.newFeedback(this.manager.feedback)
			.then((result) => {
				console.log(result);
			}, (err) => {
				console.log(err);
			});
		} else {
			this.feedbackProvider.editFeedback(this.manager.feedback)
			.then((result) => {
				console.log(result);
			}, (err) => {
				console.log(err);
			});
		}	
		this.viewCtrl.dismiss();
	}
	onQuitModal() {
		this.viewCtrl.dismiss();
	}
	
	onModelChange(event) {
		/*this.rate = event;
		console.log(event);*/
	}
}
