import { FC } from 'react'
import { CSSProp } from 'styled-components'
import { CircleImage, Container, Name } from './styles'

export interface Props {
  diameter: string
  fontSize?: string
  imageUrl?: string
  name?: string
  cssProp?: CSSProp
}

const Avatar: FC<Props> = ({ diameter, fontSize, imageUrl, name, cssProp }) => (
  <Container cssProp={cssProp}>
    <CircleImage width={diameter} height={diameter} backgroundImage={imageUrl} />
    {name && <Name fontSize={fontSize}>{name}</Name>}
  </Container>
)

export default Avatar
