import { TestBed } from '@angular/core/testing';

import { AeroportoService } from './aeroporto.service';

describe('AeroportoService', () => {
  let service: AeroportoService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AeroportoService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
