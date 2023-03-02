import { v4 as uuidv4 } from 'uuid'
import { TriggerCompare } from '../trigger'
import { FormDataValues } from '../../helpers/form/form-data-values'

type TriggerFormDataValues = {
  title: string,
  uuid: string,
  input: string,
  compare: string,
  outputText: string|null,
  outputAudio: File|Buffer|null,
  discordServerId: string
}

export class TriggerFormData extends FormDataValues {

  public readonly title: string
  public readonly uuid: string
  public readonly input: string
  public readonly compare: string
  public readonly outputText: string|null
  public readonly outputAudio: File|Buffer|null
  public readonly discordServerId: string

  constructor(values: TriggerFormDataValues) {
    super()

    this.title = values.title
    this.uuid = values.uuid
    this.input = values.input
    this.compare = values.compare
    this.outputText = values.outputText
    this.outputAudio = values.outputAudio
    this.discordServerId = values.discordServerId
  }

  toPlainObject(): TriggerFormDataValues {
    return Object(this)
  }

  static new(): TriggerFormData {
    return new TriggerFormData({
      title: "",
      uuid: uuidv4(),
      input: "",
      compare: TriggerCompare.CONTAINS,
      outputText: null,
      outputAudio: null,
      discordServerId: ""
    })
  }
}
