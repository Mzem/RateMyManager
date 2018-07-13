import { Component, OnInit } from '@angular/core';
import { NavParams, NavController } from "ionic-angular";
import { ViewController } from "ionic-angular";

@Component({
  selector: 'page-modalEvaluations',
  templateUrl: 'modalEvaluations.html',
})

export class ModalEvaluationsPage implements OnInit
{
	manager: string;
	rate : any = 0;
	
	constructor(public navCtrl: NavController, private navParams: NavParams, private viewCtrl: ViewController) {}
	
	ngOnInit() {
	}
	
	ionViewDidLoad() {
		this.manager = this.navParams.get('managerData');
	}
	
	onValidateModal() {
		//Changer le rating à la bd directement, pour ensuite le changer à la page precédente
		this.viewCtrl.dismiss();
	}
	
	onModelChange(event) {
		this.rate = event;
		console.log(event);
	}
}
