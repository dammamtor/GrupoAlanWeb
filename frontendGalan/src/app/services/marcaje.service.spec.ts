import { TestBed } from '@angular/core/testing';

import { MarcajeService } from './marcaje.service';

describe('MarcajeService', () => {
  let service: MarcajeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MarcajeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
