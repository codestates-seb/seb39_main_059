import { axiosInstance } from '@Utils/instance'
import { setUserToken } from '@Utils/tokenControl'

export const renewAccessToken = async (refreshToken: string) => {
  const { data } = await axiosInstance.post('/reissue', {
    refreshToken,
  })
  const { accessToken: renewedAccessToken, refreshToken: renewedRefreshToken } =
    data

  if (!renewedAccessToken || !renewedRefreshToken) {
    throw new Error('토큰을 받지 못했습니다.')
  }
  setUserToken('ACCESS_TOKEN', renewedAccessToken)
  setUserToken('REFRESH_TOKEN', renewedRefreshToken)

  return { renewedAccessToken, renewedRefreshToken } as {
    renewedAccessToken: string
    renewedRefreshToken: string
  }
}
