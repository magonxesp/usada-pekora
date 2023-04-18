export enum TriggerCompare {
  CONTAINS = 'in',
  PATTERN = 'pattern',
}

export enum TriggerFeature {
  TEXT_RESPONSE,
  AUDIO_RESPONSE,
}

export interface TriggerObject {
  id: string
  title: string
  input: string
  compare: string
  responseTextId: string|null
  responseAudioId: string|null
  discordGuildId: string
}

export class Trigger {

  public readonly id: string
  public readonly title: string
  public readonly input: string
  public readonly compare: TriggerCompare
  public readonly responseTextId: string|null
  public readonly responseAudioId: string|null
  public readonly discordGuildId: string

  constructor(values: TriggerObject) {
    this.title = values.title
    this.id = values.id
    this.input = values.input
    this.compare = values.compare as TriggerCompare
    this.responseTextId = values.responseTextId
    this.responseAudioId = values.responseAudioId
    this.discordGuildId = values.discordGuildId
  }

}

export const triggerCompareOptions = () => Object.entries(TriggerCompare)

