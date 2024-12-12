import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfirmarCheckinReservaComponent } from './confirmar-checkin-reserva.component';

describe('ConfirmarCheckinReservaComponent', () => {
  let component: ConfirmarCheckinReservaComponent;
  let fixture: ComponentFixture<ConfirmarCheckinReservaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ConfirmarCheckinReservaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ConfirmarCheckinReservaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
