import styled from 'styled-components'

export const Dropdown = styled.ul`
  display: flex;
  justify-content: center;
  align-items: center;
  position: absolute;
  z-index: 99;
  width: 100px;
  top: 25px;
  left: -55px;
  cursor: pointer;
  > li {
    background-color: white;
    padding: 10px 10px;
    box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
    font-size: ${({ theme }) => theme.fontSize.sm};
    color: ${({ theme }) => theme.color.black};
    font-weight: ${({ theme }) => theme.fontWeight.regular};
  }
`
