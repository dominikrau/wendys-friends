import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OwnerShowHorsesComponent } from './owner-show-horses.component';

describe('OwnerShowHorsesComponent', () => {
  let component: OwnerShowHorsesComponent;
  let fixture: ComponentFixture<OwnerShowHorsesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OwnerShowHorsesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OwnerShowHorsesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
