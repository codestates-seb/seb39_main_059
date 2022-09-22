import styled from 'styled-components'

export interface Props {
  src: string
}

export const Img = styled.img`
  border-radius: 0.75rem;
  width: 100%;
  object-fit: cover;
`

const Image = ({ src }: Props): React.ReactElement => {
  return <Img src={src} />
}

export default Image
