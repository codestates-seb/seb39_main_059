import styled from 'styled-components'
import kakaoLogin from '@Assets/kakao-login.png'

interface Props {
  onClick: () => void
}

const Button = styled.button`
  display: block;
  margin: 3.5rem 0;
  &hover {
    cursor: pointer;
  }
`
const Image = styled.img``

const SocialLoginButton: React.FC<Props> = props => {
  return (
    <Button {...props}>
      <Image src={kakaoLogin} />
    </Button>
  )
}

export default SocialLoginButton
