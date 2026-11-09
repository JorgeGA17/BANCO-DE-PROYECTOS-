import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Cargo } from '../models/Cargo';


@Injectable({
  providedIn: 'root'
})
export class CargoService {
  [x: string]: any;
  private urlEndPoint: string ='http://localhost:8080/sis/Cargos'
  
  constructor(private http:HttpClient) { }

  getAllCargos():Observable<Cargo[]>{
    return this.http.get<any[]>(this.urlEndPoint);
  }
}
