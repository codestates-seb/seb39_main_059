import { useState, FC, useRef } from 'react'
import SocialLoginButton from '@Modules/SocialLoginButton'
import { useForm, SubmitHandler } from 'react-hook-form'
import logo from '@Assets/logo.svg'
import { Link } from 'react-router-dom'
import * as S from './Signup.style'
import { useAppDispatch } from '@/redux/store'
import { signupAsync } from '@/redux/actions/userAction'

export interface FormValue {
  email: string
  password: string
  passwordCheck?: string
}

const Signup: FC = () => {
  const {
    register,
    handleSubmit,
    watch,
    formState: { errors },
  } = useForm<FormValue>()

  const [isShown, setIsSHown] = useState(false)
  const passwordRef = useRef<string | null>(null)
  passwordRef.current = watch('password')
  const dispatch = useAppDispatch()

  const togglePassword = () => {
    setIsSHown(isShown => !isShown)
  }

  const onSubmitHandler: SubmitHandler<FormValue> = data => {
    const defaultName = `집사${Math.floor(Math.random() * 10000) + 1}`

    dispatch(
      signupAsync({
        email: data.email,
        password: data.password,
        location: null,
        name: defaultName,
        profileImage: null,
      }),
    )
  }

  const handleSocialLogin = () => {
    /* 소셜 로그인 로직 */
  }

  return (
    <S.SignupLayout>
      <S.Logo>
        <img src={logo} alt="logo" />
      </S.Logo>
      <S.Description>여러분의 고양이를 자랑해보세요</S.Description>
      <S.InputForm onSubmit={handleSubmit(onSubmitHandler)} method="post">
        <S.SignupInput
          type="text"
          inputName="email"
          placeholder="이메일"
          {...register('email', { required: true, pattern: /^\S+@\S+$/i })}
        />
        {errors.email && errors.email.type === 'required' && (
          <S.ValidationSpan>이메일을 입력해 주세요.</S.ValidationSpan>
        )}
        {errors.email && errors.email.type === 'pattern' && (
          <S.ValidationSpan>올바른 이메일 형식이 아닙니다.</S.ValidationSpan>
        )}
        <S.SignupInput
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
          <S.ValidationSpan>비밀번호를 입력해 주세요.</S.ValidationSpan>
        )}
        {errors.password && errors.password.type === 'maxLength' && (
          <S.ValidationSpan>비밀번호는 20자 이하입니다.</S.ValidationSpan>
        )}
        {errors.password && errors.password.type === 'minLength' && (
          <S.ValidationSpan>비밀번호는 5자 이상입니다.</S.ValidationSpan>
        )}
        <S.SignupInput
          type={isShown ? 'text' : 'password'}
          inputName="passwordCheck"
          placeholder="비밀번호 확인"
          {...register('passwordCheck', {
            required: true,
            validate: value => value === passwordRef.current,
          })}
        />
        {errors.passwordCheck && errors.passwordCheck.type === 'validate' && (
          <S.ValidationSpan>비밀번호가 일치하지 않습니다.</S.ValidationSpan>
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
        <S.SignupButton
          backgroundColor="primary"
          fontSize="lg"
          fontWeight="bold"
          color="white"
          type="submit"
        >
          회원가입
        </S.SignupButton>
      </S.InputForm>
      <S.Box>
        <span>이미 가입하셨나요?</span>
        <Link to="/login">
          <S.LoginButton>로그인</S.LoginButton>
        </Link>
      </S.Box>
      <SocialLoginButton onClick={handleSocialLogin} />
    </S.SignupLayout>
  )
}

export default Signup
