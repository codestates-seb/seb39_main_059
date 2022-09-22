import { TextareaHTMLAttributes } from 'react'
import styled from 'styled-components'

export type TextareaProps = TextareaHTMLAttributes<HTMLTextAreaElement>

const TextareaLayout = styled.textarea`
  width: 100%;
  height: 335px;
  border-radius: 0.5rem;
  background-color: ${({ theme }) => theme.color.softGray};
  border: 1px solid ${({ theme }) => theme.color.softGray};
  &:focus {
    outline: none;
  }
`

const Textarea = (props: TextareaProps) => {
  return <TextareaLayout {...props} />
}

export default Textarea
