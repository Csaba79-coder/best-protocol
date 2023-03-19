import {Component, NgModule, OnInit} from '@angular/core';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import {
  ActivatedRoute, Router
} from '@angular/router';

import {
  Availability, GovernmentAdminModel,
  GovernmentRepresentativeService, GovernmentService
} from '../../../../../build/openapi/government-service';
import {Observable} from "rxjs";

@Component({
  selector: 'app-representative-list',
  templateUrl: './representative-list.component.html',
  styleUrls: ['./representative-list.component.css'],
})

export class RepresentativeListComponent implements OnInit {
  representatives: SanitizedRepresentativeAdminModel[] = [];
  currentLanguage: string;
  governments$: Observable<GovernmentAdminModel[]> = this.governmentService.renderAllGovernments();
  currentGovernmentId?: number;

  constructor(
    private readonly representativeService: GovernmentRepresentativeService,
    private readonly governmentService: GovernmentService,
    private sanitizer: DomSanitizer,
    private route: ActivatedRoute,
    private router: Router
  ) {
    // Initialize currentLanguage to the language stored in local storage, or to 'hu' if it's not set yet
    this.currentLanguage = window.localStorage.getItem('lang') || 'hu';
  }


  ngOnInit(): void {
    this.listRepresentatives();
  }

  changeLang(lang: string): void {
    // Set currentLanguage to the selected language
    this.currentLanguage = lang;
    // Save currentLanguage to local storage
    window.localStorage.setItem('lang', lang);
    // Update the URL with the new language
    const url = this.router.url;
    const updatedUrl = url.split('/').map(segment => {
      if (segment === 'hu' || segment === 'en' || segment === 'il') {
        return lang;
      }
      return segment;
    }).join('/');
    this.router.navigateByUrl(updatedUrl);
    // Update the displayed data
    this.listRepresentatives();
  }

  private listRepresentatives() {
    this.route.params.subscribe(params => {
      const governmentId = params['governmentId'];
      if (governmentId) {
        this.currentGovernmentId = governmentId;
        this.listRepresentativesByGovId(this.currentLanguage!, this.currentGovernmentId!);
      } else {
        this.listAllRepresentatives();
      }
    });
  }

  private listAllRepresentatives() {
    this.representativeService.renderAllRepresentatives(this.currentLanguage!).subscribe((data) => {
      this.representatives = data.map((representative) => {
        console.log("Representatives: " + JSON.stringify(representative));
        const government = representative.government;
        return {
          ...representative,
          governmentName: government ? government.name : '',
          image: this.sanitizer.bypassSecurityTrustUrl(`data:image/png;base64,${representative.image}`),
        };
      });
    });
  }

  private listRepresentativesByGovId(currentLanguage: string, governmentId: number) {
    this.currentGovernmentId = governmentId;
    this.currentLanguage = currentLanguage;
    this.representativeService
      .findByGovernmentId(this.currentGovernmentId, this.currentLanguage)
      .subscribe((data) => {
        this.representatives = data.map((representative) => {
          console.log("Representatives: " + JSON.stringify(representative));
          const government = representative.government;
          return {
            ...representative,
            governmentName: government ? government.name : '',
            image: this.sanitizer.bypassSecurityTrustUrl(`data:image/png;base64,${representative.image}`),
          };
        });
      });
  }
}

  interface SanitizedRepresentativeAdminModel {
  id?: string;
  name?: string;
  lang?: string;
  email?: string;
  phoneNumber?: string;
  address?: string;
  image: SafeUrl;
  jobTitle?: string;
  note?: string;
  availability?: Availability;
  governmentName?: string;
  secretairat?: string;
  createdAt?: string;
  updatedAt?: string;
  createdBy?: string;
  updatedBy?: string;
}
