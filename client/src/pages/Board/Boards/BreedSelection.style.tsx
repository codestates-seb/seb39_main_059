import styled, { css } from 'styled-components'

export const BreedSelectionLayout = styled.div`
  padding: 0px 20px;
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

    color: ${theme.color.black};
    cursor: pointer;

    & > option[value=''][disabled] {
      display: none;
    }
    &:valid {
      color: ${theme.color.black};
    }
    &:focus-visible,
    &:active {
      outline: none;
    }

    margin-bottom: 15px;
  `,
)
