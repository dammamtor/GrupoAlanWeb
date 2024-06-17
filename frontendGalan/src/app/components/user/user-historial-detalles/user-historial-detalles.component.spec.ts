import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserHistorialDetallesComponent } from './user-historial-detalles.component';

describe('UserHistorialDetallesComponent', () => {
  let component: UserHistorialDetallesComponent;
  let fixture: ComponentFixture<UserHistorialDetallesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UserHistorialDetallesComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(UserHistorialDetallesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
