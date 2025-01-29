import { useState } from 'react';
import Icon from './Icon';
import ProfileImage from './ProfileImage';
import Tag from './Tag';

export const FEEDBACK_TYPE = Object.freeze({
  SELF: 'SELF',
  RECEIVE: 'FROM. ',
  SEND: 'TO. ',
});

/**
 *
 * @param {*} feedbackType
 * @param {*} data
 * @returns
 */
export default function FeedBack({ feedbackType, data }) {
  const [isHeart, setIsHeart] = useState(data.heart);
  return (
    <div className='flex flex-col gap-5 border-b-8 border-gray-800 bg-gray-900 p-5'>
      <div className='flex items-end gap-3'>
        {/* 회고 아닌 경우에만 프로필 이미지 표시 */}
        {FEEDBACK_TYPE[feedbackType] !== FEEDBACK_TYPE.SELF && (
          <div className='aspect-square h-11'>
            <ProfileImage
              iconName={'@animals/' + data.profileImage.iconName}
              color={data.profileImage.color}
            />
          </div>
        )}
        <div className='flex flex-1 flex-col gap-0.5'>
          {/* 회고 아닌 경우 To. 또는 From. + 이름 표시 || 회고인 경우 팀 이름 표시 */}
          <p className='body-3 text-gray-0'>
            {FEEDBACK_TYPE[feedbackType] !== FEEDBACK_TYPE.SELF ?
              FEEDBACK_TYPE[feedbackType] + data.teamMate
            : data.teamName}
          </p>
          {/* 회고 아닌 경우 팀 이름 표시 || 회고인 경우 일정 표시 */}
          <p className='caption-1 text-gray-300'>
            {FEEDBACK_TYPE[feedbackType] !== FEEDBACK_TYPE.SELF ?
              data.teamName
            : data.scheduleName}
          </p>
        </div>
        {/* 회고인 경우 날짜 상단에 표시 */}
        {FEEDBACK_TYPE[feedbackType] === FEEDBACK_TYPE.SELF && (
          <p className='caption-1 text-gray-300'>{data.recordDate}</p>
        )}
      </div>
      <p className='body-1 text-gray-0'>{data.content}</p>
      {/* 회고 아닌 경우 키워드, 날짜, 하트 표시 */}
      {FEEDBACK_TYPE[feedbackType] !== FEEDBACK_TYPE.SELF && (
        <>
          <div className='flex flex-wrap gap-2'>
            {data.keywords.map((keyword, i) => {
              return (
                <Tag key={i} type='KEYWORD'>
                  {keyword}
                </Tag>
              );
            })}
          </div>
          <div className='flex items-center justify-between'>
            <p className='caption-1 text-gray-300'>{data.recordDate}</p>
            {FEEDBACK_TYPE[feedbackType] === FEEDBACK_TYPE.RECEIVE &&
              // 받은 피드백의 경우 하트 토글 가능
              (isHeart ?
                <button onClick={() => setIsHeart(false)}>
                  <Icon name='heartFill' />
                </button>
              : <button onClick={() => setIsHeart(true)}>
                  <Icon name='heartDefault' />
                </button>)}
            {FEEDBACK_TYPE[feedbackType] === FEEDBACK_TYPE.SEND && isHeart && (
              //보낸 피드백의 경우 하트 받았는지 여부만 표시
              <div className='caption-1 flex items-center gap-1 text-gray-300'>
                {data.teamMate}에게 도움이 됐어요!
                <Icon name='heartFill' className={'scale-75'}></Icon>
              </div>
            )}
          </div>
        </>
      )}
    </div>
  );
}

// const feedbackData = [
//     {
//       feedbackType: 'RECEIVE',
//       teamMate: '홍길동',
//       teamName: '팀 이름',
//       profileImage: {
//         iconName: 'shark',
//         color: '#62BFCA',
//       },
//       recordDate: '2025-01-29',
//       content:
//         '주요 내용을 좀 더 구체적으로 표현하면 이해하기 쉬울 것 같아요. 특히, 두 번째 섹션에서 예시를 추가하면 더 설득력이 높아질 것 같습니다!',
//       keywords: [
//         '명확하게 적기',
//         '해결점 위주',
//         '직설적인 말투',
//         '항상 긍정적인 태도',
//         '설득력',
//       ],
//       heart: true,
//     },
//     {
//       feedbackType: 'SEND',
//       teamMate: '홍길동',
//       teamName: '팀 이름',
//       profileImage: {
//         iconName: 'shark',
//         color: '#62BFCA',
//       },
//       recordDate: '2025-01-29',
//       content:
//         '주요 내용을 좀 더 구체적으로 표현하면 이해하기 쉬울 것 같아요. 특히, 두 번째 섹션에서 예시를 추가하면 더 설득력이 높아질 것 같습니다!',
//       keywords: [
//         '명확하게 적기',
//         '해결점 위주',
//         '직설적인 말투',
//         '항상 긍정적인 태도',
//         '설득력',
//       ],
//       heart: true,
//     },
//     {
//       feedbackType: 'SELF',
//       teamName: '팀 이름',
//       scheduleName: '4주차 리서치 과제',
//       recordDate: '2025.01.29',
//       content:
//         '이번 프로젝트를 통해 팀원들과의 소통이 얼마나 중요한지 다시 한번 느낌. 특히 중간 점검 회의에서 나온 피드백이 큰 도움이 되었고, 최종 결과물의 완성도를 높이는 데 기여했다. 다음에는 일정 관리를 더욱 철저히 하고, 사전 준비를 강화해야겠다고 느껴졌음.',
//     },
//   ];
