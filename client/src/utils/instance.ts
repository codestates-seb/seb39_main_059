import axios, { AxiosRequestConfig } from 'axios'

export const axiosInstance = axios.create({
  baseURL: process.env.APP_API,
  timeout: 5000,
})

const retrieveUserToken = (tokenName: string) => localStorage.getItem(tokenName)

const setUserToken = (tokenName: string, tokenString: string) =>
  localStorage.setItem(tokenName, tokenString)

const renewAccessToken = async (refreshToken: string) => {
  console.log('refreshToken',refreshToken,' 으로 재요청')
  const { data } = await axiosInstance.post('/reissue', {
    accessToken: refreshToken,
    refreshToken,
  },)
  const { accessToken } = data

  if (!accessToken) {
    throw new Error('토큰을 받지 못했습니다.')
  }

  return accessToken as string
}

const isTokenExpired = (token: string) => {
  const [header, payload, sig] = token.split('.')

  if (!(header && payload && sig)) {
    throw new Error('유효하지 않은 토큰입니다.')
  }
  const expiresIn = window.atob(payload).match(/(?<=exp":)\w{1,}/g)
  const dueToExpire = Number(expiresIn) - Date.now() / 1000 < 5 * 60 // 5분

  return dueToExpire
}

async function authorizationSetter(config: AxiosRequestConfig) {
  const header = config.headers!

  if (!header?.tokenNeeded) return config

  let accessToken = retrieveUserToken('ACCESS_TOKEN')

  if (!accessToken) {
    throw new axios.Cancel(
      '유저 인증 정보가 존재하지 않습니다. 로그인 상태를 확인하세요.',
    )
    // test
    // const refreshToken = retrieveUserToken('REFRESH_TOKEN')
    // const renewedAccessToken = await renewAccessToken(refreshToken!)
    // if (!renewedAccessToken) {
    //   throw new axios.Cancel()
    // }
    // accessToken = renewedAccessToken
    // setUserToken('ACCESS_TOKEN', accessToken)
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

axiosInstance.interceptors.request.use(authorizationSetter, function (error) {
  // Do something with request error
  return Promise.reject(error)
})
