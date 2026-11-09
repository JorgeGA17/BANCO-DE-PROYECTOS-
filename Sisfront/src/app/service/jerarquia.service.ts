import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Jerarquia } from '../models/Jerarquia';

@Injectable({
  providedIn: 'root'
})
export class JerarquiaService {

  [x: string]: any;
  private urlEndPoint: string ='http://localhost:8080/sis/Jerarquias'
  constructor(private http:HttpClient) { }

  getAllJerarquias():Observable<Jerarquia[]>{
    return this.http.get<any[]>(this.urlEndPoint)
  }

}
