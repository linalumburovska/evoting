import { Component, OnInit } from '@angular/core';
import {UserService} from "../../services/user.service";
import {User} from "../../models/user.interface";
import {Observable} from "rxjs";
import {BlockchainService} from "../../services/blockchain.service";
import {Block, Transactions} from "../../models/block.interface";
import {Candidate} from "../../models/candidate.interface";
import {CandidateService} from "../../services/candidate.service";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {Router} from "@angular/router";

@Component({
  selector: 'app-blockchain-viewer',
  templateUrl: './blockchain-viewer.component.html',
  styleUrls: ['./blockchain-viewer.component.scss']
})
export class BlockchainViewerComponent implements OnInit {

  user$: Observable<User>;
  candidates: Candidate[] = [];
  candidateName: string;
  blocks: Block[];
  selectedBlock: Block;
  transactionsForBlock: Transactions[] = [];
  saveCandId: number | undefined;

  constructor(private userService: UserService, private blockchainService: BlockchainService,
              private candidateService: CandidateService, private modalService: NgbModal,
              private router: Router
  ) {
    this.user$ = this.userService.findLoggedInUser();
    this.blockchainService.getBlocks().subscribe(blocks => {
      this.blocks = blocks;
      this.selectedBlock = blocks[0];
    })
    this.candidateService.getAllCandidates().subscribe(candidates => {
      this.candidates = candidates;
    });
  }

  ngOnInit(): void {

  }


  showTransactions(block: Block) {
    this.selectedBlock = block;
    this.blockchainService.getTransactions(block.id).subscribe(transactions => {
      this.transactionsForBlock = transactions;
    })
  }

  open(content: any, name: any, id: number | undefined) {
    this.candidateName = name;
    this.saveCandId = id;
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'}).result.then((result) => {
    });
  }

  saveVote() {
    this.router.navigate(['/new/transaction'], { queryParams: { id: this.saveCandId, name: this.candidateName }});
    this.close();
  }

  close() {
    this.modalService.dismissAll();
  }
}
