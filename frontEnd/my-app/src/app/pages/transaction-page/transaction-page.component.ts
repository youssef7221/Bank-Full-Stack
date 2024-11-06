import { Component } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BankService } from '../../services/bank.service';
import {CurrencyPipe, DatePipe, NgForOf, NgIf} from '@angular/common';
import {MessageModule} from "primeng/message";
import {CardModule} from "primeng/card";
import {ButtonDirective} from "primeng/button";

@Component({
  selector: 'app-transaction-page',
  standalone: true,
  imports: [FormsModule, ReactiveFormsModule, CurrencyPipe, NgIf, DatePipe, NgForOf, MessageModule, CardModule, ButtonDirective],
  templateUrl: './transaction-page.component.html',
  styleUrl: './transaction-page.component.css'
})
export class TransactionPageComponent {
  transactionform!: FormGroup;

  currentBalance: number = 0;
  currentDeposit: number = 0;
  currentWithdraw: number = 0;
  errorMessage: string = '';
  transactions: any[] = [];
  showAll: boolean = false;
  maxVisibleTransactions: number = 10;
  constructor(private fb: FormBuilder, private bankService: BankService) {}
  ngOnInit(): void {
    this.transactionform = this.fb.group({
      depositAmount: [''],
      withdrawAmount: ['']
    });
    this.getBalance();
    this.loadTransactionHistory();
  }

  onDeposit(): void {
    const depositAmount = parseFloat(this.transactionform.get('depositAmount')?.value);
    if (!isNaN(depositAmount) && depositAmount > 0) {
      this.bankService.deposit(this.getAccountId(),depositAmount).subscribe(response => {
        this.currentDeposit += depositAmount;
        this.currentBalance += response.data.amount;
        this.transactionform.get('depositAmount')?.reset();
        this.loadTransactionHistory();
      });
    }
  }
  onWithdraw(): void {
    const withdrawAmount = parseFloat(this.transactionform.get('withdrawAmount')?.value);
    this.bankService.withdraw(this.getAccountId(), withdrawAmount).subscribe({
      next: (response) => {
        this.currentWithdraw += withdrawAmount;
        this.currentBalance -= response.data.amount;
        this.transactionform.get('withdrawAmount')?.reset();
        this.loadTransactionHistory();
      },
      error: (error) => {
        this.errorMessage = error.error.message;
        alert(this.errorMessage);
      }
    });
  }
  getBalance(): void {
    this.bankService.getBalance(this.getAccountId()).subscribe(response => {
      this.currentBalance = response.data;
    });
  }

  getAccountId(): number {
    console.log("This is The account Id"+ Number(localStorage.getItem('accountId')));
    return Number(localStorage.getItem('accountId'));
  }
  limitedTransactions() {
    return this.showAll ? this.transactions : this.transactions.slice(0, this.maxVisibleTransactions);
  }
  // Toggle the showAll flag
  toggleShowAll() {
    this.showAll = !this.showAll;
  }
  loadTransactionHistory(): void{
    this.bankService.getTransactions(this.getAccountId()).subscribe({
      next: (response) => {
        this.transactions = response; // Store the returned transaction data
        console.log('Transaction history:', this.transactions);
      },
      error: (error) => {
        console.error('Error fetching transaction history:', error);
      }
    });
  }



}
