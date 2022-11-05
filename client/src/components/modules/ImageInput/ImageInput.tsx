import { CameraIcon } from '@Assets/icons'
import Image from '@Atoms/Image'
import Text from '@Atoms/Text'
import { fileExtensionValid, isEmpty } from '@Utils/utility'
import { FC, forwardRef, useImperativeHandle, useRef, useState } from 'react'
import { CSSProp } from 'styled-components'
import * as S from './ImageInput.style'

// 허용가능한 확장자 목록!
const ALLOW_FILE_EXTENSION = 'jpg,jpeg,png'
const FILE_SIZE_MAX_LIMIT = 4 * 1024 * 1024 // 4MB

const fileUploadValidHandler = (file: File): boolean => {
  // 파일 확장자 체크
  if (!fileExtensionValid(file.name, ALLOW_FILE_EXTENSION)) {
    alert(
      `업로드 가능한 확장자가 아닙니다. [가능한 확장자 : ${ALLOW_FILE_EXTENSION}]`,
    )
    return false
  }

  // 파일 용량 체크
  if (file.size > FILE_SIZE_MAX_LIMIT) {
    alert('업로드 가능한 최대 용량은 5MB입니다. ')
    return false
  }

  return true
}

export interface Props {
  /** name of input */
  inputName: string
  /** onChange handler(setState) */
  onChange?: (e: React.ChangeEvent<HTMLInputElement>) => void
  /** onFocusOut handler */
  onFocusOut?: (e: React.FocusEvent<HTMLInputElement>) => void
  /** uploadHandler */
  onPost: (FormData: FormData) => Promise<string>
  // onUploadImage?:
  desc?: string
  cssProp?: CSSProp
  imgCssProp?: CSSProp
}

const ImageInput: FC<Props> = forwardRef(
  ({ inputName, onPost, ...props }, ref) => {
    const [imgUrl, setImgUrl] = useState('')

    const inputRef = useRef(null)
    const urlRef = useRef<HTMLInputElement>(null)
    useImperativeHandle(ref, () => urlRef.current)

    const fileUploadHandler = async (
      event: React.ChangeEvent<HTMLInputElement>,
    ) => {
      const target = event.currentTarget
      const file = (target.files as FileList)[0]

      if (!fileUploadValidHandler(file)) {
        target.value = ''
        return
      }

      // !!중요1. formData활용!!
      const formData = new FormData()
      formData.append('images', file)

      const ImgUrl = await onPost(formData)
      // 파일 업로드 성공!
      if (isEmpty(ImgUrl)) alert('파일 업로드 실패')

      setImgUrl(ImgUrl)
    }

    return (
      <S.ImageInputlayout cssProp={props.cssProp}>
        <S.ImgLabel imgCssProp={props.imgCssProp} htmlFor="image-input">
          {isEmpty(imgUrl) ? <CameraIcon /> : <Image src={imgUrl} />}
        </S.ImgLabel>
        <S.HiddenInput
          type="file"
          id="image-input"
          name={inputName}
          onChange={fileUploadHandler}
          ref={inputRef}
        />
        <S.HiddenInput type="text" value={imgUrl} {...props} ref={urlRef} />
        {props.desc && (
          <Text fontSize="sm" color="softGray" fontWeight="regular">
            {props.desc}
          </Text>
        )}
      </S.ImageInputlayout>
    )
  },
)

export default ImageInput
