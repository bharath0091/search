export class SearchResultModel {
    constructor(public readonly title: string,
                public readonly type: string,
                public readonly authors: string[],
                public readonly artists: string[]) {}
}
