import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AutCadComponent } from './aut-cad.component';

describe('AutCadComponent', () => {
  let component: AutCadComponent;
  let fixture: ComponentFixture<AutCadComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AutCadComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AutCadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
