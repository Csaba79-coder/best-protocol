import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {
  GovernmentService,
  GovernmentTranslationModel
} from '../../../../../../build/openapi/government-service';
import {ActivatedRoute, Router} from '@angular/router';
import {BehaviorSubject} from "rxjs";

@Component({
  selector: 'app-government-list',
  templateUrl: './government-list.component.html',
  styleUrls: ['./government-list.component.css']
})
export class GovernmentListComponent implements OnInit {


  public languageShortName?: string;

  constructor(
    private activatedRoute: ActivatedRoute,

  ) {
  }

  ngOnInit(): void {
  }


}

  /*public governments: GovernmentTranslationModel[] = [];
  public languageShortName?: BehaviorSubject<string>;

  constructor(
    private readonly governmentService: GovernmentService,
    private readonly activatedRoute: ActivatedRoute,
    private readonly router: Router,
    private cdr: ChangeDetectorRef
  ) {
    // initialize the languageShortName$ with the default value
    this.languageShortName = new BehaviorSubject<string>('hu');
  }

  ngOnInit(): void {
    // subscribe to paramMap observable
    this.activatedRoute.paramMap.subscribe((params) => {
      // set the new value of languageShortName$
      this.languageShortName?.next(params.get('languageShortName') || 'hu');
    });

    // subscribe to languageShortName$ observable and update the list every time it changes
    this.languageShortName?.subscribe((languageShortName) => {
      this.governmentService.renderAllGovernments(languageShortName).subscribe(
        (response) => {
          // filter governments by languageShortName
          this.governments = response.filter(
            (government) => government.language_short_name === languageShortName
          );
          this.cdr.detectChanges();

          // navigate to the same route with a different URL parameter to display the new data
          this.router.navigate([], {
            relativeTo: this.activatedRoute,
            queryParams: { languageShortName },
            queryParamsHandling: 'merge'
          });
        },
        (error) => {
          console.error(error);
        }
      );
    });
  }
}*/

  /*public governments: GovernmentTranslationModel[] = [];
  public languageShortName?: string;

  constructor(
      private readonly governmentService: GovernmentService,
      private readonly activatedRoute: ActivatedRoute,
      private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    // subscribe to paramMap observable
    this.activatedRoute.paramMap.subscribe((params) => {
      // set languageShortName property to the new value or fallback to 'en'
      this.languageShortName = params.get('languageShortName') || 'hu';
      // call governmentList method to update the list
      this.governmentList();
      this.cdr.detectChanges();
    });
  }

  private governmentList() {
    this.governmentService.renderAllGovernments(this.activatedRoute.snapshot.paramMap.get('languageShortName')!).subscribe(
        (response) => {
          // filter governments by languageShortName
          this.governments = response.filter(government => government.language_short_name === this.languageShortName);
        },
        (error) => {
          console.error(error);
        }
    );
  }
}*/
