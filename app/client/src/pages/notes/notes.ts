import { Component, OnInit } from '@angular/core';
import { NavParams, NavController } from "ionic-angular";
import { AlertController } from "ionic-angular";
import { ModalController } from "ionic-angular";
import { ModalNotesPage } from "./modalNotes/modalNotes";

@Component({
  selector: 'page-notes',
  templateUrl: 'notes.html',
})

export class NotesPage implements OnInit
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
