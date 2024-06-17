import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HeaderComponent } from '../../header/header.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-after-register',
  standalone: true,
  imports: [HeaderComponent, CommonModule],
  templateUrl: './after-register.component.html',
  styleUrl: './after-register.component.css'
})
export class AfterRegisterComponent {
  username: string = "";
  userType: string | null = null;

  constructor(private route: ActivatedRoute) { }

  ngOnInit() {
    this.username = this.route.snapshot.params['username'];
    this.userType = this.route.snapshot.queryParams['userType'];

    console.log("userType: ", this.userType);
  }
}
