import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GovernmentListComponent } from './government-list.component';

describe('GovernmentListComponent', () => {
  let component: GovernmentListComponent;
  let fixture: ComponentFixture<GovernmentListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GovernmentListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GovernmentListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
