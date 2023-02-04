export enum TriggerCompare {
  CONTAINS = 'in',
  PATTERN = 'pattern',
}

export enum TriggerFeature {
  TEXT_RESPONSE,
  AUDIO_RESPONSE,
}

export class Trigger {

  public title: string
  public readonly uuid: string
  public input: string
  public compare: TriggerCompare
  public discordServerId: string
  public outputText: string|null

  constructor(values: {
    title: string,
    uuid: string,
    input: string,
    compare: string,
    outputText: string|null,
    discordServerId: string
  }) {
    this.title = values.title
    this.uuid = values.uuid
    this.input = values.input
    this.compare = values.compare as TriggerCompare
    this.discordServerId = values.discordServerId
    this.outputText = values.outputText
  }

  toPlainObject(): object {
    return Object(this)
  }
}

export const triggerCompareOptions = () => Object.entries(TriggerCompare)

