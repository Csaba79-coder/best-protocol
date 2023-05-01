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

/**
 * Component that displays a list of sanitized government representatives.
 * The list is fetched from the API and displayed in a template.
 */
@Component({
  selector: 'app-representative-list',
  templateUrl: './representative-list.component.html',
  styleUrls: ['./representative-list.component.css'],
})

export class RepresentativeListComponent implements OnInit {
  /**
   * The list of sanitized government representatives fetched from the API.
   */
  representatives: SanitizedRepresentativeAdminModel[] = [];
  /**
   * The current language of the component.
   */
  currentLanguage: string;
  /**
   * An observable that emits a list of government translations in the current language.
   */
  governments$: Observable<GovernmentTranslationModel[]>;
  /**
   * The ID of the current government.
   */
  currentGovernmentId?: number;
  /**
   * The translation of the "all" button.
   */
  public allTranslation?: string;
  /**
   * The translation of the "modify" button.
   */
  public modifyButton?: string;
  /**
   * The translation of the "delete" button.
   */
  public deleteButton?: string;
  /**
   * The search query entered by the user.
   */
  searchQuery: string = '';
  /**
   * The translation of the "Introduction" menu item.
   */
  public introductionMenu?: string;
  /**
   * The translation of the "Contact" menu item.
   */
  public contactMenu?: string;
  /**
   * The translation of the "Service" menu item.
   */
  public serviceMenu?: string;
  /**
   * The translation of the "Question" menu item.
   */
  public questionMenu?: string;
  /**
   * The placeholder text of the search input field.
   */
  public searchPlaceholder?: string;
  /**
   * The translation of the "Search" button.
   */
  public searchButton?: string;
  /**
   * The list of government translations in the current language.
   */
  public governments: GovernmentTranslationModel[] = [];

  /**
   * Creates a new instance of RepresentativeListComponent.
   * @param representativeService The service that fetches the list of government representatives.
   * @param governmentService The service that fetches the list of governments.
   * @param menuService The service that fetches the translations of the menu items.
   * @param sanitizer The Angular sanitizer used to sanitize the image URLs.
   * @param route The Angular route used to retrieve the route parameters.
   * @param router The Angular router used to navigate to other routes.
   * @param activatedRoute The Angular route used to retrieve the query parameters.
   * @param cdr The Angular change detector used to detect changes in the component.
   */
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

  /**
   * Lifecycle hook that is called after Angular has initialized all data-bound properties
   * of the component. It is used to perform initialization tasks such as fetching data
   * from a server, setting up event listeners, or subscribing to observables.
   *
   * In this component, ngOnInit() does the following:
   * - calls getMenuTranslation() and listRepresentatives() to initialize the component's data
   * - subscribes to the paramMap observable to get the current language from the URL
   * - filters the list of governments by language and saves them to the governments array
   * - subscribes to the queryParams and params observables to update the list of representatives
   * - detects changes to the component's view and updates it
   */
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

    this.activatedRoute.queryParams.subscribe(queryParams => {
      this.searchQuery = queryParams['search'];
      this.listRepresentatives();
    });

    this.activatedRoute.params.subscribe(params => {
      this.currentGovernmentId = params['governmentId'];
      this.listRepresentatives();
    });

