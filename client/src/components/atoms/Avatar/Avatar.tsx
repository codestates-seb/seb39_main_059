import { FC } from 'react'
import { CSSProp } from 'styled-components'
import * as S from './Avatar.style'

export interface Props {
  diameter: string
  fontSize?: string
  imageUrl?: string
  name?: string
  cssProp?: CSSProp
}

const Avatar: FC<Props> = ({ diameter, fontSize, imageUrl, name, cssProp }) => (
  <S.AvatarLayout cssProp={cssProp}>
    <S.CircleImage
      width={diameter}
      height={diameter}
      backgroundImage={imageUrl}
    />
    {name && <S.Name fontSize={fontSize}>{name}</S.Name>}
  </S.AvatarLayout>
)

export default Avatar
