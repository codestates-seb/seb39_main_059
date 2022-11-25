import { axiosInstance } from '@Utils/instance'

export const renewAccessToken = async (refreshToken: string) => {
  const { data } = await axiosInstance.post('/reissue', {
    refreshToken,
  })
  const { accessToken } = data

  if (!accessToken) {
    throw new Error('토큰을 받지 못했습니다.')
  }

  return accessToken as string
}
