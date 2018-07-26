import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { SERVER_URL } from "../../config";


@Injectable()
export class FeedbackProvider 
{
	serverURL = `${SERVER_URL}`+'/feedback';
	
	httpOptions = {
		headers: new HttpHeaders({ 'Content-Type': 'application/json' })
	};
	
	constructor(public http: HttpClient) {}
	
	getManagers(consultant: string) 
	{
		return new Promise(resolve => {
			this.http.get(this.serverURL+'/managers?consultant='+consultant).subscribe(data => {
				resolve(data);
			}, err => {
				console.log(err);
			});
		});
	}
	
	getFeedback(consultant: string, manager: string, month: string) 
	{
		return new Promise(resolve => {
			this.http.get(this.serverURL+'/getOne?consultant='+consultant+'&manager='+manager+'&month='+month).subscribe(data => {
				resolve(data);
			}, err => {
				console.log(err);
			});
		});
	}
	
	newFeedback(feedback: any)
	{
		return new Promise((resolve, reject) => {
			this.http.post(this.serverURL+'/add', feedback, {responseType: 'text'})
			.subscribe(res => {
				resolve(res);
		}, (err) => {
			reject(err);
			});
		});
	}
	
	editFeedback(feedback: any)
	{
		return new Promise((resolve, reject) => {
			this.http.put(this.serverURL+'/edit', feedback, {responseType: 'text'})
			.subscribe(res => {
				resolve(res);
		}, (err) => {
			reject(err);
			});
		});
	}
	
	getGlobalRating(manager: string) 
	{
		return new Promise(resolve => {
			this.http.get(this.serverURL+'/globalRating?manager='+manager).subscribe(data => {
				resolve(data);
			}, err => {
				console.log(err);
			});
		});
	}
	
	getYearRatings(manager: string, year: number) 
	{
		return new Promise(resolve => {
			this.http.get(this.serverURL+'/yearRatings?manager='+manager+'&year='+year).subscribe(data => {
				resolve(data);
			}, err => {
				console.log(err);
			});
		});
	}
	
	getMonthRatings(manager: string, month: string) 
	{
		return new Promise(resolve => {
			this.http.get(this.serverURL+'/monthRatings?manager='+manager+'&month='+month).subscribe(data => {
				resolve(data);
			}, err => {
				console.log(err);
			});
		});
	}
}
