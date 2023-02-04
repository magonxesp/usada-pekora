export class TriggerAudioFormData {

  constructor(
    public readonly id: string,
    public readonly file: File,
    public readonly triggerId: string,
    public readonly guildId: string,
  ) {
  }

  toFormData() {
    const formData = new FormData()

    formData.set('id', this.id)
    formData.set('file', this.file)
    formData.set('triggerId', this.triggerId)
    formData.set('guildId', this.guildId)

    return formData
  }

}
