import PostForm from '@Template/PostForm'
import { useEffect } from 'react'
import { useNavigate } from 'react-router-dom'
import { SubmitHandler } from 'react-hook-form'
import { FormValue } from '@Template/PostForm/PostForm'
import Header from '@Modules/Header'
import { useAppSelector } from '@/redux/store'
import * as S from './NewFeed.style'

const NewFeed = () => {
  const navigate = useNavigate()
  const { isLogin } = useAppSelector(state => state.user)

  useEffect(() => {
    if (!isLogin) {
      alert('로그인이 필요한 서비스입니다🐱')
      navigate('/login')
    }
  }, [isLogin])

  const onSubmitHandler: SubmitHandler<FormValue> = async data => {
    // do something
  }

  return (
    <S.NewFeedLayout>
      <Header>냥이생활 글쓰기</Header>
      <PostForm onSubmitHandler={onSubmitHandler} />
    </S.NewFeedLayout>
  )
}

export default NewFeed
