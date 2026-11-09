import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Comision } from '../models/Comision';

@Injectable({
  providedIn: 'root'
})
export class ComisionService {
  
  [x: string]: any;
  private urlEndPoint: string ='http://localhost:8080/sis/Comisiones'
  constructor(private http:HttpClient) { }

  getAllComisiones():Observable<Comision[]>{
    return this.http.get<any[]>(this.urlEndPoint);
  }
}
