export interface FileAttributes {
  url: string
}

export interface File {
  id?: number
  attributes: FileAttributes
}

export interface FileData {
  data: File
}

export function fileUrlToFileData(url?: string): FileData | null {
  if (!url) {
    return null
  }

  return {
    data: {
      attributes: {
        url: url
      }
    }
  }
}
