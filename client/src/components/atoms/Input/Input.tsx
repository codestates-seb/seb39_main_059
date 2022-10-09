import React, { FC, forwardRef, RefObject } from 'react'
import { CSSProp } from 'styled-components'
import * as S from './Input.style'

export interface Props {
  /** type of input */
  type?: string
  /** name of input */
  inputName: string
  /** is invalid? */
  invalid?: boolean
  /** is disabled */
  disabled?: boolean
  /** defaultValue of input */
  defaultValue?: string
  /** placeholder content */
  placeholder?: string
  /** input value(state) */
  value?: string | number
  /** onChange handler(setState) */
  onChange?: (e: React.ChangeEvent<HTMLInputElement>) => void
  /** onFocusOut handler */
  onFocusOut?: (e: React.FocusEvent<HTMLInputElement>) => void
  ref?:
    | ((e: HTMLInputElement | null) => void)
    | RefObject<HTMLInputElement>
    | null
    | undefined
  cssProp?: CSSProp
}

const Input: FC<Props> = forwardRef(({ inputName, ...props }, ref) => {
  return <S.Input name={inputName} {...props} ref={ref} autoComplete="off" />
})

export default Input
