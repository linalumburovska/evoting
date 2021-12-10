import { Component, OnInit } from '@angular/core';
import { BlockchainService } from 'src/app/services/blockchain.service';
// @ts-ignore
import { Transaction } from '../../../assets/blockchain.js';
import { ActivatedRoute, Router } from '@angular/router';
import {Block} from "../../models/block.interface";
import {UserService} from "../../services/user.service";

@Component({
  selector: 'app-create-transaction',
  templateUrl: './create-transaction.component.html',
  styleUrls: ['./create-transaction.component.scss']
})
export class CreateTransactionComponent implements OnInit {

  public nexTx: any;
  // of the user that wants to make a transaction (sign it with a private key)
  public walletKey: any;
  saveCandId: any;
  candidate: string;
  public pendingTransactions: any = [];
  nextIdBlock: number;
  previousHashBlock: string;
  privateKey: string;
  enableSignVote: boolean = false;

  constructor(private blockchainService: BlockchainService, private route: ActivatedRoute,private router: Router, private userService: UserService) {
    this.walletKey = blockchainService.walletKeys[0];
    this.blockchainService.getBlocks().subscribe((blocks) => {
      this.previousHashBlock = blocks[blocks.length-1].hash;
      this.nextIdBlock = blocks.length + 1;

    })
  }

  ngOnInit(): void {
    this.nexTx = new Transaction();
    this.nexTx.amount = 100;
    this.nexTx.toAddress = 'E-voting';
    this.route.queryParams
      .subscribe(params => {
        this.saveCandId = params.id;
        this.candidate = params.name;
      });
  }

  createTransaction() {
    // only send from your wallet
    this.nexTx.fromAddress = this.walletKey.publicKey;
    // this.nexTx.signTransaction(this.walletKey.keyObj);
    const block: Block = {
      hash: Math.random().toString(36).slice(2),
      nonce: Math.random() * 100,
      previousHash: this.previousHashBlock,
      timestamp: 157822800
    }

    this.blockchainService.createBlock(block).subscribe((id: number) => {
      const transaction: Transaction = {
        amount: 100,
        fromAddress: this.walletKey.publicKey,
        timestamp: block.timestamp,
        toAddress: 'E-voting',
        blockId: id
      }

      this.blockchainService.createTransaction(transaction).subscribe(() => {
        this.blockchainService.getTransactions(id).subscribe(transactions => {
          this.pendingTransactions = transactions;
          this.nexTx = new Transaction();
        })
      })
    })

  }

  minePendingTransactions() {
    this.blockchainService.signBlindSignature(this.saveCandId).subscribe((result) => {
      if(result) {
        this.userService.finishVoting().subscribe(() => {
          this.router.navigate(['/blockchain']);
        })
      }
    })
  }

  addPrivateKey() {
    this.blockchainService.addPrivateKey(this.privateKey).subscribe(() => {
      this.enableSignVote = true;
    })
  }

}
