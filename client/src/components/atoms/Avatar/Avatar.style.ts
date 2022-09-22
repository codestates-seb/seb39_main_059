import styled, { css, CSSProp } from "styled-components";

import defaultProfile from "@Assets/default-profile.jpeg";

export const AvatarLayout = styled.div<{ cssProp?: CSSProp }>(
  ({ cssProp }) => css`
  display: inline-flex;
  justify-content: center;
  align-items: center;
  gap: 7px;
  ${cssProp}
  `
);

export const CircleImage = styled.div<React.CSSProperties>`
  ${({ width, height, backgroundImage }) => css`
    width: ${width};
    height: ${height};  
    background: no-repeat center/cover url(${backgroundImage ?? defaultProfile});
  `}
  border-radius: 50%;
`;

export const Name = styled.div<React.CSSProperties>`
  ${({ fontSize, theme }) => `
    font-size: ${fontSize ?? theme.fontSize.md};
    color: ${theme.color.black};
    font-weight: ${theme.fontWeight.bold};
  `}
`;