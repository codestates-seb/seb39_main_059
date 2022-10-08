import FeedForm from '@Template/FeedForm'
import { useEffect } from 'react'
import { useNavigate } from 'react-router-dom'
import { useAppSelector } from '@/redux/store'

const NewFeed = () => {
  const navigate = useNavigate()
  const { isLogin } = useAppSelector(state => state.user)

  useEffect(() => {
    if (!isLogin) {
      alert('로그인이 필요한 서비스입니다🐱')
      navigate('/login')
    }
  }, [])

  return <FeedForm />
}
export default NewFeed
