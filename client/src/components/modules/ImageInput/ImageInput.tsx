import { CameraIcon } from '@Assets/icons'
import Text from '@Atoms/Text'
import {
  ChangeEvent,
  FC,
  forwardRef,
  useImperativeHandle,
  useRef,
  useState,
} from 'react'
import { CSSProp } from 'styled-components'
import * as S from './ImageInput.style'

export interface Props {
  /** name of input */
  inputName: string
  /** onChange handler(setState) */
  onChange?: (e: React.ChangeEvent<HTMLInputElement>) => void
  /** onFocusOut handler */
  onFocusOut?: (e: React.FocusEvent<HTMLInputElement>) => void
  desc?: string
  cssProp?: CSSProp
  imgCssProp?: CSSProp
}

const ImageInput: FC<Props> = forwardRef(({ inputName, ...props }, ref) => {
  const inputRef = useRef(null)
  // useImperativeHandle 은 reference를 맵핑한다.
  useImperativeHandle(ref, () => inputRef.current)
  const [files, setFiles] = useState<File | null>(null)
  const [showImages, setShowImages] = useState<string[]>([])
  const uploadHandler = async (event: ChangeEvent<HTMLInputElement>) => {
    if (event.target.files) {
      setFiles(event.target.files[0])
    }
    // formdata에 담기
    const formData = new FormData()
    if (files) {
      formData.append('file', files)
    }
    // result 갱신
    const result: string = await fetch('server-api', {
      method: 'POST',
      body: formData,
      headers: {
        authorization: 'bearer TOKENSTRING',
      },
    })
      .then(res => res.json())
      .then(body => body.url)
    // showImages 갱신
    if (result) setShowImages(prev => [...prev, result])
  }
  return (
    <S.ImageInputlayout cssProp={props.cssProp}>
      <S.ImgLabel imgCssProp={props.imgCssProp} htmlFor="image-input">
        {/* click */}
        <CameraIcon />
      </S.ImgLabel>
      <S.HiddenInput
        type="file"
        id="image-input"
        name={inputName}
        onChange={uploadHandler}
        ref={inputRef}
        {...props}
      />
      {showImages.map(src => (
        <div key={src}>
          <img src={src} alt={src} />
        </div>
      ))}
      {props.desc && (
        <Text fontSize="sm" color="softGray" fontWeight="regular">
          {props.desc}
        </Text>
      )}
    </S.ImageInputlayout>
  )
})
export default ImageInput
