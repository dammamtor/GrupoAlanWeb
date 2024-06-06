import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserNotificacionesComponent } from './user-notificaciones.component';

describe('UserNotificacionesComponent', () => {
  let component: UserNotificacionesComponent;
  let fixture: ComponentFixture<UserNotificacionesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UserNotificacionesComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(UserNotificacionesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
