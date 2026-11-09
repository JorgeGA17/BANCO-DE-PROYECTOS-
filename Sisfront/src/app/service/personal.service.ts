import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Personal } from '../models/Personal';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PersonalService {

  [x: string]: any;
  private urlEndPoint: string ='http://localhost:8080/sis/Personas'
  constructor(private http:HttpClient) { }

  getAllPersonas():Observable<Personal[]>{
    return this.http.get<any[]>(this.urlEndPoint);
  }


  

}
