const calRem = (size: number): string => `${size / 16}rem`

const fontSize = {
  xs: calRem(9),
  sm: calRem(11),
  md: calRem(13),
  lg: calRem(15),
  xl: calRem(20),
}

const fontWeight = {
  thin: 300,
  regular: 400,
  bold: 500,
}

const color = {
  primary: '#F8ADB2',
  black: '#000000',
  deepGray: '#A0A0A0',
  softGray: '#E2E2E2',
  white: '#FFFFFF',
  red: '#FF0000',
  yellow: '#F8DE58',
}

export type FontSizeTypes = typeof fontSize
export type FontWeightTypes = typeof fontWeight
export type ColorTypes = typeof color

const theme = {
  fontSize,
  fontWeight,
  color,
}

export default theme
