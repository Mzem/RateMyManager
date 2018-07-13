import { Component, OnInit } from '@angular/core';
import { NavParams, NavController } from "ionic-angular";
import { ViewController } from "ionic-angular";

@Component({
  selector: 'page-modalNotes',
  templateUrl: 'modalNotes.html',
})

export class ModalNotesPage implements OnInit
{
	constructor(public navCtrl: NavController, private navParams: NavParams, private viewCtrl: ViewController) {}
	
	ngOnInit() {
	}
	
	ionViewDidLoad() {
		//this.manager = this.navParams.get('managerData');
	}
	
	onQuitModal() {
		this.viewCtrl.dismiss();
	}
}
