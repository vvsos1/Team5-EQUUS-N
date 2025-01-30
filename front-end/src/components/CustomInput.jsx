import Icon from './Icon';
import classNames from 'classnames';
import { useState } from 'react';

/**
 *
 * @param {string} label - 제목
 * @param {string} hint - 플레이스홀더
 * @param {string} content - 초기값
 * @param {function} setContent - 값 변경 함수
 * @param {ReactNode} addOn - 오른쪽 아이콘
 * @param {string} condition - 하단 조건
 * @param {boolean} isPassword - 비밀번호 여부
 * @param {boolean} disabled - 비활성화 여부
 * @param {boolean} isOutlined - 테두리/배경 여부
 * @returns
 */
export default function CustomInput({
  label,
  hint,
  content = '',
  setContent,
  addOn,
  condition,
  isPassword,
  disabled = setContent ? false : true, // setContent가 없으면 비활성화
  isOutlined = true,
}) {
  return (
    <div className='flex flex-col gap-2'>
      {/* 제목 */}
      {label && <p className='subtitle-2 text-gray-0'>{label}</p>}
      {/* 인풋 */}
      <div className='group relative flex flex-col gap-2'>
        <input
          className={classNames(
            'rounded-300 text-gray-0 flex w-full items-center justify-between border py-4 caret-gray-300',
            // 오른쪽 아이콘 있으면 입력 길이를 줄임
            addOn ? 'pr-14 pl-5' : 'px-5',
            // 테두리 여부
            isOutlined ? 'border-gray-600' : 'border-none bg-gray-800',
            // 포커스 시 스타일
            'placeholder:text-gray-500 focus:border-gray-300 focus:outline-none focus:placeholder:text-gray-400',
          )}
          placeholder={hint}
          value={content}
          type={isPassword ? 'password' : 'text'}
          onChange={(e) => setContent(e.target.value)}
          disabled={disabled}
        ></input>
        {/* 오른쪽 아이콘 */}
        {addOn && <div className='absolute top-4 right-5'>{addOn}</div>}
        {/* 하단 조건 */}
        {condition && (
          <p className='caption-1 text-right text-gray-500 group-focus-within:text-lime-300'>
            {condition}
          </p>
        )}
      </div>
    </div>
  );
}

// const [isPasswordVisible, setIsPasswordVisible] = useState(false);
// const [isAnonymous, setIsAnonymous] = useState(false);
// const [content, setContent] = useState('');
// const [content2, setContent2] = useState('소프티어 개발팀 정기회의');
// const [content3, setContent3] = useState('임세준');
/* <CustomInput label='이메일' hint='softeer05@hyundai.co.kr' />
      <CustomInput
        label='활동이름'
        content={content3}
        setContent={setContent3}
      />
      <CustomInput
        label='비밀번호'
        hint='비밀번호를 입력하세요'
        content={content}
        setContent={setContent}
        condition={'영문, 숫자, 특수 문자 포함 • 8글자 이상'}
        isPassword={isPasswordVisible}
        addOn={
          <button onClick={() => setIsPasswordVisible(!isPasswordVisible)}>
            <Icon
              name='eye'
              color={
                isPasswordVisible ?
                  'var(--color-gray-500)'
                : 'var(--color-lime-300)'
              }
            />
          </button>
        }
      />

      <CustomInput
        label='피드백 방식'
        hint='익명을 기본으로 설정할게요'
        addOn={
          <button onClick={() => setIsAnonymous(!isAnonymous)}>
            {isAnonymous ?
              <Icon name='checkBoxClick' />
            : <Icon name='checkBoxNone' />}
          </button>
        }
        isOutlined={false}
      />

      <CustomInput
        label='일정 이름'
        hint='일정 이름을 입력해주세요'
        content={content2}
        setContent={setContent2}
        isOutlined={false}
      />

      <CustomInput
        label='날짜'
        content='2025-02-17'
        addOn={<button onClick={() => {}}>{<Icon name='calendar' />}</button>}
        isOutlined={false}
      />

      <CustomInput
        hint='익명을 기본으로 설정할게요'
        content='PPT 파일 업로드'
        addOn={
          <button onClick={() => {}}>
            {<Icon name='deleteSmall' color='white' />}
          </button>
        }
        isOutlined={false}
      /> */
