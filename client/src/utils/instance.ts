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
    delete header.tokenNeeded
    header.Authorization = ''
    return config
  }

  if (isTokenExpired(accessToken)) {
    const refreshToken = retrieveUserToken('REFRESH_TOKEN')
    const { renewedAccessToken, renewedRefreshToken } = await renewAccessToken(
      refreshToken!,
    )

    if (!renewedAccessToken) {
      throw new axios.Cancel()
    }

    accessToken = renewedAccessToken
  }

  delete header.tokenNeeded
  header.Authorization = `Bearer ${accessToken}`

  return config
}

axiosInstance.interceptors.request.use(authorizationSetter, error =>
  // Do something with request error
  Promise.reject(error),
)
