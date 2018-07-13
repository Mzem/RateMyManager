import { Component, OnInit } from '@angular/core';
import { NavParams, NavController } from "ionic-angular";
import { AlertController } from "ionic-angular";
import { ModalController } from "ionic-angular";
import { ModalEvaluationsPage } from "./modalEvaluations/modalEvaluations";

@Component({
  selector: 'page-evaluations',
  templateUrl: 'evaluations.html',
})

export class EvaluationsPage implements OnInit
{
	username : string;
	date: String = new Date().toISOString();
	rate : any = 0;
	
	constructor(public navCtrl: NavController, public navParams: NavParams, private alertCtrl: AlertController, private modalCtrl: ModalController) {}
	
	ngOnInit() {
		this.username = localStorage.getItem('username');
	}
	
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
