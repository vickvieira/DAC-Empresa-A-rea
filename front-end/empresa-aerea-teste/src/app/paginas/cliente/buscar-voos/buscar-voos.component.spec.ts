import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BuscarVoosComponent } from './buscar-voos.component';

describe('BuscarVoosComponent', () => {
  let component: BuscarVoosComponent;
  let fixture: ComponentFixture<BuscarVoosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BuscarVoosComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BuscarVoosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
