import { FC, forwardRef, useCallback, useImperativeHandle, useRef } from 'react'
import * as S from './FormTextInput.style'

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
  /** rows number */
  rows?: number
  /** onFocusOut handler */
  onFocusOut?: (e: React.FocusEvent<HTMLInputElement>) => void
}

const ContentInput: FC<Props> = forwardRef(({ inputName, ...props }, ref) => {
  const textRef = useRef<HTMLTextAreaElement>(null)
  useImperativeHandle(ref, () => textRef.current)
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
      // rows={10}
    />
  )
})

export default ContentInput
