import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HorseDialogComponent } from './horse-dialog.component';

describe('HorseDialogComponent', () => {
  let component: HorseDialogComponent;
  let fixture: ComponentFixture<HorseDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HorseDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HorseDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
