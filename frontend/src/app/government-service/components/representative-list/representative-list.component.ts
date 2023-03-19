import { Component, OnInit } from '@angular/core';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import {
  ActivatedRoute
} from '@angular/router';

import {
  Availability, GovernmentAdminModel,
  GovernmentRepresentativeService, GovernmentService
} from '../../../../../build/openapi/government-service';
import {Observable, Subject} from "rxjs";

@Component({
  selector: 'app-representative-list',
  templateUrl: './representative-list.component.html',
  styleUrls: ['./representative-list.component.css'],
})

export class RepresentativeListComponent implements OnInit {
  representatives: SanitizedRepresentativeAdminModel[] = [];
  currentLanguage?: string = 'hu';
  governments$: Observable<GovernmentAdminModel[]> = this.governmentService.renderAllGovernments();
  currentGovernmentId?: number;

  constructor(
    private readonly representativeService: GovernmentRepresentativeService,
    private readonly governmentService: GovernmentService,
    private sanitizer: DomSanitizer,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
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
  /*private listAllRepresentatives() {
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
  }*/

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
