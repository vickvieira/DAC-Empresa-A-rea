import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HomeFuncComponent } from './home-func.component';

describe('HomeFuncComponent', () => {
  let component: HomeFuncComponent;
  let fixture: ComponentFixture<HomeFuncComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HomeFuncComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HomeFuncComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
