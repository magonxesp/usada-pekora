import { emptyTriggerFormData, TriggerFormData } from '../../../../shared/helpers/form/trigger/form-data'
import Button from '../../../shared/form/Button'
import Form from '../../../shared/form/Form'
import { useRef, useState } from 'react'
import { useIntl } from 'react-intl'
import { alert } from '../../../../shared/helpers/alert'
import { useSelectedGuild } from '../../../../shared/hooks/guilds'
import { TriggerEntityFormGroup } from './TriggerEntityFormGroup'
import { TriggerTextResponseFormGroup } from './TriggerTextResponseFormGroup'
import { TriggerFormGroupRef } from '../../../../shared/helpers/form/trigger/handle'

interface TriggerFormProps {
  triggerFormData?: TriggerFormData,
  onSubmit?: (data: TriggerFormData) => void
  disableSubmit?: boolean
}

export default function TriggerForm({ triggerFormData, onSubmit, disableSubmit }: TriggerFormProps) {
  // form group refs
  const entityFormRef = useRef<TriggerFormGroupRef>(null)
  const responseTextFormRef = useRef<TriggerFormGroupRef>(null)

  // form specific states
  const [formData, setFormData] = useState(triggerFormData ?? emptyTriggerFormData())
  const selectedGuild = useSelectedGuild()
  const intl = useIntl()

  const formGroupRefs = [
    entityFormRef,
    responseTextFormRef
  ]

  const validate = () => {
    formGroupRefs.forEach(ref => {
      ref.current?.cleanErrors()
      ref.current?.validate()
    })
  }

  const submitForm = () => {
    if (typeof onSubmit !== 'undefined') {
      if (!selectedGuild) {
        alert(intl.$t({ id: 'trigger.form.error.missing_discord_server_id' }), 'error')
        return
      }

      validate()
      //onSubmit({ id: id, discordServerId: selectedGuild})
    }
  }

  return (
    <Form onSubmit={submitForm}>
      <>
        <TriggerEntityFormGroup data={formData} ref={entityFormRef} />
        <TriggerTextResponseFormGroup data={formData.responseText} ref={responseTextFormRef} />
        <Button className="w-full" type="submit" disabled={disableSubmit ?? false}>{intl.$t({ id: 'trigger.form.submit' })}</Button>
      </>
    </Form>
  )
}
