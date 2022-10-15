import { TriggerFeature } from './trigger-feature'

export enum TriggerCompare {
  CONTAINS = 'in',
  PATTERN = 'pattern',
}

export class Trigger {

  constructor(
    public title: string,
    public readonly uuid: string,
    public input: string,
    public compare: TriggerCompare,
    public discordServerId: string,
    public outputText?: string,
    public outputAudio?: string,
  ) {}

  static fromObject(object: {
    title: string,
    uuid: string,
    input: string,
    compare: TriggerCompare,
    outputText?: string,
    outputAudio?: string,
    discordServerId: string
  }) {
    return new Trigger(
      object.title,
      object.uuid,
      object.input,
      object.compare,
      object.discordServerId,
      object.discordServerId,
      object.outputAudio
    )
  }

  features() {
    return [
      this.outputText && TriggerFeature.TEXT_RESPONSE,
      this.outputAudio && TriggerFeature.AUDIO_RESPONSE,
    ].filter(feature => typeof feature !== 'undefined')
  }
}
