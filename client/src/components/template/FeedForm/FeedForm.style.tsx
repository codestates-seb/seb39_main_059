import FormTextInput from '@Modules/FormTextInput'
import styled, { css } from 'styled-components'
import Button from '@Atoms/Button'

export const FeedFormLayout = styled.form.attrs({
  onSubmit: (e: React.FormEvent<HTMLFormElement>) => e.preventDefault(),
})(
  () => css`
    margin: 0px 20px;
    display: flex;
    flex-direction: column;
  `,
)

export const ImageInputBox = styled.div`
  margin-bottom: 30px;
`

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
