import { AppPage } from './app.po';

describe('workspace-project App', () => {
  let page: AppPage;

  beforeEach(() => {
    page = new AppPage();
  });

  it('Should display header text and file input on load', () => {
    page.navigateTo();
    expect(page.getHeaderText()).toEqual('Employee CSV File Viewer');
    expect(page.getInputToFileUpload().isPresent()).toEqual(true);
  });

  it('Should display file content when the file is selected', () => {
    page.navigateTo();
    page.uploadFile('issues.csv');
    expect(page.getLoadedIssuesTable()).toBeTruthy();
  });
});
