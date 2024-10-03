import { TestBed } from '@angular/core/testing';

import { VooService } from './voo.service';

describe('VooService', () => {
  let service: VooService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(VooService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
