import { Component, Input, OnInit } from '@angular/core';
import {Block} from "../../models/block.interface";

@Component({
  selector: 'app-block-view',
  templateUrl: './block-view.component.html',
  styleUrls: ['./block-view.component.scss']
})
export class BlockViewComponent implements OnInit {

  @Input() public block: Block;

  constructor() { }

  ngOnInit(): void {
  }

}
