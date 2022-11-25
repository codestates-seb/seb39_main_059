import { renewAccessToken } from '@Api/auth'
import axios, { AxiosRequestConfig } from 'axios'
import { isTokenExpired, retrieveUserToken, setUserToken } from './tokenControl'

export const axiosInstance = axios.create({
  baseURL: process.env.APP_API,
  timeout: 5000,
})

async function authorizationSetter(config: AxiosRequestConfig) {
  const header = config.headers!

  if (!header?.tokenNeeded) return config

  let accessToken = retrieveUserToken('ACCESS_TOKEN')

  if (!accessToken) {
    throw new axios.Cancel(
      '유저 인증 정보가 존재하지 않습니다. 로그인 상태를 확인하세요.',
    )
  }

  if (isTokenExpired(accessToken)) {
    const refreshToken = retrieveUserToken('ACCESS_TOKEN')
    const renewedAccessToken = await renewAccessToken(refreshToken!)
    if (!renewedAccessToken) {
      throw new axios.Cancel()
    }
    accessToken = renewedAccessToken
    setUserToken('ACCESS_TOKEN', accessToken)
  }

  delete header.tokenNeeded
  header.Authorization = `Bearer ${accessToken}`

  return config
}

axiosInstance.interceptors.request.use(authorizationSetter, error =>
  // Do something with request error
  Promise.reject(error),
)
