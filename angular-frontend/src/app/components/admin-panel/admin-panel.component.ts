import { Component, OnInit } from '@angular/core';
import {CandidateService} from "../../services/candidate.service";
import {Candidate} from "../../models/candidate.interface";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {find} from "rxjs/operators";

@Component({
  selector: 'app-admin-panel',
  templateUrl: './admin-panel.component.html',
  styleUrls: ['./admin-panel.component.scss']
})
export class AdminPanelComponent implements OnInit {

  candidates: Candidate[];
  name: string;
  // true add, false edit
  flag: boolean = true;
  saveId: number | undefined;
  candFrequency: any = '';
  showNumberOfVotes: boolean = false;
  numberOfVotes: number;

  constructor(private candidateService: CandidateService, private modalService: NgbModal) { }

  ngOnInit(): void {
    this.candidateService.getAllCandidates().subscribe(candidates => {this.candidates = candidates})
  }

  open(content: any, id: any) {
    if(id != null) {
      const findCandidate = this.candidates.find(el => el.id == id);
      if(findCandidate) {
        this.name = findCandidate.name;
        this.saveId = findCandidate.id;
      }
      this.flag = false;
    } else {
      this.flag = true;
      this.name = '';
      this.saveId = undefined;
    }
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'}).result.then((result) => {
    });
  }

  saveCandidate() {
    if(this.flag) {
      const newCandidate: Candidate = {
        name: this.name
      }
      this.candidateService.createNewCandidate(newCandidate).subscribe(() => {
        this.candidateService.getAllCandidates().subscribe(candidates => {this.candidates = candidates})
      })

    } else {
      this.candidateService.updateCandidateById(this.saveId, this.name).subscribe(() => {
        this.candidateService.getAllCandidates().subscribe(candidates => {this.candidates = candidates})
      })
    }
    this.close();
  }

  close() {
    this.modalService.dismissAll();
  }

  delete(id: any) {
    this.candidateService.deleteCandidateById(id).subscribe(() => {
      this.candidateService.getAllCandidates().subscribe(candidates => {this.candidates = candidates})
    })
  }

  showCounter(event: any) {
    this.candidateService.getVotesForCandidate(event.target.value).subscribe(val => {
      this.showNumberOfVotes = true;
      this.numberOfVotes = val;
    })
  }

}
