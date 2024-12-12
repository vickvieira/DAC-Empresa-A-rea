import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavbarFuncComponent } from './navbar-func.component';

describe('NavbarFuncComponent', () => {
  let component: NavbarFuncComponent;
  let fixture: ComponentFixture<NavbarFuncComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NavbarFuncComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(NavbarFuncComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
