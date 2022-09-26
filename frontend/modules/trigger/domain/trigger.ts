export enum TriggerCompare {
  CONTAINS = 'in',
  PATTERN = 'pattern',
}

export interface Trigger {
  uuid: string
  input: string
  compare: TriggerCompare
  outputText?: string
  outputAudio?: string
  discordServerId: string
}
