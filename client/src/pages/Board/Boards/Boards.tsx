import { FC, useEffect, useState } from 'react'
import { useAppDispatch, useAppSelector } from '@/redux/store'
import * as S from './Boards.style'
import { getBoardsAsync } from '@/redux/actions/boardsAction'
import BoardView from './BoardView'
import Tab from './Tab'
import BreedSelection from './BreedSelection'

const Boards: FC = () => {
  const dispatch = useAppDispatch()
  const { items } = useAppSelector(state => state.boards)

  const [demo, setDemo] = useState(false)
  const filterItem = (b: boolean) => {
    if (!b) return items
    return items.filter(({ boardId }) => [9, 10, 6].includes(boardId))
  }

  useEffect(() => {
    dispatch(getBoardsAsync())
  }, [dispatch])
  return (
    <S.BoardLayout>
      <BreedSelection />
      <Tab onChange={() => setDemo(prev => !prev)} />
      <S.BoardBox>
        {items &&
          filterItem(demo).map(item => {
            return (
              <BoardView
                key={`${item.boardId}`}
                boardId={item.boardId}
                title={item.title}
                name={item.name}
                createdDate={item.createdDate}
                viewCount={item.viewCount}
                picture={item.picture}
              />
            )
          })}
      </S.BoardBox>
    </S.BoardLayout>
  )
}
export default Boards

interface TabProps {
  options: string[]
}
