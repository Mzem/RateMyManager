import { Component } from '@angular/core';
import { NavController } from 'ionic-angular';

import { ManagerPage } from "./manager/manager";

import { RestProvider } from '../../providers/rest/rest';

@Component({
	selector: 'page-managers',
	templateUrl: 'managers.html',
})

export class ManagersPage 
{
	managers: any;
	
	constructor(private navCtrl: NavController, public restProvider: RestProvider) {}
	
	onLoadManager(nom: string) {
		this.getManagers();
		this.navCtrl.push(ManagerPage, {nomManager: nom});
	}
	
	getManagers() {
		this.restProvider.getManagers()
			.then(data => {
				this.managers = data;
				console.log(this.managers);
			});
	}
	
	ionViewWillEnter() {
		this.getManagers();
	}


}
