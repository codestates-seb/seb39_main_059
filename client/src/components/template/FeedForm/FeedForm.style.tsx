import FormTextInput from '@Modules/FormTextInput'
import styled, { css } from 'styled-components'
import Button from '@Atoms/Button'
import ImageInput from '@Modules/ImageInput'

export const FeedFormLayout = styled.form(
  () => css`
    margin: 0px 20px;
    display: flex;
    flex-direction: column;
  `,
)

export const ImageInputBox = styled.div`
  margin-bottom: 30px;
`

export const FeedImageInput = styled(ImageInput).attrs({
  imgCssProp: css`
    width: 100%;
    height: 335 / 300;
  `,
  cssProp: css`
    margin-bottom: 25px;
  `,
})``

export const TitleInput = styled(FormTextInput).attrs({ rows: 1 })(
({ theme }) => css`
    font-size: ${theme.fontSize.md};
    padding: 15px 0px;
    border-top: 1px solid ${theme.color.softGray};
    &::placeholder {
      font-size: ${theme.fontSize.md};
    }
    border-top: 1px solid ${theme.color.softGray};
  `,
)

export const BodyInput = styled(FormTextInput).attrs({ rows: 10 })(
  ({ theme }) => css`
    font-size: ${theme.fontSize.md};
    padding: 15px 0px;
    border-top: 1px solid ${theme.color.softGray};
    &::placeholder {
      font-size: ${theme.fontSize.md};
    }
  `,
)

export const SubmitButton = styled(Button)`
  padding: 9px 0;
`

export const Header = styled.div`
  display: grid;
  grid-template-columns: 24px auto 24px;
  margin: 15px 20px 12px 20px;
  span {
    margin: auto;
  }
`
