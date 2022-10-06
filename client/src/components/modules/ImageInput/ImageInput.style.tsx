import styled, { css, CSSProp } from 'styled-components'

export const ImageInputlayout = styled.div<{ cssProp?: CSSProp }>(
  ({ cssProp }) => css`
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 13px;
    ${cssProp}
    position: relative;
  `,
)

export const ImgLabel = styled.label<{ imgCssProp?: CSSProp }>(
  ({ imgCssProp }) => css`
    display: flex;
    width: 86px;
    aspect-ratio: 1/1;
    justify-content: center;
    align-items: center;

    background: #ffffff;
    box-shadow: 0px 1px 3px 1px rgba(0, 0, 0, 0.15);
    border-radius: 10px;
    cursor: pointer;
    ${imgCssProp}
  `,
)

export const HiddenInput = styled.input`
  position: absolute;
  z-index: -1;
`
