export interface Block {
  id?: number;
  hash: string;
  nonce: number;
  previousHash: any;
  timestamp: number;
}

export interface Transactions {
  id?: number;
  amount: number;
  fromAddress: string;
  timestamp: number;
  toAddress: string;
  blockId: number;
}
