import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Periodo } from '../models/Periodo';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PeriodoService {
  [x: string]: any;
  private urlEndPoint: string ='http://localhost:8080/sis/Periodos'
  constructor(private http:HttpClient) { }

  getAllPeriodos():Observable<Periodo[]>{
    return this.http.get<any[]>(this.urlEndPoint);
  }
}
