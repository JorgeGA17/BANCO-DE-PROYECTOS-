import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Estado } from '../models/estado';

@Injectable({
  providedIn: 'root'
})
export class EstadoService {
  [x: string]: any;
  private urlEndPoint: string ='http://localhost:8080/sis/Estados'
  constructor(private http:HttpClient) { }

  getAllEstados():Observable<Estado[]>{
    return this.http.get<any[]>(this.urlEndPoint)
  }

}

