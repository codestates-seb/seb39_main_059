import { useState, FC, useEffect } from 'react'
import SocialLoginButton from '@Modules/SocialLoginButton'
import { useForm, SubmitHandler } from 'react-hook-form'
import { FormValue } from '@pages/Signup/Signup'
import { ValidationSpan } from '@pages/Signup/Signup.style'
import { useNavigate } from 'react-router-dom'
import { FEED_PATH } from '@Routes/feed.routes'
import logo from '@Assets/logo.svg'
import * as S from './Login.style'
import { useAppDispatch, useAppSelector } from '@/redux/store'
import { loginAsync } from '@/redux/actions/userAction'

const Login: FC = () => {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<FormValue>()

  const [isShown, setIsSHown] = useState(false)
  const navigate = useNavigate()
  const dispatch = useAppDispatch()

  const { isLogin } = useAppSelector(state => state.user)

  useEffect(() => {
    if (isLogin) navigate(`/${FEED_PATH}`)
  }, [navigate, isLogin])

  const togglePassword = () => {
    setIsSHown(isShown => !isShown)
  }

  const onSubmitHandler: SubmitHandler<FormValue> = data => {
    dispatch(
      loginAsync({
        email: data.email,
        password: data.password,
      }),
    )
  }

  const handleSocialLogin = () => {
    /* 소셜 로그인 로직 */
  }

  return (
    <S.LoginLayout>
      <S.Logo>
        <img src={logo} alt="logo" />
      </S.Logo>
      <S.Description>여러분의 고양이를 자랑해보세요</S.Description>
      <S.InputForm onSubmit={handleSubmit(onSubmitHandler)} method="post">
        <S.LoginInput
          type="text"
          inputName="email"
          placeholder="이메일"
          {...register('email', { required: true, pattern: /^\S+@\S+$/i })}
        />
        {errors.email && errors.email.type === 'required' && (
          <ValidationSpan>이메일을 입력해 주세요.</ValidationSpan>
        )}
        {errors.email && errors.email.type === 'pattern' && (
          <ValidationSpan>올바른 이메일 형식이 아닙니다.</ValidationSpan>
        )}
        <S.LoginInput
          type={isShown ? 'text' : 'password'}
          inputName="password"
          placeholder="비밀번호"
          {...register('password', {
            required: true,
            maxLength: 20,
            minLength: 5,
          })}
        />
        {errors.password && errors.password.type === 'required' && (
          <ValidationSpan>비밀번호를 입력해 주세요.</ValidationSpan>
        )}
        {errors.password && errors.password.type === 'maxLength' && (
          <ValidationSpan>비밀번호는 20자 이하입니다.</ValidationSpan>
        )}
        {errors.password && errors.password.type === 'minLength' && (
          <ValidationSpan>비밀번호는 5자 이상입니다.</ValidationSpan>
        )}
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
        <S.LoginButton
          backgroundColor="primary"
          fontSize="lg"
          fontWeight="bold"
          color="white"
        >
          로그인
        </S.LoginButton>
      </S.InputForm>
      <S.ButtonBox>
        <S.FindButton>아이디/비밀번호 찾기</S.FindButton>
        <S.SignupButton>회원가입</S.SignupButton>
      </S.ButtonBox>
      <SocialLoginButton onClick={handleSocialLogin} />
    </S.LoginLayout>
  )
}

export default Login
