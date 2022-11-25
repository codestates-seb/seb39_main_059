import { useLocation, useNavigate } from 'react-router-dom'
import { useEffect } from 'react'
import PostForm, { FormValue } from '@Template/PostForm'
import { SubmitHandler } from 'react-hook-form'
import { BOARD_PATH } from '@Routes/board.routes'
import { axiosInstance } from '@Utils/instance'
import Header from '@Modules/Header'
import { useAppSelector } from '@/redux/store'
import * as S from './NewBoard.style'

const NewBoard = () => {
  const { pathname } = useLocation()
  const navigate = useNavigate()
  const { isLogin } = useAppSelector(state => state.user)

  // login 컴포넌트의 useeffect로 인해 리다이렉션 적용안되는 중
  const accessToken = localStorage.getItem('ACCESS_TOKEN')
  useEffect(() => {
    if (!accessToken) {
      alert('로그인이 필요한 서비스입니다🐱')
      navigate(`/login?redirectUrl=${pathname}`)
    }
  })

  const onSubmitHandler: SubmitHandler<FormValue> = async data => {
    const { picture, body, title } = data
    const formData = {
      body,
      pictures: [
        {
          picture,
        },
      ],
      tags: [
        {
          tag: '궁금해요',
        },
      ],
      title,
    }
    const axiosResponse = await axiosInstance.post(BOARD_PATH, formData, {
      headers: {
        contentType: 'application/json',
        tokenNeeded: true
      },
    })
    if (axiosResponse.status === 201) {
      navigate(-1)
    } else {
      alert('생성 실패!')
    }
  }

  return (
    <S.NewBoardLayout>
      <Header>집사생활 글쓰기</Header>
      <PostForm onSubmitHandler={onSubmitHandler} hasTitle imgRequired />
    </S.NewBoardLayout>
  )
}
export default NewBoard
