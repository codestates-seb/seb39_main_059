export const retrieveUserToken = (tokenName: string) =>
  localStorage.getItem(tokenName)

export const setUserToken = (tokenName: string, tokenString: string) =>
  localStorage.setItem(tokenName, tokenString)

export const isTokenExpired = (token: string) => {
  const [header, payload, sig] = token.split('.')

  if (!(header && payload && sig)) {
    throw new Error('유효하지 않은 토큰입니다.')
  }
  const expiresIn = window.atob(payload).match(/(?<=exp":)\w{1,}/g)
  const dueToExpire = Number(expiresIn) - Date.now() / 1000 < 5 * 60 // 5분
  return dueToExpire
}
