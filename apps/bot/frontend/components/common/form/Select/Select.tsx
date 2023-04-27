import { useEffect, useState } from 'react'
import { SelectOption } from '../../../../shared/helpers/form/select-option'
import { CheckIcon, ChevronUpDownIcon } from '@heroicons/react/20/solid'
import Image from 'next/image'
import Picture from '../../image/Picture/Picture'

interface ComponentProps {
  options: SelectOption[]
  onChange?: (selected: SelectOption) => void,
  selected?: any
}

export default function Select(props: ComponentProps) {
  const { options, onChange, selected } = props
  const [selectedOption, setSelectedOption] = useState(options[0])

  useEffect(() => {
    const option = options.filter(option => option.value == selected).shift()

    if (option) {
      setSelectedOption(option)
    }
  }, [selected])

  const onChangeSelectedOption = (option: SelectOption) => {
    setSelectedOption(option)

    if (onChange) {
      onChange(option)
    }
  }

  return (
    <p>Hola select</p>
  )
}

