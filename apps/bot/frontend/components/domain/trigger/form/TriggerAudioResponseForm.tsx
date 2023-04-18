import InputWrapper from '../../../shared/form/input-wrapper/InputWrapper'
import { useIntl } from 'react-intl'

export default function TriggerAudioResponseForm() {
  const intl = useIntl()

  return (
    <InputWrapper
      label={intl.$t({ id: 'trigger.form.output_audio.label' })}
      help={intl.$t({ id: 'trigger.form.output_audio.description' })}
    >
      <>
        <InputWrapper.Input>
          <input
            type="file"
            accept="audio/mpeg"
            onChange={undefined}
            name="responseAudio"
          />
        </InputWrapper.Input>
        {([]).map((error, index) => (
          <InputWrapper.Error key={index}>{error}</InputWrapper.Error>
        ))}
      </>
    </InputWrapper>
  )
}
