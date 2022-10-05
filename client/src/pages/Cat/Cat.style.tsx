import styled from "styled-components";

export const Select = styled.select`
  & > option[value=""][disabled] {
    display: none;
  }
`