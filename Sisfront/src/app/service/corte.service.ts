import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Corte } from '../models/corte';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CorteService {
  [x: string]: any;
  private urlEndPoint: string ='http://localhost:8080/sis/Cortes'
  constructor(private http:HttpClient) { }

  getAllCortes():Observable<Corte[]>{
    return this.http.get<any[]>(this.urlEndPoint);
  }

}
