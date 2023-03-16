import { Component, OnInit } from '@angular/core';
import { GovernmentAdminModel, GovernmentService } from '../../../../../../build/openapi/government-service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-government-list',
  templateUrl: './government-list.component.html',
  styleUrls: ['./government-list.component.css']
})
export class GovernmentListComponent implements OnInit {

  public governments: GovernmentAdminModel[] = [];

  constructor(
    private readonly governmentService: GovernmentService,
    private readonly router: Router,
  ) {}

  ngOnInit(): void {
    this.governmentList();
  }

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

  goToGovernment(governmentId: string) {
    this.router.navigate(['/api/admin/gov-representatives', governmentId]);
  }
}



