import { Component, OnInit } from '@angular/core';
import { NavParams, NavController } from 'ionic-angular';

@Component({
	selector: 'page-manager',
	templateUrl: 'manager.html',
})

//OnInit : 
export class ManagerPage implements OnInit 
{
	nom: string;
	
	//navParams allows to retrieve data
	constructor (private navParams: NavParams, private navCtrl: NavController) {}
	
	//Normal angular method to retrieve data
	ngOnInit() {
		//Retrieve all the data
		//this.nom = this.navParams.data; 
		
		//Retrieve a specific data
		this.nom = this.navParams.get('nomManager');
	}
	
	onGoBack() {
		//Pops the top most page 
		//this.navCtrl.pop();
		this.navCtrl.popToRoot();
	}

}
