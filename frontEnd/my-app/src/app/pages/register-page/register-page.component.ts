import { Component, OnInit } from '@angular/core';
import { BankService } from '../../services/bank.service';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-register-page',
  standalone: true,
  imports: [FormsModule, ReactiveFormsModule, NgIf],
  templateUrl: './register-page.component.html',
  styleUrl: './register-page.component.css'
})
export class RegisterPageComponent implements OnInit {

  form!: FormGroup;
  successMessage: string = '';
  constructor(private bankService: BankService, private router: Router) {}

  ngOnInit(): void {
    this.form = new FormGroup({
      userName: new FormControl('', [Validators.required]),
      cardNumber: new FormControl('', [Validators.required, Validators.minLength(16), Validators.maxLength(16)]),
      cvv: new FormControl('', [Validators.required, Validators.minLength(3), Validators.maxLength(3)]),
      password: new FormControl('', [Validators.required, Validators.minLength(8)]),
      confirmPassword: new FormControl('', [Validators.required])
    });
  }

  register(): void {
    if (this.form.valid) {
      let data = {
        userName: this.form.get('userName')?.value,
        cardNumber: this.form.get('cardNumber')?.value,
        cvv: this.form.get('cvv')?.value,
        password: this.form.get('password')?.value
      }
      this.bankService.register(data).subscribe({
        next: (response) => {
          console.log(response);
          this.successMessage = 'Account created successfully!';
          setTimeout(() => {
            this.successMessage = '';
            this.router.navigate(['/auth/login']);
          }, 2000); // 2 seconds delay
        },
        error: (error) => {
          console.log(error);
          console.log(data);
        }
      });
    }
  }
}
