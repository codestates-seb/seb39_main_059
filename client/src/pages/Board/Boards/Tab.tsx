import { FC, useState } from 'react'
import * as S from './Tab.style'

interface Prop {
  onChange: () => void
}

const options = ['전체', '일상', '궁금해요', '실종신고', '동물병원']

const Tab: FC<Prop> = ({ onChange }) => {
  const [selected, setSelected] = useState<number>(0)

  const handleClick = (idx: number): void => setSelected(idx)

  return (
    <S.TabLayout>
      {options.map((item, idx) => (
        <S.TabMenu
          onClick={() => {
            handleClick(idx)
            onChange()
          }}
          key={`${item}`}
          selected={idx === selected}
        >
          {item}
        </S.TabMenu>
      ))}
    </S.TabLayout>
  )
}
export default Tab
