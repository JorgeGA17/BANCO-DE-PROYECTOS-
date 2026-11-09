import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Presidente } from '../models/Presidente';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PresidenteService {
  [x: string]: any;
  private urlEndPoint: string ='http://localhost:8080/sis/Presidentes'
  constructor(private http:HttpClient) { }

  getAllPresidentes():Observable<Presidente[]>{
    return this.http.get<any[]>(this.urlEndPoint);
  }
}
