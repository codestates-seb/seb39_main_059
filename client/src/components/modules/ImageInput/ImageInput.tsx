import { CameraIcon } from '@Assets/icons'
import { ChangeEvent, FC, useState } from 'react'
import * as S from './ImageInput.style'

export interface Props {
  /** name of input */
  inputName: string
  /** onChange handler(setState) */
  onChange?: (e: React.ChangeEvent<HTMLInputElement>) => void
  /** onFocusOut handler */
  onFocusOut?: (e: React.FocusEvent<HTMLInputElement>) => void
}

const ImageInput: FC<Props> = ({ inputName, ...props }) => {
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
    <>
      <S.ImgLabel htmlFor="image-input">
        {/* click */}
        <CameraIcon />
      </S.ImgLabel>
      <S.HiddenInput
        type="file"
        id="image-input"
        name={inputName}
        onChange={uploadHandler}
        {...props}
      />
      {showImages.map(src => (
        <div key={src}>
          <img src={src} alt={src} />
        </div>
      ))}
    </>
  )
}
export default ImageInput
