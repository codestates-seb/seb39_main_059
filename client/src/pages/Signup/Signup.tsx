import { ChangeEvent, useState } from 'react'
import SocialLoginButton from '@Modules/SocialLoginButton'
import * as S from './Signup.style'

const Login = () => {
  const [id, setId] = useState('')
  const [password, setPassword] = useState('')
  const [passwordCheck, setPasswordCheck] = useState('')
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

  const handlePasswordCheckChange = (e: ChangeEvent<HTMLInputElement>) => {
    setPasswordCheck(e.target.value)
  }

  const handleSignup = () => {
    /* 일반 회원가입 로직 */
  }

  const handleSocialLogin = () => {
    /* 소셜 로그인 로직 */
  }

  return (
    <S.SignupLayout>
      <S.Logo />
      <S.Description>여러분의 고양이를 자랑해보세요</S.Description>
      <S.InputForm>
        <S.SignupInput
          type="text"
          inputName="id"
          value={id}
          placeholder="아이디"
          onChange={handleIdChange}
        />
        <S.SignupInput
          type={isShown ? 'text' : 'password'}
          inputName="password"
          value={password}
          placeholder="비밀번호"
          onChange={handlePasswordChange}
        />
        <S.SignupInput
          type={isShown ? 'text' : 'password'}
          inputName="passwordCheck"
          value={passwordCheck}
          placeholder="비밀번호 확인"
          onChange={handlePasswordCheckChange}
        />
        <S.Checkbox>
          <label htmlFor="password">
            <input
              type="checkbox"
              checked={isShown}
              onChange={togglePassword}
            />
            비밀번호 보기
          </label>
        </S.Checkbox>
        <S.SignupButton
          backgroundColor="primary"
          fontSize="lg"
          fontWeight="bold"
          color="white"
          onClick={handleSignup}
        >
          회원가입
        </S.SignupButton>
        <S.Box>
          <span>이미 가입하셨나요?</span>
          <S.LoginButton>로그인</S.LoginButton>
        </S.Box>
        <SocialLoginButton onClick={handleSocialLogin} />
      </S.InputForm>
    </S.SignupLayout>
  )
}

export default Login
