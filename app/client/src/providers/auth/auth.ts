import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { tap } from 'rxjs/operators';
import { ReplaySubject, Observable } from "rxjs";
import { SERVER_URL } from "../../config";
import { Storage } from "@ionic/storage";
import { JwtHelperService } from "@auth0/angular-jwt";


//Manages the authentication of our app. It checks the JWT, sends login and signup requests to the server and handles the responses.
@Injectable()
export class AuthProvider 
{
	private jwtTokenName = 'jwt_token';
	
	//Our app utilizes the ReplaySubject provided by the RxJs library to notify other parts of the app when the authorization state changes. 
	//This object represents an observable sequence as well as an observer. Every time the app calls next on this object all subscribers will be notified. 
	//We use that mechanism for navigation from the LoginPage to the secure HomePage and back.
	//Used in app.com
	authUser = new ReplaySubject<any>(1);
	
	
	constructor(private readonly httpClient: HttpClient,
              private readonly storage: Storage,
              private readonly jwtHelper: JwtHelperService) {
	}
	
	//The function fetches the JWT from the storage, if it exists it checks the validity and then it calls a secure endpoint /authenticate. 
	//If that call succeeds it calls authUser.next with the JWT as parameter which triggers the navigation to the HomePage in app.components.ts.
	//If the /authenticate call fails or there is no JWT locally stored or the token is expired, the app deletes the token from the storage, calls authUser.next(null) 
	//which then triggers a navigation to the LoginPage.
	//This call to /authenticate is again debatable like the query to fetch the user from the database. It depends on the use case. 
	//If the client and the server only depend on the information stored in the JWT without ever check the server and the user database,
	//the application would not have a way to immediately block a user or change the roles of a user. 
	//This is not a problem when the JWT has a very short validity, but in this example the token is valid for 30 days.
	checkLogin() {
		this.storage.get(this.jwtTokenName).then(jwt => {
			if (jwt && !this.jwtHelper.isTokenExpired(jwt)) 
			{
				this.httpClient.get(`${SERVER_URL}/authenticate`)
				.subscribe(() => this.authUser.next(jwt),
				(err) => this.storage.remove(this.jwtTokenName).then(() => this.authUser.next(null)));
				// OR
				// this.authUser.next(jwt);
			}
			else {
				this.storage.remove(this.jwtTokenName).then(() => this.authUser.next(null));
			}
		});
	}
	
	//This function is called from the LoginPage after the user enters his username and password and taps the login button. 
	//The function posts the data to the /login endpoint and receives back a JWT when the login information is correct. 
	login(values: any, profile: string): Observable<any> 
	{
		return this.httpClient.post(`${SERVER_URL}/login`, {"username":values.username, "password":values.password, "profiles":[{"role":profile}]}, {responseType: 'text'})
				.pipe(tap(jwt => this.handleJwtResponse(jwt)));
	}
	
	//Called when the user taps on the logout icon from the HomePage, deletes the token in the Storage and calls authUser.next(null) to trigger a navigation to the LoginPage.
	logout() {
		this.storage.remove(this.jwtTokenName).then(() => this.authUser.next(null));
	}
	
	//Posts the sign up information to the /signup endpoint and receives back either a JWT or the string 'EXISTS' when the username already exists. 
	//When the response is a JWT it will call handleJwtResponse which stores the token and navigates to the HomePage.
	signup(values: any): Observable<any> {
		return this.httpClient.post(`${SERVER_URL}/signup`, values, {responseType: 'text'})
			.pipe(tap(jwt => {
				if (jwt !== 'EXISTS') {
					return this.handleJwtResponse(jwt);
				}
				return jwt;
			}));
	}
	
	//Posts the update information to the /user/updatePassword endpoint and receives back either a JWT or the string 'EXISTS' when the username already exists. 
	updatePassword(username: string, values: any): Observable<any> 
	{
		return this.httpClient.put(`${SERVER_URL}/updatePassword`, 
			{
				"username": username, 
				"password": values.password, 
				"newPassword": values.newPassword
			}, 
			//this.storage.get(this.jwtTokenName),
			{responseType: 'text'})
			.pipe(tap(resp => {return resp;}));
	}
	
	//Stores the token locally with the storage.set function and then calls authUser.next which triggers the HomePage to load.
	private handleJwtResponse(jwt: string) {
		return this.storage.set(this.jwtTokenName, jwt)
			.then(() => this.authUser.next(jwt))
			.then(() => jwt);
	}
}

