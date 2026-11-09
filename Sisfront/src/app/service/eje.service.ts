import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Eje } from '../models/Eje';

@Injectable({
  providedIn: 'root'
})
export class EjeService {

  [x: string]: any;
  private urlEndPoint: string ='http://localhost:8080/sis/Ejes'
  constructor(private http:HttpClient) { }

  getAllEjes():Observable<Eje[]>{
    return this.http.get<any[]>(this.urlEndPoint)
  }
 
}
