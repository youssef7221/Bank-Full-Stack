import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { BankService } from '../../services/bank.service';
import { Router } from '@angular/router';
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-login-page',
  standalone: true,
  imports: [FormsModule, ReactiveFormsModule, NgIf],
  templateUrl: './login-page.component.html',
  styleUrl: './login-page.component.css'
})
export class LoginPageComponent implements OnInit {
  loginform!: FormGroup;
  showPassword: boolean = false;
  loginError: string | null = null;

  constructor(private bankService: BankService, private router: Router) { }

  ngOnInit(): void {
    this.loginform = new FormGroup({
      cardNumber: new FormControl('', [Validators.required]),
      password: new FormControl('', [Validators.required])
    });
  }

  togglePasswordVisibility(): void {
    this.showPassword = !this.showPassword;
  }

  Login(): void {
    this.loginError = null;  // Reset error message
    if (this.loginform.valid) {
      const data = {
        cardNumber: this.loginform.get('cardNumber')?.value,
        password: this.loginform.get('password')?.value,
      };
      this.bankService.login(data).subscribe({
        next: (response) => {
          localStorage.clear();
          localStorage.setItem('accountId', response.data.accountId);
          this.router.navigate(['/transactions']);
        },
        error: (error) => {
          console.log(error);
          this.loginError = 'Incorrect card number or password';
        }
      });
    }
  }
}
