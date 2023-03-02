import { FormDataValues } from '../../helpers/form/form-data-values'

type TriggerAudioFormDataValues = {
  id: string,
  file: File|Buffer,
  triggerId: string,
  guildId: string,
}

export class TriggerAudioFormData extends FormDataValues {

  public readonly id: string
  public readonly file: File|Buffer
  public readonly triggerId: string
  public readonly guildId: string

  constructor(values: TriggerAudioFormDataValues) {
    super()

    this.id = values.id
    this.file = values.file
    this.triggerId = values.triggerId
    this.guildId = values.guildId
  }

  toPlainObject(): TriggerAudioFormDataValues {
    return Object(this)
  }

}
