import { useLocation, useNavigate } from 'react-router-dom'
import { useEffect } from 'react'
import FeedForm from '@Template/FeedForm'
import { SubmitHandler } from 'react-hook-form'
import { FormValue } from '@Template/FeedForm/FeedForm'
import { BOARD_PATH } from '@Routes/board.routes'
import { axiosInstance } from '@Utils/instance'
import { useAppSelector } from '@/redux/store'

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
    console.log('게시글 올리는중')
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
    console.log(BOARD_PATH)
    console.log(formData)
    const axiosResponse = await axiosInstance.post(BOARD_PATH, formData, {
      headers: {
        contentType: 'application/json',
        Authorization: `Bearer ${localStorage.getItem('ACCESS_TOKEN')}`,
      },
    })
    console.log(axiosResponse)
    console.log('게시완료')
  }

  return (
    <FeedForm onSubmitHandler={onSubmitHandler} hasTitle imgRequired={false} />
  )
}
export default NewBoard
