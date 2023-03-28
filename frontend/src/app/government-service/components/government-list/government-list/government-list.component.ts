import { Component, OnInit } from '@angular/core';
import {
  GovernmentAdminModel,
  GovernmentService,
  GovernmentTranslationModel
} from '../../../../../../build/openapi/government-service';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-government-list',
  templateUrl: './government-list.component.html',
  styleUrls: ['./government-list.component.css']
})
export class GovernmentListComponent implements OnInit {

  public governments: GovernmentTranslationModel[] = [];
  public languageShortName?: string;

  constructor(
      private readonly governmentService: GovernmentService,
      private readonly activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.governmentList();
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
}
