import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {DomSanitizer, SafeUrl} from '@angular/platform-browser';
import {
  ActivatedRoute, Router
} from '@angular/router';

import {
  Availability,
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
  searchQuery: string = '';

  public introductionMenu?: string;
  public contactMenu?: string;
  public serviceMenu?: string;
  public questionMenu?: string;

  public searchPlaceholder?: string;
  public searchButton?: string;
  public governments: GovernmentTranslationModel[] = [];

  constructor(
    private readonly representativeService: GovernmentRepresentativeService,
    private readonly governmentService: GovernmentService,
    readonly menuService: MenuService,
    private sanitizer: DomSanitizer,
    private route: ActivatedRoute,
    private router: Router,
    private activatedRoute: ActivatedRoute,

    private cdr: ChangeDetectorRef
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

    this.activatedRoute.paramMap.subscribe(paramMap => {
      const languageShortName = paramMap.get('languageShortName');
      this.governmentService.renderAllGovernments(languageShortName!).subscribe(
        (response) => {
          // filter governments by languageShortName
          this.governments = response.filter(government => government.language_short_name === languageShortName);
        },
        (error) => {
          console.error(error);
        }
      );
    });

    this.cdr.detectChanges();
  }
  /*ngOnInit(): void {
    this.getMenuTranslation();
    this.listRepresentatives();
    this.cdr.detectChanges();
  }*/

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
    this.getMenuTranslation();

    // Update governments$ to only fetch governments for the current language
    this.governments$ = this.governmentService.renderAllGovernments(this.currentLanguage).pipe(
      map(governments => governments.filter(government => government.language_short_name === this.currentLanguage))
    );

    // Update the governments array after the governments$ observable emits new governments
    this.governments$.subscribe(governments => {
      this.governments = governments;
    });
  }

  private governmentList() {
    this.activatedRoute.paramMap.subscribe(paramMap => {
      const languageShortName = paramMap.get('languageShortName');
      this.governmentService.renderAllGovernments(languageShortName!).subscribe(
        (response) => {
          // filter governments by languageShortName
          this.governments = response.filter(government => government.language_short_name === languageShortName);
        },
        (error) => {
          console.error(error);
        }
      );
    });
  }

  /*changeLang(lang: string): void {
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
  this.getMenuTranslation();
  this.governmentList();
  this.cdr.detectChanges();
  // window.location.reload();
}*/

  onSearch(): void {
    console.log('Search query:', this.searchQuery);
    this.updateSearchQuery(this.searchQuery);
    this.cdr.detectChanges(); // add this line to detect changes
  }

  public updateSearchQuery(query: string): void {
    this.searchQuery = query;
    this.listAllRepresentatives(this.searchQuery);
    const queryParams = {search: this.searchQuery};
    this.router.navigate([], {relativeTo: this.route, queryParams: queryParams});
    this.governmentList();
  }

  private listRepresentatives() {
    this.route.params.subscribe(params => {
      const governmentId = params['governmentId'];
      if (governmentId) {
        this.currentGovernmentId = governmentId;
        this.listRepresentativesByGovId(this.currentLanguage!, this.currentGovernmentId!);
      } else {
        this.listAllRepresentatives(this.searchQuery!);
      }
    });
  }

  private listAllRepresentatives(searchQuery?: string) {
    this.representativeService.renderAllRepresentatives(this.currentLanguage!, searchQuery!).subscribe(
      (data) => {
        this.representatives = data.map(
          (representative) => {
            console.log("Representatives: " + JSON.stringify(representative));
            const government = representative.government;
            const reprTranslation = representative.representativeTranslation;
            return {
              ...representative,
              name: reprTranslation ? reprTranslation.name : '',
              address: reprTranslation ? reprTranslation.address : '',
              country: reprTranslation ? reprTranslation.country : '',
              jobTitle: reprTranslation ? reprTranslation.jobTitle : '',
              note: reprTranslation ? reprTranslation.note : '',
              secretNote: reprTranslation ? reprTranslation.secretNote : '',
              governmentName: government ? government.name : '',
              secretairat: reprTranslation ? reprTranslation.secretairat : '',
              image: this.sanitizer.bypassSecurityTrustUrl(`data:image/png;base64,${representative.image}`),
            };
          })
          .filter(repr =>
            !this.currentGovernmentId || repr.government?.id === this.currentGovernmentId
          )
          .filter(repr =>
            !searchQuery ||
            repr.name?.toLowerCase().includes(searchQuery.toLowerCase()) ||
            repr.address?.toLowerCase().includes(searchQuery.toLowerCase()) ||
            repr.country?.toLowerCase().includes(searchQuery.toLowerCase()) ||
            repr.jobTitle?.toLowerCase().includes(searchQuery.toLowerCase()) ||
            repr.note?.toLowerCase().includes(searchQuery.toLowerCase()) ||
            repr.secretNote?.toLowerCase().includes(searchQuery.toLowerCase()) ||
            repr.secretairat?.toLowerCase().includes(searchQuery.toLowerCase())
          );
      });
  }

  private listRepresentativesByGovId(currentLanguage: string, governmentId: number) {
    this.currentGovernmentId = governmentId;
    this.currentLanguage = currentLanguage;
    this.representativeService
      .findByGovernmentId(this.currentGovernmentId, this.currentLanguage)
      .subscribe(
        (data) => {
          this.representatives = data.map(
            (representative) => {
              console.log("Representatives: " + JSON.stringify(representative));
              const government = representative.government;
              const reprTranslation = representative.representativeTranslation;
              return {
                ...representative,
                name: reprTranslation ? reprTranslation.name : '',
                address: reprTranslation ? reprTranslation.address : '',
                country: reprTranslation ? reprTranslation.country : '',
                jobTitle: reprTranslation ? reprTranslation.jobTitle : '',
                note: reprTranslation ? reprTranslation.note : '',
                secretNote: reprTranslation ? reprTranslation.secretNote : '',
                governmentName: government ? government.name : '',
                secretairat: reprTranslation ? reprTranslation.secretairat : '',
                image: this.sanitizer.bypassSecurityTrustUrl(`data:image/png;base64,${representative.image}`),
              };
            });
        });
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

    this.menuService.renderAllMenuTranslations(this.currentLanguage, 'search_placeholder')
      .subscribe((data: MenuTranslationModel[]) => {
        if (data.length > 0) {
          this["searchPlaceholder"] = data[0].translationValue!;
        } else {
          this.searchPlaceholder = 'Data search ...'; // Or any default value you choose
        }
      });

    this.menuService.renderAllMenuTranslations(this.currentLanguage, 'search_button')
      .subscribe((data: MenuTranslationModel[]) => {
        if (data.length > 0) {
          this.searchButton = data[0].translationValue!;
        } else {
          this.searchButton = 'Search'; // Or any default value you choose
        }
      });

    this.menuService.renderAllMenuTranslations(this.currentLanguage, 'introduction')
      .subscribe((data: MenuTranslationModel[]) => {
        if (data.length > 0) {
          this.introductionMenu = data[0].translationValue!;
        } else {
          this.introductionMenu = 'Introduction'; // Or any default value you choose
        }
      });

    this.menuService.renderAllMenuTranslations(this.currentLanguage, 'contact')
      .subscribe((data: MenuTranslationModel[]) => {
        if (data.length > 0) {
          this.contactMenu = data[0].translationValue!;
        } else {
          this.contactMenu = 'Contact'; // Or any default value you choose
        }
      });

    this.menuService.renderAllMenuTranslations(this.currentLanguage, 'service')
      .subscribe((data: MenuTranslationModel[]) => {
        if (data.length > 0) {
          this.serviceMenu = data[0].translationValue!;
        } else {
          this.serviceMenu = 'Services'; // Or any default value you choose
        }
      });

    this.menuService.renderAllMenuTranslations(this.currentLanguage, 'question')
      .subscribe((data: MenuTranslationModel[]) => {
        if (data.length > 0) {
          this.questionMenu = data[0].translationValue!;
        } else {
          this.questionMenu = 'Frequently asked questions'; // Or any default value you choose
        }
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
