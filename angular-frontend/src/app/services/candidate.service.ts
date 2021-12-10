import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {Candidate} from "../models/candidate.interface";

@Injectable({
  providedIn: 'root'
})
export class CandidateService {

  private readonly api: string;

  constructor(private http: HttpClient) {
    this.api = 'http://localhost:8080/candidates';
  }

  createNewCandidate(candidate: Candidate) {
    return this.http.post(this.api + '/create', candidate);
  }

  getAllCandidates(): Observable<Candidate[]> {
    return this.http.get<Candidate[]>(this.api);
  }

  updateCandidateById(id: any, name: string) {
    return this.http.put(this.api + '/' + id, name);
  }

  deleteCandidateById(id: number) {
    return this.http.delete(this.api + '/' + id);
  }

  getVotesForCandidate(id: number) {
    return this.http.get<number>(this.api +  '/votes/' + id);
  }

}