    this.cdr.detectChanges();
  }

  /**
   * Updates the language of the component and its dependencies to the specified language.
   *
   * This method does the following:
   * - sets the currentLanguage property to the selected language
   * - saves the currentLanguage to local storage
   * - updates the URL to reflect the new language
   * - updates the list of representatives and the menu translation
   * - updates the list of governments by subscribing to a new observable that filters them by language
   */
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

  /**

   Retrieves a list of governments based on the language specified in the route parameter.
   Filters the response to only include governments with the specified language_short_name.
   Sets the governments property to the filtered list of governments.
   @private
   */
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

  /**

   Called when the user performs a search. Updates the searchQuery property and calls listAllRepresentatives.
   Also updates the URL with the new search query.
   @public
   */
  onSearch(): void {
    console.log('Search query:', this.searchQuery);
    this.updateSearchQuery(this.searchQuery);
    this.cdr.detectChanges(); // add this line to detect changes
  }

  /**

   Updates the searchQuery property with the provided query and calls listAllRepresentatives to update the displayed data.
   Also updates the URL with the new search query and calls governmentList to update the list of governments.
   @public
   @param query The search query entered by the user.
   */
  public updateSearchQuery(query: string): void {
    this.searchQuery = query;
    this.listAllRepresentatives(this.searchQuery);
    const queryParams = {search: this.searchQuery};
    this.router.navigate([], {relativeTo: this.route, queryParams: queryParams});
    this.governmentList();
  }

  /**

   Lists all representatives if governmentId is not provided, otherwise lists representatives by governmentId.
   If searchQuery is provided, filters the results based on the provided query.
   @param searchQuery Optional search query to filter the list of representatives
   */
  private listRepresentatives() {
    this.route.params.subscribe(params => {
      const governmentId = params['governmentId'];
      if (governmentId) {
        this.currentGovernmentId = governmentId;
        this.listRepresentativesByGovId(this.currentGovernmentId!, this.searchQuery!);
      } else {
        this.listAllRepresentatives(this.searchQuery!);
      }
    });
  }

  /**

   Lists all representatives regardless of government if searchQuery is not provided,
   otherwise lists all representatives that match the provided searchQuery.
   @param searchQuery Optional search query to filter the list of representatives
   */
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
            (!searchQuery || this.entityMatchesSearchCriteria(repr, searchQuery)) // filter by searchQuery if provided
          );
      });
  }

  /**

   Lists all representatives that belong to a specific government.
   If searchQuery is provided, filters the results based on the provided query.
   @param governmentId ID of the government whose representatives will be listed
   @param searchQuery Optional search query to filter the list of representatives
   */
  private listRepresentativesByGovId(governmentId: number, searchQuery?: string) {
    this.currentGovernmentId = governmentId;
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
            })
            .filter(repr =>
              (!searchQuery || this.entityMatchesSearchCriteria(repr, searchQuery)) // filter by searchQuery if provided
            )
        });
    this.cdr.detectChanges();
  }

  /**

   Checks if a given entity matches a search query. The search query is compared against several
   fields of the entity to see if there is a match. If there is a match, the function returns true.
   If there is no match, the function returns false.
   There is a special check in case of null fields (if translation does not exist!)
   @param model - The entity to be checked for a match with the search query.
   @param search - The search query to be used to search for a match.
   @returns true if the entity matches the search query, false otherwise.
   */
  private entityMatchesSearchCriteria(model: any, search: string): boolean {
    const lowercaseSearch = search.toLowerCase();
    if (model.governmentName && model.governmentName.toLowerCase().includes(lowercaseSearch)) {
      return true;
    }
    if (model.email && model.email.toLowerCase().includes(lowercaseSearch)) {
      return true;
    }
    // removes all hyphens from the phone number
    if (model.phoneNumber) {
      const phoneWithoutHyphens = model.phoneNumber.replace(/-/g, "");
      if (phoneWithoutHyphens.includes(search)) {
        return true;
      }
    }
    if (model.name && model.name.toLowerCase().includes(lowercaseSearch)) {
      return true;
    }
    if (model.address && model.address.toLowerCase().includes(lowercaseSearch)) {
      return true;
    }
    if (model.country && model.country.toLowerCase().includes(lowercaseSearch)) {
      return true;
    }
    if (model.jobTitle && model.jobTitle.toLowerCase().includes(lowercaseSearch)) {
      return true;
    }
    if (model.note && model.note.toLowerCase().includes(lowercaseSearch)) {
      return true;
    }
    if (model.secretNote && model.secretNote.toLowerCase().includes(lowercaseSearch)) {
      return true;
    }
    if (model.secretairat && model.secretairat.toLowerCase().includes(lowercaseSearch)) {
      return true;
    }
    return false;
  }

  /**

   Retrieves translations for menu items from the menuService and sets their corresponding class properties.
   If no translation is found for a given item, a default value is assigned.
   The language is retrieved from the localStorage.
   @returns void
   */
  public getMenuTranslation() {
    // Retrieves translation for "All" menu item
    this.menuService.renderAllMenuTranslations(this.currentLanguage, 'all')
      .subscribe((data: MenuTranslationModel[]) => {
        if (data.length > 0) {
          this["allTranslation"] = data[0].translationValue!;
        } else {
          this.allTranslation = 'All'; // Or any default value you choose
        }
      });
    // Retrieves translation for "Modify" button
    this.menuService.renderAllMenuTranslations(this.currentLanguage, 'modify_button')
      .subscribe((data: MenuTranslationModel[]) => {
        if (data.length > 0) {
          this.modifyButton = data[0].translationValue!;
        } else {
          this.modifyButton = 'Modify'; // Or any default value you choose
        }
      });
    // Retrieves translation for "Delete" button
    this.menuService.renderAllMenuTranslations(this.currentLanguage, 'delete_button')
      .subscribe((data: MenuTranslationModel[]) => {
        if (data.length > 0) {
          this.deleteButton = data[0].translationValue!;
        } else {
          this.deleteButton = 'Delete'; // Or any default value you choose
        }
      });
    // Retrieves translation for search input placeholder
    this.menuService.renderAllMenuTranslations(this.currentLanguage, 'search_placeholder')
      .subscribe((data: MenuTranslationModel[]) => {
        if (data.length > 0) {
          this["searchPlaceholder"] = data[0].translationValue!;
        } else {
          this.searchPlaceholder = 'Data search ...'; // Or any default value you choose
        }
      });
    // Retrieves translation for "Search" button
    this.menuService.renderAllMenuTranslations(this.currentLanguage, 'search_button')
      .subscribe((data: MenuTranslationModel[]) => {
        if (data.length > 0) {
          this.searchButton = data[0].translationValue!;
        } else {
          this.searchButton = 'Search'; // Or any default value you choose
        }
      });
    // Retrieves translation for "Introduction" menu item
    this.menuService.renderAllMenuTranslations(this.currentLanguage, 'introduction')
      .subscribe((data: MenuTranslationModel[]) => {
        if (data.length > 0) {
          this.introductionMenu = data[0].translationValue!;
        } else {
          this.introductionMenu = 'Introduction'; // Or any default value you choose
        }
      });
    // Retrieves translation for "Contact" menu item
    this.menuService.renderAllMenuTranslations(this.currentLanguage, 'contact')
      .subscribe((data: MenuTranslationModel[]) => {
        if (data.length > 0) {
          this.contactMenu = data[0].translationValue!;
        } else {
          this.contactMenu = 'Contact'; // Or any default value you choose
        }
      });
    // Retrieves translation for "Services" menu item
    this.menuService.renderAllMenuTranslations(this.currentLanguage, 'service')
      .subscribe((data: MenuTranslationModel[]) => {
        if (data.length > 0) {
          this.serviceMenu = data[0].translationValue!;
        } else {
          this.serviceMenu = 'Services'; // Or any default value you choose
        }
      });
    // Retrieves translation for "Frequently Asked Questions" menu item
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

/**

 Interface for the sanitized representative admin model, which represents the sanitized version
 of the full representative admin model without any sensitive or unnecessary data. This interface
 includes the following properties:
 @param {string | undefined} id - The ID of the representative admin.
 @param {string | undefined} name - The name of the representative admin.
 @param {string | undefined} email - The email address of the representative admin.
 @param {string | undefined} phoneNumber - The phone number of the representative admin.
 @param {string | undefined} address - The address of the representative admin.
 @param {string | undefined} country - The country of the representative admin.
 @param {SafeUrl} image - The safe URL of the representative admin's image.
 @param {string | undefined} jobTitle - The job title of the representative admin.
 @param {string | undefined} note - The note associated with the representative admin.
 @param {Availability | undefined} availability - The availability status of the representative admin.
 @param {string | undefined} governmentName - The government name of the representative admin.
 @param {string | undefined} secretairat - The secretariat information of the representative admin.
 @param {string | undefined} secretNote - The secret note associated with the representative admin.
 @param {string | undefined} createdAt - The date and time when the representative admin was created.
 @param {string | undefined} updatedAt - The date and time when the representative admin was last updated.
 @param {string | undefined} createdBy - The user who created the representative admin.
 @param {string | undefined} updatedBy - The user who last updated the representative admin.
 */
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
