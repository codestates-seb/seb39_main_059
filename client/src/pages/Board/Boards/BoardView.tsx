import { BOARD_PATH } from '@Routes/board.routes'
import { FC } from 'react'
import { Link } from 'react-router-dom'
import * as S from './BoardView.style'

interface Prop {
  boardId: number
  createdDate: string
  name: string
  picture: string
  title: string
  viewCount: number
}

const BoardView: FC<Prop> = ({
  boardId,
  createdDate,
  name,
  picture,
  title,
  viewCount,
}) => {
  return (
    <Link to={`/${BOARD_PATH}/${boardId}`}>
      <S.BoardViewLayout>
        <S.TextBox>
          <S.TitleText>{title}</S.TitleText>
          <S.InfoBox>
            <S.InfoText bold>{name}</S.InfoText>
            <S.InfoText>
              {createdDate
                .substring(0, createdDate.indexOf('T'))
                .replaceAll('-', '.')}
            </S.InfoText>
            <S.InfoText bold>조회수</S.InfoText>
            <S.InfoText>{viewCount}</S.InfoText>
          </S.InfoBox>
        </S.TextBox>
        <S.ImageView src={picture} alt={`${title}`} />
      </S.BoardViewLayout>
    </Link>
  )
}

export default BoardView
