import {Component, NgModule, OnInit} from '@angular/core';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import {
  ActivatedRoute, Router
} from '@angular/router';

import {
  Availability, GovernmentAdminModel,
  GovernmentRepresentativeService, GovernmentService, GovernmentTranslationModel, MenuService, MenuTranslationModel
} from '../../../../../build/openapi/government-service';
import {map, Observable} from "rxjs";

@Component({
  selector: 'app-representative-list',
  templateUrl: './representative-list.component.html',
  styleUrls: ['./representative-list.component.css'],
})

export class RepresentativeListComponent implements OnInit {
  representatives: SanitizedRepresentativeAdminModel[] = [];
  currentLanguage: string;
  governments$: Observable<GovernmentTranslationModel[]>;
  currentGovernmentId?: number;
  public allTranslation?: string;
  public modifyButton?: string;
  public deleteButton?: string;

  constructor(
    private readonly representativeService: GovernmentRepresentativeService,
    private readonly governmentService: GovernmentService,
    readonly menuService: MenuService,
    private sanitizer: DomSanitizer,
    private route: ActivatedRoute,
    private router: Router

  ) {
    // Initialize currentLanguage to the language stored in local storage, or to 'hu' if it's not set yet
    this.currentLanguage = window.localStorage.getItem('lang') || 'hu';
    // Update governments$ to only fetch governments for the current language
    this.governments$ = this.governmentService.renderAllGovernments(this.currentLanguage).pipe(
        map(governments => governments.filter(government => government.language_short_name === this.currentLanguage))
    );
  }

  ngOnInit(): void {
    this.getMenuTranslation();
    this.listRepresentatives();
  }

  public getMenuTranslation() {
    this.menuService.renderAllMenuTranslations(this.currentLanguage, 'all')
      .subscribe((data: MenuTranslationModel[]) => {
        if (data.length > 0) {
          this["allTranslation"] = data[0].translationValue!;
        } else {
          this.allTranslation = 'All'; // Or any default value you choose
        }
      });
    this.menuService.renderAllMenuTranslations(this.currentLanguage, 'modify_button')
      .subscribe((data: MenuTranslationModel[]) => {
        if (data.length > 0) {
          this.modifyButton = data[0].translationValue!;
        } else {
          this.modifyButton = 'Modify'; // Or any default value you choose
        }
      });
    this.menuService.renderAllMenuTranslations(this.currentLanguage, 'delete_button')
      .subscribe((data: MenuTranslationModel[]) => {
        if (data.length > 0) {
          this.deleteButton = data[0].translationValue!;
        } else {
          this.deleteButton = 'Delete'; // Or any default value you choose
        }
      });
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
    this.representativeService.renderAllRepresentatives(this.currentLanguage!).subscribe(
      (data) => {
      this.representatives = data.map((representative) => {
        console.log("Representatives: " + JSON.stringify(representative));
        const government = representative.government;
        const reprTranslation = representative.representativeTranslation;
        return {
          ...representative,
          name: reprTranslation? reprTranslation.name: '',
          address: reprTranslation? reprTranslation.address: '',
          country: reprTranslation? reprTranslation.country: '',
          jobTitle: reprTranslation? reprTranslation.jobTitle: '',
          note: reprTranslation? reprTranslation.note: '',
          secretNote: reprTranslation? reprTranslation.secretNote: '',
          governmentName: government ? government.name : '',
          secretairat: reprTranslation? reprTranslation.secretairat: '',
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
      .subscribe(
        (data) => {
        this.representatives = data.map((representative) => {
          console.log("Representatives: " + JSON.stringify(representative));
          const government = representative.government;
          const reprTranslation = representative.representativeTranslation;
          return {
            ...representative,
            name: reprTranslation? reprTranslation.name: '',
            address: reprTranslation? reprTranslation.address: '',
            country: reprTranslation? reprTranslation.country: '',
            jobTitle: reprTranslation? reprTranslation.jobTitle: '',
            note: reprTranslation? reprTranslation.note: '',
            secretNote: reprTranslation? reprTranslation.secretNote: '',
            governmentName: government ? government.name : '',
            secretairat: reprTranslation? reprTranslation.secretairat: '',
            image: this.sanitizer.bypassSecurityTrustUrl(`data:image/png;base64,${representative.image}`),
          };
        });
      });
  }
}

  interface SanitizedRepresentativeAdminModel {
  id?: string;
  name?: string;
  email?: string;
  phoneNumber?: string;
  address?: string;
  country?: string,
  image: SafeUrl;
  jobTitle?: string;
  note?: string;
  availability?: Availability;
  governmentName?: string;
  secretairat?: string;
  secretNote?: string;
  createdAt?: string;
  updatedAt?: string;
  createdBy?: string;
  updatedBy?: string;
}
