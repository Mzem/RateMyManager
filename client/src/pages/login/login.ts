import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';


@Component({
  selector: 'page-login',
  templateUrl: 'login.html',
})
export class LoginPage 
{
	onLogin(form: NgForm) {
		console.log(form.value);
	}
}
