import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'frontend';
  selectedLanguage: string;

  constructor(private router: Router) {
    this.selectedLanguage = window.localStorage.getItem('lang') || 'hu';
  }

  ngOnInit(): void {}

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
  }
}








