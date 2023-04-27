import { emptyTriggerFormData, TriggerFormData } from '../../../../shared/helpers/form/trigger/form-data'
import Button from '../../../common/form/Button/Button'
import Form from '../../../common/form/Form/Form'
import { useRef, useState } from 'react'
import { useIntl } from 'react-intl'
import { alert } from '../../../../shared/helpers/alert'
import { useSelectedGuild } from '../../../../shared/hooks/guilds'
import { TriggerEntityFormGroup } from '../TriggerEntityFormGroup/TriggerEntityFormGroup'
import { TriggerTextResponseFormGroup } from '../TriggerTextResponseFormGroup/TriggerTextResponseFormGroup'
import { TriggerFormGroupRef } from '../../../../shared/helpers/form/trigger/handle'
import TriggerFormAddResponse from '../TriggerFormAddResponse/TriggerFormAddResponse'

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
      console.log(ref)
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

        <TriggerFormAddResponse
          addTitle={intl.$t({ id: 'trigger.form.response_text.add' })}
          removeTitle={intl.$t({ id: 'trigger.form.response_text.remove' })}
          onRemove={() => setFormData({...formData, responseText: undefined})}
        >
          <TriggerTextResponseFormGroup
            data={formData.responseText}
            ref={responseTextFormRef}
            onChange={(data) => setFormData({...formData, responseText: data})}
          />
        </TriggerFormAddResponse>

        <Button className="w-full" type="submit" disabled={disableSubmit ?? false}>{intl.$t({ id: 'trigger.form.submit' })}</Button>
      </>
    </Form>
  )
}
