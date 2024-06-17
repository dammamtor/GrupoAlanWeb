import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserFavoritosComponent } from './user-favoritos.component';

describe('UserFavoritosComponent', () => {
  let component: UserFavoritosComponent;
  let fixture: ComponentFixture<UserFavoritosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UserFavoritosComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(UserFavoritosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
