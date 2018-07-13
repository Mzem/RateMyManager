import { Component, OnInit } from '@angular/core';
import { IonicPage, NavController, NavParams } from 'ionic-angular';
import { LoadingController, ToastController } from 'ionic-angular';
import { FileTransfer, FileUploadOptions, FileTransferObject } from '@ionic-native/file-transfer';
import { Camera, CameraOptions } from '@ionic-native/camera';



@Component({
  selector: 'page-profile',
  templateUrl: 'profile.html',
})

export class ProfilePage implements OnInit
{
	username: string;
	profile : string;

	constructor(public navCtrl: NavController, public navParams: NavParams, 
		private transfer: FileTransfer,
		private camera: Camera,
		public loadingCtrl: LoadingController,
		public toastCtrl: ToastController) {}
	
	ngOnInit() {
		this.username = localStorage.getItem('username');
		this.profile = localStorage.getItem('profile');
	}
	
	onChangeProfilePic() {
		console.log("hi");
	}


}
