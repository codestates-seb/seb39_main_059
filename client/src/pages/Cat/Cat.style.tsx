import Button from '@Atoms/Button'
import Input from '@Atoms/Input'
import FormTextInput from '@Modules/FormTextInput'
import ImageInput from '@Modules/ImageInput'
import styled, { css, DefaultTheme } from 'styled-components'

export const CatLayout = styled.div`
  padding-bottom: 25px;
`

export const CatForm = styled.form`
  margin: 0px 20px;
`

export const Header = styled.div`
  display: grid;
  grid-template-columns: 24px auto 24px;
  margin: 15px 20px 12px 20px;
  span {
    margin: auto;
  }
`

export const CatImageInput = styled(ImageInput).attrs({
  imgCssProp: css`
    width: 100%;
    height: 335 / 300;
  `,
  cssProp: css`
    margin-bottom: 25px;
  `,
})``

const catInputCss = (theme: DefaultTheme) => {
  return css`
    height: 58px;
    width: 100%;

    background: transparent;
    box-shadow: 0px 1px 3px 1px rgba(0, 0, 0, 0.05);
    border-radius: 10px;
    border: none;
    padding-left: 22px;

    color: ${theme.color.deepGray};
    font-weight: ${theme.fontWeight.bold};
    font-size: ${theme.fontSize.lg};

    margin-bottom: 15px;
  `
}

export const CatNameInput = styled(Input)(
  ({ theme }) => css`
    ${catInputCss(theme)}
    &::placeholder {
      font-size: ${theme.fontSize.lg};
      color: ${theme.color.softGray};
    }
  `,
)

export const CatBirthInput = styled(Input)(
  ({ theme }) => css`
    ${catInputCss(theme)}
    &:focus::before,
    &::before {
      content: attr(data-placeholder);
      color: ${theme.color.softGray};
    }
    &:valid::before {
      display: none;
    }
    &::-webkit-datetime-edit-text,
    &::-webkit-datetime-edit-month-field,
    &::-webkit-datetime-edit-day-field,
    &::-webkit-datetime-edit-year-field {
      display: none;
    }
    &:valid {
      &::-webkit-datetime-edit-text,
      &::-webkit-datetime-edit-month-field,
      &::-webkit-datetime-edit-day-field,
      &::-webkit-datetime-edit-year-field {
        display: inline;
        color: ${theme.color.deepGray};
      }
    }
  `,
)

export const SelectsBox = styled.div`
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 18px;
`

export const Select = styled.select(
  ({ theme }) => css`
    height: 58px;
    width: 100%;

    background: transparent;
    box-shadow: 0px 1px 3px 1px rgba(0, 0, 0, 0.05);
    border-radius: 10px;
    border: none;
    padding-left: 22px;

    font-weight: ${theme.fontWeight.bold};
    font-size: ${theme.fontSize.lg};

    color: ${theme.color.softGray};

    & > option[value=''][disabled] {
      display: none;
    }
    &:valid {
      color: ${theme.color.deepGray};
    }

    margin-bottom: 15px;
  `,
)

export const DescArea = styled(FormTextInput).attrs({ rows: 3 })(
  ({ theme }) => css`
    border: none;
    background: transparent;
    box-shadow: 0px 1px 3px 1px rgba(0, 0, 0, 0.05);
    padding: 10px;
    padding-left: 22px;
    margin-bottom: 15px;
    width: 100%;
    color: ${theme.color.deepGray};
    &::placeholder {
      font-size: ${theme.fontSize.lg};
      color: ${theme.color.softGray};
      font-weight: ${theme.fontWeight.bold};
    }
  `,
)

export const SubmitButton = styled(Button).attrs({
  color: 'white',
  backgroundColor: 'primary',
  fontSize: 'md',
  fontWeight: 'bold',
  type: 'submit',
})`
  width: 100%;
  height: 40px;
`
