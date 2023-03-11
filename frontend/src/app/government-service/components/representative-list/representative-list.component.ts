import {Component, OnInit} from '@angular/core';
import {
  Availability,
  GovernmentRepresentativeService
} from "../../../../../build/openapi/governemnt-service";
import {DomSanitizer, SafeUrl} from "@angular/platform-browser";

@Component({
  selector: 'app-representative-list',
  templateUrl: './representative-list.component.html',
  styleUrls: ['./representative-list.component.css']
})
export class RepresentativeListComponent implements OnInit {

  representatives: SanitizedRepresentativeModel[] = [];

  constructor(private readonly representativeService: GovernmentRepresentativeService, private sanitizer: DomSanitizer) {}

  ngOnInit(): void {
    this.listRepresentatives();
  }

  private listRepresentatives() {
    this.representativeService.renderAllRepresentatives().subscribe(
      data => {
        this.representatives = data.map(representative => ({
          ...representative,
          image: this.sanitizer.bypassSecurityTrustUrl(`data:image/png;base64,${representative.image}`)
        }));
      }
    );
  }
}

interface SanitizedRepresentativeModel {
  id?: string;
  name?: string;
  email?: string;
  phoneNumber?: string;
  address?: string;
  image: SafeUrl;
  jobTitle?: string;
  note?: string;
  availability?: Availability;
  government?: string;
  createdAt?: string;
  updatedAt?: string;
  createdBy?: string;
  updatedBy?: string;
}
