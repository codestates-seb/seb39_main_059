import { FC } from 'react'
import Avatar from '@/components/atoms/Avatar'
import SvgButton from '@/components/atoms/SvgButton'
import * as S from './Comment.style'
import { AvatarCssProp, SvgButtonCssProp } from './Comment.style'

interface Props {
  imageUrl: string
  userName: string
  children: JSX.Element | string
}

const Comment: FC<Props> = ({ imageUrl, userName, children }) => {
  return (
    <S.Commentlayout>
      <S.AvatarBox>
        <Avatar
          diameter="25px"
          imageUrl={imageUrl}
          name={userName}
          cssProp={AvatarCssProp}
        />
      </S.AvatarBox>
      <S.ContentBox>{children}</S.ContentBox>
      <SvgButton icon="HeartIcon" cssProp={SvgButtonCssProp} />
    </S.Commentlayout>
  )
}

export default Comment
