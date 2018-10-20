import {Component} from '@angular/core';
import {DataShareService} from '../search/data-share.service';
import {SearchResultModel} from './search-result.model';
import {SearchResultService} from "./search-result.service";

@Component({
  selector: 'app-employee-list',
  templateUrl: './search-result.component.html',
  styleUrls: ['./search-result.component.css'],
  providers: [SearchResultService]
})
export class SearchResultComponent {
  searchResult: SearchResultModel[];

  constructor(private dataShareService: DataShareService, private searchResultService: SearchResultService) {
      dataShareService.observableData.subscribe((searchText: string) => {
        if(searchText) {
          this.searchResultService.search(searchText).subscribe((searchResult: SearchResultModel[]) => {
            this.searchResult = searchResult;
          });
        }
      });
  }
}
