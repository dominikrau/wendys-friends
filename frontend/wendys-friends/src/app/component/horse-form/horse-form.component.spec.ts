import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HorseFormComponent } from './horse-form.component';

describe('HorseFormComponent', () => {
  let component: HorseFormComponent;
  let fixture: ComponentFixture<HorseFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HorseFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HorseFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
