import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {MenuService, MenuTranslationModel} from "../../build/openapi/government-service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'frontend';
  selectedLanguage: string;
  public searchPlaceholder?: string;
  public searchButton?: string;
  public introductionMenu?: string;
  public contactMenu?: string;
  public serviceMenu?: string;
  public questionMenu?: string;

  constructor(private router: Router,
              readonly menuService: MenuService,) {
    this.selectedLanguage = window.localStorage.getItem('lang') || 'hu';
  }

  ngOnInit(): void {
    this.getSearchTranslation();
  }

  public getSearchTranslation() {
    this.menuService.renderAllMenuTranslations(this.selectedLanguage, 'search_placeholder')
      .subscribe((data: MenuTranslationModel[]) => {
        if (data.length > 0) {
          this["searchPlaceholder"] = data[0].translationValue!;
        } else {
          this.searchPlaceholder = 'Data search ...'; // Or any default value you choose
        }
      });

    this.menuService.renderAllMenuTranslations(this.selectedLanguage, 'search_button')
      .subscribe((data: MenuTranslationModel[]) => {
        if (data.length > 0) {
          this.searchButton = data[0].translationValue!;
        } else {
          this.searchButton = 'Search'; // Or any default value you choose
        }
      });

    this.menuService.renderAllMenuTranslations(this.selectedLanguage, 'introduction')
      .subscribe((data: MenuTranslationModel[]) => {
        if (data.length > 0) {
          this.introductionMenu = data[0].translationValue!;
        } else {
          this.introductionMenu = 'Introduction'; // Or any default value you choose
        }
      });

    this.menuService.renderAllMenuTranslations(this.selectedLanguage, 'contact')
      .subscribe((data: MenuTranslationModel[]) => {
        if (data.length > 0) {
          this.contactMenu = data[0].translationValue!;
        } else {
          this.contactMenu = 'Contact'; // Or any default value you choose
        }
      });

    this.menuService.renderAllMenuTranslations(this.selectedLanguage, 'service')
      .subscribe((data: MenuTranslationModel[]) => {
        if (data.length > 0) {
          this.serviceMenu = data[0].translationValue!;
        } else {
          this.serviceMenu = 'Services'; // Or any default value you choose
        }
      });

    this.menuService.renderAllMenuTranslations(this.selectedLanguage, 'question')
      .subscribe((data: MenuTranslationModel[]) => {
        if (data.length > 0) {
          this.questionMenu = data[0].translationValue!;
        } else {
          this.questionMenu = 'Frequently asked questions'; // Or any default value you choose
        }
      });
  }

  changeLang(lang: string): void {
    // Store selected language in local storage
    window.localStorage.setItem('lang', lang);

    // Update URL with selected language
    const url = this.router.url;
    const updatedUrl = url.split('/').map(segment => {
      if (segment === 'hu' || segment === 'en' || segment === 'il') {
        return lang;
      }
      return segment;
    }).join('/');
    this.router.navigateByUrl(updatedUrl);

    // Set the selected language in the component
    this.selectedLanguage = lang;
    window.location.reload();
  }
}








