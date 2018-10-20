import {Component} from '@angular/core';
import {DataShareService} from './data-share.service';

@Component({
  selector: 'search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent {

  constructor(private dataShareService: DataShareService) { }

  search(searchText: string) {
    this.dataShareService.publishData(searchText);
  }
}
