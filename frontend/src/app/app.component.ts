import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'frontend';
  selectedLanguage: string = '';

  ngOnInit(): void {

  }

  constructor() {
    // this.router.navigate(['/hu/admin/gov-representatives/government', governmentId]);
    this.selectedLanguage = window.localStorage.getItem('lang') || 'hu';
  }

  changeLang(lang: string): void {
    this.selectedLanguage = lang;
  }
}

