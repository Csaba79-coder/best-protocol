import { Component, OnInit } from '@angular/core';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import {
  ActivatedRoute,
  PRIMARY_OUTLET,
  Router,
  UrlSegment,
  UrlSegmentGroup,
  UrlTree
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
  private governmentsSubject = new Subject<any>();
  governments$: Observable<GovernmentAdminModel[]> = this.governmentService.renderAllGovernments();
  currentGovernmentId?: number;


  constructor(
    private readonly representativeService: GovernmentRepresentativeService,
    private readonly governmentService: GovernmentService,
    private sanitizer: DomSanitizer,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.governmentList();
    this.listRepresentatives();
  }

  private listRepresentatives() {
    this.route.params.subscribe(params => {
      const governmentId = params['governmentId'];
      if (governmentId) {
        this.listRepresentativesByGovId(governmentId);
      } else {
        this.listAllRepresentatives();
      }
    });
  }

  private listAllRepresentatives() {
    this.representativeService.renderAllRepresentatives().subscribe((data) => {
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

  private listRepresentativesByGovId(governmentId: number) {
    this.currentGovernmentId = governmentId;
    this.representativeService
      .findByGovernmentId(this.currentGovernmentId)
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

  private governmentList() {
    this.governmentService.renderAllGovernments().subscribe(
      (response) => {
        console.log("Representatives: " + JSON.stringify(response));
        this.governmentsSubject.next(response);
      },
      (error) => {
        console.error(error);
      }
    );
  }

  isActive(url: string): boolean {
    const currentUrlTree: UrlTree = this.router.parseUrl(this.router.url);
    const targetUrlTree: UrlTree = this.router.createUrlTree([url]);
    const currentUrlSegmentGroup: UrlSegmentGroup = currentUrlTree.root.children[PRIMARY_OUTLET];
    const targetUrlSegmentGroup: UrlSegmentGroup = targetUrlTree.root.children[PRIMARY_OUTLET];
    const currentSegments: UrlSegment[] = currentUrlSegmentGroup.segments;
    const targetSegments: UrlSegment[] = targetUrlSegmentGroup.segments;
    const length: number = Math.min(currentSegments.length, targetSegments.length);

    for (let i = 0; i < length; i++) {
      if (currentSegments[i].path !== targetSegments[i].path) {
        return false;
      }
    }

    return targetSegments.length <= currentSegments.length;
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
  secretairat?: string;
  createdAt?: string;
  updatedAt?: string;
  createdBy?: string;
  updatedBy?: string;
}
