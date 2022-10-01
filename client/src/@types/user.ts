export interface User {
  isLoading: boolean
  isSignup: boolean
  userInfo: UserInfo | null
}

export interface UserInfo {
  email: string
  password: string
  location: null
  name: string
  profileImage: string | null
}

// 로그인 요청 페이로드의 타입을 미리 생성했습니다.
// export interface LoginPayload {
//   email: string
//   password: string
// }
