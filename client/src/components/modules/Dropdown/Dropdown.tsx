import { FC } from 'react'
import * as S from './Dropdown.styles'

interface Props {
  handleDeleteFeed: () => void
}

const Dropdown: FC<Props> = ({ handleDeleteFeed }) => {
  return (
    <S.Dropdown>
      <li role="presentation" onClick={handleDeleteFeed}>
        삭제하기
      </li>
    </S.Dropdown>
  )
}

export default Dropdown
