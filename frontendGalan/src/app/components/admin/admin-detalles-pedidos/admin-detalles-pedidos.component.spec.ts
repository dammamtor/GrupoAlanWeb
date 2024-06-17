import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminDetallesPedidosComponent } from './admin-detalles-pedidos.component';

describe('AdminDetallesPedidosComponent', () => {
  let component: AdminDetallesPedidosComponent;
  let fixture: ComponentFixture<AdminDetallesPedidosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminDetallesPedidosComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AdminDetallesPedidosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
