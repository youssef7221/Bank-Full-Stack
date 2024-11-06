import { NgIf } from '@angular/common';
import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterLink, NgIf],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent {


  constructor(private router: Router) {}

  isLoggedIn(): boolean {
    return localStorage.getItem('accountId') !== null;
  }

  logout(): void {
    localStorage.clear();
    this.router.navigate(['']);
  }

}
