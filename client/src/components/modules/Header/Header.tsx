import SvgButton from '@Atoms/SvgButton'
import { Children, FC } from 'react'
import { useNavigate } from 'react-router-dom'
import * as S from './Header.style'

interface Props {
  children?: React.ReactNode
  backButtonDisabled?: boolean
}

const Header: FC<Props> = ({ children, backButtonDisabled }) => {
  const navigate = useNavigate()
  const [content, ...buttons] = Children.toArray(children)
  return (
    <S.HeaderLayout>
      <S.HeaderBox>
        {backButtonDisabled ? (
          <S.ButtonBox />
        ) : (
          <SvgButton icon="Back" onClick={() => navigate(-1)} />
        )}
        <S.TextBox>{content}</S.TextBox>
        <S.ButtonBox>{buttons}</S.ButtonBox>
      </S.HeaderBox>
      <S.MarginBox />
    </S.HeaderLayout>
  )
}

export default Header
