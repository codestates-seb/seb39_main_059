import { useNavigate } from 'react-router-dom'
import Button from '@Atoms/Button'
import { CatspiceList as CatBreedList, CatWeightList } from '@/constant'
import * as S from './Cat.style'

const Cat = () => {
  const navigate = useNavigate()
  return (
    <div>
      <div className="header">
        <button type="button" onClick={() => navigate(-1)}>
          back
        </button>
        <span>캣 프로필</span>
        <button type="button">edit</button>
      </div>
      <form className="new-cat">
        <input type="file" name="image-input" />
        <input type="text" name="name" />
        <input type="date" name="birth" />
        <S.Select>
          <option value="" disabled selected>
            몸무게
          </option>
          {CatWeightList.map(weight => (
            <option value={`${weight}`}>{`${weight} kg`}</option>
          ))}
        </S.Select>
        <S.Select>
          <option value="" disabled selected>
            성별
          </option>
          <option value="male">♂︎</option>
          <option value="female">♀︎</option>
        </S.Select>
        <S.Select>
          <option value="" disabled selected>
            폼종
          </option>
          {CatBreedList.map(breed => (
            <option value={`${breed}`}>{`${breed}`}</option>
          ))}
        </S.Select>
        <Button
          color="white"
          backgroundColor="primary"
          fontSize="md"
          fontWeight="bold"
          type="submit"
        >
          등록하기
        </Button>
      </form>
    </div>
  )
}
export default Cat
