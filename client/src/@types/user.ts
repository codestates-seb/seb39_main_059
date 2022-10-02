export interface UserInitialState {
  isLoading: boolean
  isSignup: boolean
  isLogin: boolean
  userInfo: UserInfo
}

export interface UserInfo {
  email?: string
  password?: string
  location?: null
  name: string
  profileImage: string | null
  userId?: string
}

export interface LoginPayload {
  email: string
  password: string
}
