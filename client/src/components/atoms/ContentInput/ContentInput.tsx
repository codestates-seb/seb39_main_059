import { FC, useCallback, useRef } from 'react'
import * as S from './ContentInput.style'

export interface Props {
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
  /** onFocusOut handler */
  onFocusOut?: (e: React.FocusEvent<HTMLInputElement>) => void
}

const ContentInput: FC<Props> = ({ inputName, ...props }) => {
  const textRef = useRef<HTMLTextAreaElement>(null)
  const handleResizeHeight = useCallback(() => {
    // eslint-disable-next-line @typescript-eslint/no-non-null-assertion
    textRef.current!.style.height = 'auto'
    // eslint-disable-next-line @typescript-eslint/no-non-null-assertion
    textRef.current!.style.height = `${textRef.current!.scrollHeight}px`
  }, [])
  return (
    <S.TextArea
      ref={textRef}
      name={inputName}
      {...props}
      onInput={handleResizeHeight}
      rows={10}
    />
  )
}
export default ContentInput
