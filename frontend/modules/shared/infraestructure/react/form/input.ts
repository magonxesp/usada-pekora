import { ChangeEvent } from 'react'

export type InputChangeEventHandler = (event: ChangeEvent<HTMLInputElement|HTMLSelectElement>) => void

/**
 * Input onChange event handler
 *
 * @param {string} name
 * @param {any} value
 */
export type InputWrapperOnChangeEventHandler = (name: string, value: any) => void

/**
 * Validate the input value when onChange or onSubmit event is fired
 *
 * @param {any} value
 *    The input value
 *
 * @throws {string}
 *    Throws string with the error message in case the value is not valid
 */
export type InputWrapperValueValidator = (value: any) => void

export type InputWrapperOnValidateEventHandler = (name: string, error: string) => void
