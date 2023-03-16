import { Component, OnInit } from '@angular/core';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import {ActivatedRoute, RouterModule} from '@angular/router';

import {
  Availability, GovernmentAdminModel,
  GovernmentRepresentativeService, GovernmentService
} from '../../../../../build/openapi/government-service';

@Component({
  selector: 'app-representative-list',
  templateUrl: './representative-list.component.html',
  styleUrls: ['./representative-list.component.css'],
})
export class RepresentativeListComponent implements OnInit {
  representatives: SanitizedRepresentativeAdminModel[] = [];
  governments: GovernmentAdminModel[] = [];
  currentGovernmentId?: number;

  constructor(
      private readonly representativeService: GovernmentRepresentativeService,
      private readonly governmentService: GovernmentService,
      private sanitizer: DomSanitizer,
      private route: ActivatedRoute
      // private readonly router: Router
  ) {}

  ngOnInit(): void {
    this.governmentList();
    this.listRepresentatives();
  }

  private listRepresentatives() {
    const governmentId = this.route.snapshot.params['governmentId'];
    if (governmentId) {
      this.listRepresentativesByGovId();
    } else {
      this.listAllRepresentatives();
    }
  }

  private listAllRepresentatives() {
    this.representativeService.renderAllRepresentatives().subscribe((data) => {
      this.representatives = data.map((representative) => {
        const government = representative.government;
        return {
          ...representative,
          governmentName: government ? government.name : '',
          image: this.sanitizer.bypassSecurityTrustUrl(`data:image/png;base64,${representative.image}`),
        };
      });
    });
  }

  private listRepresentativesByGovId() {
    const governmentId = this.route.snapshot.params['governmentId'];
    this.currentGovernmentId = +governmentId!;
    this.representativeService
      .findByGovernmentId(this.currentGovernmentId)
      .subscribe((data) => {
        this.representatives = data.map((representative) => {
          const government = representative.government;
          return {
            ...representative,
            government: government ? government.name : '',
            image: this.sanitizer.bypassSecurityTrustUrl(`data:image/png;base64,${representative.image}`),
          };
        });
      });
  }

  /*private listRepresentativesByGovId() {
    const governmentId = this.route.snapshot.queryParamMap.get('governmentId');
    this.currentGovernmentId = +governmentId!;
    this.representativeService
      .findByGovernmentId(this.currentGovernmentId)
      .subscribe((data) => {
        this.representatives = data.map((representative) => {
          const government = representative.government;
          return {
            ...representative,
            government: government ? government.name : '',
            image: this.sanitizer.bypassSecurityTrustUrl(`data:image/png;base64,${representative.image}`),
          };
        });
      });
  }*/

  private governmentList() {
    this.governmentService.renderAllGovernments().subscribe(
      (response) => {
        this.governments = response;
      },
      (error) => {
        console.error(error);
      }
    );
  }
}

interface SanitizedRepresentativeAdminModel {
  id?: string;
  name?: string;
  email?: string;
  phoneNumber?: string;
  address?: string;
  image: SafeUrl;
  jobTitle?: string;
  note?: string;
  availability?: Availability;
  governmentName?: string;
  createdAt?: string;
  updatedAt?: string;
  createdBy?: string;
  updatedBy?: string;
}
