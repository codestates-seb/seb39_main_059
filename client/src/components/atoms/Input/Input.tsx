import { FC } from 'react'
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
}

const Input: FC<Props> = ({ inputName, ...props }) => {
  return <S.Input name={inputName} {...props} autoComplete="off" />
}

export default Input
