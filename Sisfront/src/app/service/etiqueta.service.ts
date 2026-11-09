import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Etiqueta } from '../models/Etiqueta';

@Injectable({
  providedIn: 'root'
})
export class EtiquetaService {

  [x: string]: any;
  private urlEndPoint: string ='http://localhost:8080/sis/Etiquetas'
  constructor(private http:HttpClient) { }

  getAllEtiquetas():Observable<Etiqueta[]>{
    return this.http.get<any[]>(this.urlEndPoint)
  }

  
}
