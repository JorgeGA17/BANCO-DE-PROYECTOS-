import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Miembro } from '../models/Miembro';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MiembroService {

  [x: string]: any;
  private urlEndPoint: string ='http://localhost:8080/sis/Miembros'
  constructor(private http:HttpClient) { }

  getAllMiembros():Observable<Miembro[]>{
    return this.http.get<any[]>(this.urlEndPoint);
  }

  
}
