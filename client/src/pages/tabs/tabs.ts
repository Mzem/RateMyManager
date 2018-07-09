import { Component } from '@angular/core';

import { HomePage } from "../home/home";
import { ManagersPage } from "../managers/managers";

@Component({
  selector: 'page-tabs',
  template: `
    <ion-tabs>
      <ion-tab [root]="homePage" tabTitle="Accueil" tabIcon="home"></ion-tab>
      <ion-tab [root]="managersPage" tabTitle="Managers" tabIcon="people"></ion-tab>
    </ion-tabs>
  `
})
export class TabsPage {
  homePage = HomePage;
  managersPage = ManagersPage;
}
