import { TestBed } from '@angular/core/testing';
import { CanActivateFn } from '@angular/router';

import { authNaoLogadoGuard } from './auth-nao-logado.guard';

describe('authNaoLogadoGuard', () => {
  const executeGuard: CanActivateFn = (...guardParameters) => 
      TestBed.runInInjectionContext(() => authNaoLogadoGuard(...guardParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeGuard).toBeTruthy();
  });
});
