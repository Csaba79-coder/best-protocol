import {Component, OnInit} from '@angular/core';
import {GovernmentRepresentativeService} from "../../../../../build/openapi/governemnt-service";

@Component({
  selector: 'app-representative-list',
  templateUrl: './representative-list.component.html',
  styleUrls: ['./representative-list.component.css']
})
export class RepresentativeListComponent implements OnInit {

  $representatives = this.representativeService.renderAllRepresentatives()
  constructor(private readonly representativeService: GovernmentRepresentativeService) {}

  ngOnInit(): void {
  }
}
