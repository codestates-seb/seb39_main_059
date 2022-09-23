import { FC, TextareaHTMLAttributes } from 'react'
import styled from 'styled-components'

export type Props = TextareaHTMLAttributes<HTMLTextAreaElement>

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

const Textarea: FC<Props> = props => {
  return <TextareaLayout {...props} />
}

export default Textarea
