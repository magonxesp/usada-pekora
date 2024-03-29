import { useRef, useState } from 'react'
import { useIntl } from 'react-intl'
import { alert } from '../../../../helpers/alert'
import { useSelectedGuild } from '../../../../hooks/guild'
import { TriggerEntityFormGroup } from '../TriggerEntityFormGroup/TriggerEntityFormGroup'
import { TriggerTextResponseFormGroup } from '../TriggerTextResponseFormGroup/TriggerTextResponseFormGroup'
import { Button, CollapsibleFormGroup, Form } from '@usada-pekora/ui/components'
import { TriggerAudioResponseFormGroup } from '../TriggerAudioResponseFormGroup/TriggerAudioResponseFormGroup'
import styles from './TriggerForm.module.css'
import { emptyTriggerFormData } from '../../../../helpers/trigger-form'

export default function TriggerForm({ triggerFormData, onSubmit, disableSubmit }) {
  // form group refs
  const entityFormRef = useRef(null)
  const responseTextFormRef = useRef(null)
  const responseAudioFormRef = useRef(null)

  // form specific states
  const [formData, setFormData] = useState(triggerFormData ?? emptyTriggerFormData())
  const { selected: selectedGuild } = useSelectedGuild()
  const intl = useIntl()

  const formGroupRefs = [
    entityFormRef,
    responseTextFormRef,
    responseAudioFormRef
  ]

  const validate = () => {
    formGroupRefs.forEach(ref => {
      ref.current?.cleanErrors()
      ref.current?.validate()
    })

    return formGroupRefs.map(ref => ({ ...ref.current?.errors }))
      .filter(errors => Object.keys(errors).length > 0)
  }

  const submitForm = () => {
    if (typeof onSubmit !== 'undefined') {
      if (!selectedGuild) {
        alert(intl.$t({ id: 'trigger.form.error.missing_discord_server_id' }), 'error')
        return
      }

      const errors = validate()

      if (errors.length === 0) {
        onSubmit({ ...formData, guildId: selectedGuild })
      }
    }
  }

  return (
    <Form onSubmit={submitForm}>
      <>
        <TriggerEntityFormGroup
          data={formData}
          ref={entityFormRef}
          onChange={(data) => setFormData({...formData, ...data})}
        />

        <CollapsibleFormGroup
          addTitle={intl.$t({ id: 'trigger.form.response_text.add' })}
          removeTitle={intl.$t({ id: 'trigger.form.response_text.remove' })}
          onRemove={() => setFormData({...formData, responseText: undefined})}
          open={typeof formData.responseText !== 'undefined'}
        >
          <TriggerTextResponseFormGroup
            data={formData.responseText}
            ref={responseTextFormRef}
            onChange={(data) => setFormData({...formData, responseText: data})}
          />
        </CollapsibleFormGroup>

        <CollapsibleFormGroup
          addTitle={intl.$t({ id: 'trigger.form.response_audio.add' })}
          removeTitle={intl.$t({ id: 'trigger.form.response_audio.remove' })}
          onRemove={() => setFormData({...formData, responseAudio: undefined})}
          open={typeof formData.responseAudio !== 'undefined'}
        >
          <TriggerAudioResponseFormGroup
            data={formData.responseAudio}
            ref={responseAudioFormRef}
            onChange={(data) => setFormData({...formData, responseAudio: data})}
          />
        </CollapsibleFormGroup>

        <div className={styles.triggerFormSubmit}>
          <Button type="submit" disabled={disableSubmit ?? false}>{intl.$t({ id: 'trigger.form.submit' })}</Button>
        </div>
      </>
    </Form>
  )
}
