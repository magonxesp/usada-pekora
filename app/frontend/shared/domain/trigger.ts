export enum TriggerCompare {
  CONTAINS = 'in',
  PATTERN = 'pattern',
}

export enum TriggerFeature {
  TEXT_RESPONSE,
  AUDIO_RESPONSE,
}

export class Trigger {

  public readonly id: string
  public readonly title: string
  public readonly input: string
  public readonly compare: TriggerCompare
  public readonly discordServerId: string
  public readonly outputText: string|null

  constructor(values: {
    id: string,
    title: string,
    input: string,
    compare: string,
    outputText: string|null,
    discordServerId: string
  }) {
    this.title = values.title
    this.id = values.id
    this.input = values.input
    this.compare = values.compare as TriggerCompare
    this.discordServerId = values.discordServerId
    this.outputText = values.outputText
  }

}

export const triggerCompareOptions = () => Object.entries(TriggerCompare)

