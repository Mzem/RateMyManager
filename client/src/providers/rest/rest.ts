import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';


@Injectable()
export class RestProvider 
{
	serviceURL = 'http://localhost:8080';
	httpOptions = {
		headers: new HttpHeaders({ 'Content-Type': 'application/json' })
	};
	
	constructor(public http: HttpClient) {
		console.log('Hello RestProvider Provider');
	}
	
	getManagers() 
	{
		return new Promise(resolve => {
			this.http.get(this.serviceURL+'/Managers').subscribe(data => {
				resolve(data);
			}, err => {
				console.log(err);
			});
		});
	}

}
