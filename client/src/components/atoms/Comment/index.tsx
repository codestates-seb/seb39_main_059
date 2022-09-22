import { FC } from 'react'
import styled from 'styled-components'
import Avatar from '@Components/atoms/Avatar'
import SvgIcon from '@/components/atoms/SvgButton';

interface Props {
  imageUrl: string;
  userName: string;
  children: JSX.Element | string
}

const Commentlayout = styled.li`
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 7px;
  margin-top: 7px;
`

const Comment: FC<Props> = ({ imageUrl, userName, children }) => {
  return (
    <Commentlayout>
      <Avatar diameter='25px' imageUrl={imageUrl} name={userName} />
      {children}
      <SvgIcon icon='HeartIcon' />
    </Commentlayout>
  )
}

export default Comment
