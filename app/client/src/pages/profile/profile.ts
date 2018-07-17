import { Component } from '@angular/core';
import { IonicPage, NavController, NavParams } from 'ionic-angular';
import { LoadingController, ToastController } from 'ionic-angular';
import { FileTransfer, FileUploadOptions, FileTransferObject } from '@ionic-native/file-transfer';
import { Camera, CameraOptions } from '@ionic-native/camera';

//Auth
import { JwtHelperService } from "@auth0/angular-jwt";
import { SERVER_URL } from "../../config";
import { AuthProvider } from "../../providers/auth/auth";
import { HttpClient } from "@angular/common/http";


@Component({
  selector: 'page-profile',
  templateUrl: 'profile.html',
})

export class ProfilePage
{
	username: string;
	profile : string;

	constructor(public navCtrl: NavController, public navParams: NavParams, 
		private transfer: FileTransfer,
		private camera: Camera,
		public loadingCtrl: LoadingController,
		public toastCtrl: ToastController,
		private readonly authProvider: AuthProvider,
				jwtHelper: JwtHelperService,
				private readonly httpClient: HttpClient) 
	{				
		this.authProvider.authUser.subscribe(jwt => {
			if (jwt) {
				const decoded = jwtHelper.decodeToken(jwt);
				this.username = decoded.sub
			}
			else {
				this.username = null;
			}
		});
		
		this.profile = localStorage.getItem('profile');
	}
	
	onChangeProfilePic() {
		console.log("Change pic");
	}


}
