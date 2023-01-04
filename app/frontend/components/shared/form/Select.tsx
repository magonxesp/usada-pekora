import { Fragment, useEffect, useState } from 'react'
import { Listbox, Transition } from '@headlessui/react'
import { SelectOption } from '../../../shared/infraestructure/form/select-option'
import { CheckIcon, ChevronUpDownIcon } from '@heroicons/react/20/solid'
import Image from 'next/future/image'
import Picture from '../image/Picture'

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
    <div className="w-72">
      <Listbox value={selectedOption} onChange={onChangeSelectedOption}>
        <div className="relative mt-1">
          <Listbox.Button className="relative w-full cursor-default rounded-lg bg-white py-2 pl-10 pr-10 text-left shadow-md focus:outline-none focus-visible:border-indigo-500 focus-visible:ring-2 focus-visible:ring-white focus-visible:ring-opacity-75 focus-visible:ring-offset-2 focus-visible:ring-offset-orange-300 sm:text-sm">
            {selectedOption.labelIcon ? (
              <span className="absolute inset-y-0 left-0 flex items-center pl-3">
                <Picture
                  src={selectedOption.labelIcon}
                  className="h-5 w-5 rounded-full"
                  alt={`Icon of ${selectedOption.label} option`}
                />
              </span>
            ) : null}
            <span className="block truncate">{selectedOption.label}</span>
            <span className="pointer-events-none absolute inset-y-0 right-0 flex items-center pr-2">
              <ChevronUpDownIcon
                className="h-5 w-5 text-gray-400"
                aria-hidden="true"
              />
            </span>
          </Listbox.Button>
          <Transition
            as={Fragment}
            leave="transition ease-in duration-100"
            leaveFrom="opacity-100"
            leaveTo="opacity-0"
          >
            <Listbox.Options className="z-10 absolute mt-1 max-h-60 w-full overflow-auto rounded-md bg-white py-1 text-base shadow-lg ring-1 ring-black ring-opacity-5 focus:outline-none sm:text-sm">
              {options.map((option, index) => (
                <Listbox.Option
                  key={index}
                  value={option}
                  disabled={option.disabled}
                  className={({ active }) =>
                    `relative cursor-default select-none py-2  pr-10
                    ${ option.labelIcon ? 'pl-10' : 'pl-4' }
                    ${ active ? 'bg-primary' : 'text-gray-900'}`
                  }
                >
                  {({ selected }) => (
                    <>
                      {option.labelIcon ? (
                        <span className="absolute inset-y-0 left-0 flex items-center pl-3">
                          <Picture
                            src={option.labelIcon}
                            className="h-5 w-5 rounded-full"
                            alt={`Icon of ${option.label} option`}
                          />
                        </span>
                      ) : null}
                      <span
                        className={`block truncate ${
                          selected ? 'font-medium' : 'font-normal'
                        }`}
                      >
                        {option.label}
                      </span>
                      {selected ? (
                        <span className="absolute inset-y-0 right-0 flex items-center pr-3 text-gray-900">
                          <CheckIcon className="h-5 w-5" aria-hidden="true" />
                        </span>
                      ) : null}
                    </>
                  )}
                </Listbox.Option>
              ))}
            </Listbox.Options>
          </Transition>
        </div>
      </Listbox>
    </div>
  )
}

