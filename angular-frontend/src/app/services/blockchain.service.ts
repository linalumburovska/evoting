import { Injectable } from '@angular/core';
// @ts-ignore
import { Blockchain } from '../../assets/blockchain.js';
// @ts-ignore
import EC from 'elliptic';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Block, Transactions} from "../models/block.interface";

@Injectable({
  providedIn: 'root'
})
export class BlockchainService {

  public blockChainInstance = new Blockchain();
  public walletKeys: any = [];

  private readonly apiBlocks: string;
  private readonly apiTransactions: string;

  constructor(private http: HttpClient) {
    this.apiBlocks = 'http://localhost:8080/blocks';
    this.apiTransactions = 'http://localhost:8080/transactions';
    this.generateWalletKeys();
  }

  // constructor() {
  //   this.blockChainInstance.difficulty = 1;
  //   this.blockChainInstance.minePendingTransactions('my-wallet-address');
  //
  //
  // }

  // getBlocks() {
  //   return this.blockChainInstance.chain;
  // }

  getBlocks() : Observable<Block[]> {
    return this.http.get<Block[]>(this.apiBlocks);
  }

  createBlock(block: Block): Observable<number> {
    return this.http.post<number>(this.apiBlocks + '/create', block)
  }

  createTransaction(tx: Transactions) {
    return this.http.post(this.apiTransactions + '/create', tx)
  }

  // createTransaction(tx: any) {
  //   this.blockChainInstance.addTransaction(tx);
  // }

  // getPendingTransactions() {
  //   return this.blockChainInstance.pendingTransactions;
  // }
  getTransactions(id: number | undefined): Observable<Transactions[]> {
    return this.http.get<Transactions[]>(this.apiTransactions + '/' + id);
  }

  minePendingTransactions() {
    this.blockChainInstance.minePendingTransactions(
      this.walletKeys[0].publicKey
    )
  }

  signBlindSignature(id: number) {
    return this.http.post(this.apiBlocks + '/blind-signature/' + id, {})
  }

  addPrivateKey(privateKey: string) {
    return this.http.post(this.apiBlocks + '/add-private-key', privateKey);
  }

  private generateWalletKeys() {
    const ec = new EC.ec('secp256k1');
    const key = ec.genKeyPair();

    this.walletKeys.push({
      keyObj: key,
      publicKey: key.getPublic('hex'),
      privateKey: key.getPrivate('hex')
    })
  }

}
