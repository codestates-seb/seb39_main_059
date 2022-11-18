import { logoutAsync } from "@/redux/actions/userAction"
import { useAppDispatch } from "@/redux/store"

const Profile = () => {
  const dispatch = useAppDispatch()
  
  const logoutHandler = () => {
    console.log('로그아웃 click')
    dispatch(logoutAsync(null))
  }
  return (
    <>
      <div>Profile</div>
      <button type="button" onClick={logoutHandler}>logout</button>
    </>
  )
}
export default Profile
