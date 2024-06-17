import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserHistorialComponent } from './user-historial.component';

describe('UserHistorialComponent', () => {
  let component: UserHistorialComponent;
  let fixture: ComponentFixture<UserHistorialComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UserHistorialComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(UserHistorialComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
