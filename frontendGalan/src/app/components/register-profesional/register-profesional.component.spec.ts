import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegisterProfesionalComponent } from './register-profesional.component';

describe('RegisterProfesionalComponent', () => {
  let component: RegisterProfesionalComponent;
  let fixture: ComponentFixture<RegisterProfesionalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RegisterProfesionalComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(RegisterProfesionalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
