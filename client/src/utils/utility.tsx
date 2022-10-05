export const isEmpty = (value: unknown): boolean => {
  return (
    value === '' ||
    value === null ||
    value === undefined ||
    (typeof value === 'object' && !Object.keys(value).length)
  )
}

export function isValidEmail(email: string): boolean {
  const isMatch = email.match(
    /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/,
  )
  return !!isMatch
}

export function isValidPhoneNumber(phoneNumber: string): boolean {
  const isVaild = phoneNumber.match(/^01(?:0|1|[6-9])-(?:\d{3}|\d{4})-\d{4}$/)
  return !!isVaild
}

export function getRangeArray(start: number, end: number, step?: number) {
  const res = []
  if (typeof step === 'undefined') {
    step = 1
  }
  for (let i = start; i < end; i += step) {
    res.push(i)
  }
  return res
}
