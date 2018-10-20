import { browser, by, element } from 'protractor';
import * as path from 'path';

export class AppPage {
  navigateTo() {
    return browser.get('/');
  }

  getHeaderText() {
    return element(by.tagName('h3')).getText();
  }

  getInputToFileUpload() {
    return element(by.tagName('input'));
  }

  uploadFile(filePath: string) {
    const absolutePath = path.resolve(__dirname, filePath);
    element(by.id('fileUpload')).sendKeys(absolutePath);
  }

  getLoadedIssuesTable() {
    return element(by.tagName('table'));
  }

}
