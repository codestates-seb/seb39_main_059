import * as S from './BreedSelection.style'

const breedOptions = [
  '코리안 숏 헤어',
  '페르시안 고양이',
  '브리티쉬 숏 헤어',
  '샴',
  '렉돌',
  '아메리칸 숏 헤어',
  '아비시니안',
  '아메리칸 컬',
  '노르웨이 숲',
  '스코티시 폴드',
  '먼 치킨',
  '셀커크 렉스',
]

const BreedSelection = () => {
  return (
    <S.BreedSelectionLayout>
      <S.Select defaultValue="">
        <option key="placeholder" value="" disabled>
          품종선택
        </option>
        {breedOptions.map(option => (
          <option value={`${option}`} key={`${option}`}>
            {option}
          </option>
        ))}
      </S.Select>
    </S.BreedSelectionLayout>
  )
}
export default BreedSelection
