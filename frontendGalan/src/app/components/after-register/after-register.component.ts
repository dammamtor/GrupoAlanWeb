import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HeaderComponent } from '../header/header.component';

@Component({
  selector: 'app-after-register',
  standalone: true,
  imports: [HeaderComponent],
  templateUrl: './after-register.component.html',
  styleUrl: './after-register.component.css'
})
export class AfterRegisterComponent {
  username: string = "";

  constructor(private route: ActivatedRoute) { }

  ngOnInit() {
    this.username = this.route.snapshot.params['username'];
  }
}
