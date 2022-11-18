import { useNavigate } from 'react-router-dom'
import Header from '@Modules/Header'
import { logoutAsync } from '@/redux/actions/userAction'
import { useAppDispatch } from '@/redux/store'
import { persistor } from '../../index'

const Profile = () => {
  const dispatch = useAppDispatch()
  const navigate = useNavigate()

  const purge = async () => {
    await persistor.purge()
  }

  const logoutHandler = async () => {
    if (window.confirm('로그아웃 하시겠습니까?')) {
      await dispatch(logoutAsync())
      setTimeout(() => purge(), 200)
      navigate('/')
    }
  }
  return (
    <>
      <Header>profile</Header>
      <button type="button" onClick={logoutHandler}>
        logout
      </button>
    </>
  )
}
export default Profile
