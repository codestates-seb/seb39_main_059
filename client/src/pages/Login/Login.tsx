/* eslint-disable jsx-a11y/label-has-associated-control */
import { ChangeEvent, useState } from 'react'
import SocialLoginButton from '@Modules/SocialLoginButton'
import * as S from './Login.style'

const Login = () => {
  const [id, setId] = useState('')
  const [password, setPassword] = useState('')
  const [isShown, setIsSHown] = useState(false)

  const togglePassword = () => {
    setIsSHown(isShown => !isShown)
  }

  const handleIdChange = (e: ChangeEvent<HTMLInputElement>) => {
    setId(e.target.value)
  }

  const handlePasswordChange = (e: ChangeEvent<HTMLInputElement>) => {
    setPassword(e.target.value)
  }

  const handleLogin = () => {
    /* 일반 로그인 로직 */
  }

  const handleSocialLogin = () => {
    /* 소셜 로그인 로직 */
  }

  return (
    <S.LoginLayout>
      <S.Logo />
      <S.Description>여러분의 고양이를 자랑해보세요</S.Description>
      <S.InputBox>
        <S.LoginInput
          type="text"
          inputName="id"
          value={id}
          placeholder="아이디"
          onChange={handleIdChange}
        />
        <S.LoginInput
          type={isShown ? 'text' : 'password'}
          inputName="password"
          value={password}
          placeholder="비밀번호"
          onChange={handlePasswordChange}
        />
        <S.Checkbox>
          <input type="checkbox" checked={isShown} onChange={togglePassword} />
          <label htmlFor="checkbox">비밀번호 보기</label>
        </S.Checkbox>
      </S.InputBox>
      <S.LoginButton
        backgroundColor="primary"
        fontSize="lg"
        fontWeight="bold"
        color="white"
        onClick={handleLogin}
      >
        로그인
      </S.LoginButton>
      <S.ButtonBox>
        <S.FindButton>아이디/비밀번호 찾기</S.FindButton>
        <S.SignupButton>회원가입</S.SignupButton>
      </S.ButtonBox>
      <SocialLoginButton onClick={handleSocialLogin} />
    </S.LoginLayout>
  )
}

export default Login
