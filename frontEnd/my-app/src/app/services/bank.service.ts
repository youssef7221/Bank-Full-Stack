import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BankService {

  baseUrl = 'http://localhost:8080/api/v1/banking';

  constructor(private http: HttpClient) { }

  login(data: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/account/login`,data);
  }
  register(data: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/account/register`, data);
  }
  logout() {
    localStorage.clear();
  }
    // Deposit transaction
  deposit(accountId: number, amount: number): Observable<any> {
  console.log('Making deposit request:', { accountId, amount });
    return this.http.post(`${this.baseUrl}/transaction/deposit/${accountId}?amount=${amount}`,{});
  }
// Withdraw transaction

withdraw(accountId: number, amount:number): Observable<any> {
  console.log('Making withdraw request:', { accountId, amount });
  return this.http.post(`${this.baseUrl}/transaction/withdraw/${accountId}?amount=${amount}`, {});
}

getBalance(accountId: number): Observable<any> {
  console.log('account id inside service function', accountId);
  return this.http.get(`${this.baseUrl}/account/balance/${accountId}`);
}

getTransactions(accountId: number) : Observable<any>{
    console.log('this the history of accound Id : ' + accountId);
    return this.http.get(`${this.baseUrl}/transaction/history/${accountId}`);
}
}
