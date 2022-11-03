import { useLocation, useNavigate } from 'react-router-dom'
import { useEffect } from 'react'
import FeedForm from '@Template/FeedForm'
import { useAppSelector } from '@/redux/store'

const NewBoard = () => {
  const {pathname} = useLocation()
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

  return (<FeedForm hasTitle/>)
}
export default NewBoard