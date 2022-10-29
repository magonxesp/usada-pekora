export enum TriggerCompare {
  CONTAINS = 'in',
  PATTERN = 'pattern',
}

export enum TriggerFeature {
  TEXT_RESPONSE,
  AUDIO_RESPONSE,
}

export interface TriggerPrimitives {
  title: string,
  uuid: string,
  input: string,
  compare: string,
  outputText?: string,
  outputAudio?: string,
  discordServerId: string
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

  static fromPrimitives(values: TriggerPrimitives): Trigger {
    return new Trigger(
      values.title,
      values.uuid,
      values.input,
      values.compare as TriggerCompare,
      values.discordServerId,
      values.outputText,
      values.outputAudio
    )
  }

  static empty(): Trigger {
    return Trigger.fromPrimitives({
      title: "",
      uuid: "",
      input: "",
      compare: TriggerCompare.CONTAINS,
      outputText: "",
      outputAudio: "",
      discordServerId: ""
    })
  }

  toPrimitives(): TriggerPrimitives {
    return {
      title: this.title,
      uuid: this.uuid,
      input: this.input,
      compare: this.compare as string,
      outputText: this.outputText,
      outputAudio: this.outputAudio,
      discordServerId: this.discordServerId
    }
  }

  features(): Array<TriggerFeature> {
    return [
      this.outputText && TriggerFeature.TEXT_RESPONSE,
      this.outputAudio && TriggerFeature.AUDIO_RESPONSE,
    ].filter(feature => typeof feature !== 'undefined') as Array<TriggerFeature>
  }
}

export const triggerCompareOptions = () => Object.entries(TriggerCompare)
