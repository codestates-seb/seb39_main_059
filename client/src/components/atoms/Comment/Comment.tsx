import { FC } from 'react'

import Avatar from '@/components/atoms/Avatar'
import SvgButton from '@/components/atoms/SvgButton'
import * as S from './Comment.style'

interface Props {
  imageUrl: string
  userName: string
  children: JSX.Element | string
}

const Comment: FC<Props> = ({ imageUrl, userName, children }) => {
  return (
    <S.Commentlayout>
      <Avatar diameter="25px" imageUrl={imageUrl} name={userName} />
      {children}
      <SvgButton icon="HeartIcon" />
    </S.Commentlayout>
  )
}

export default Comment
