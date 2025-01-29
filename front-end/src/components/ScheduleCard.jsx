import Tag from './Tag';
import { TagType } from './Tag';
import Icon from './Icon';
import MediumButton from './buttons/MediumButton';
import { useEffect, useRef, useState } from 'react';
import classNames from 'classnames';

/**
 *
 * @param {string} teamName - 팀 이름
 * @param {object} schedule - 스케줄 정보
 * @param {array} roles - 팀원 역할 정보 배열
 * @param {function} onClickEdit - 수정 클릭 시 호출되는 함수
 * @returns
 */
export default function ScheduleCard({
  teamName,
  schedule,
  roles,
  onClickEdit,
  isFinished = false,
}) {
  const [isOpen, setIsOpen] = useState(false);
  const contentRef = useRef(null);
  const [height, setHeight] = useState(0);

  // 스케줄 컨텐츠 높이 계산
  useEffect(() => {
    if (contentRef.current) {
      setHeight(isOpen ? contentRef.current.scrollHeight : 0);
    }
  }, [isOpen]);

  // 나의 역할 찾기
  const myRole = roles.find((role) => role.memberId === 1)?.task;

  return (
    <div
      className={classNames(
        'rounded-400 flex h-fit w-full flex-col gap-6 overflow-hidden bg-gray-800 transition-all duration-300',
        teamName ? 'p-4' : 'px-4 py-5',
      )}
    >
      <div className='flex'>
        <div className='flex flex-1 flex-col gap-3'>
          {teamName && <Tag type={TagType.TEAM_NAME}>{teamName}</Tag>}
          <div className='flex items-center gap-2'>
            <div className='subtitle-2 text-lime-500'>{schedule.start}</div>
            <hr className='h-4 w-1 bg-lime-500' />
            <p className='subtitle-2 text-gray-100'>{schedule.content}</p>
          </div>
        </div>
        {/* 일정 종료 전에만 수정 버튼 표시 */}
        {!isFinished && (
          <Icon
            name='edit'
            className={classNames(
              'h-max w-max cursor-pointer',
              teamName && 'pt-1',
            )}
            onClick={onClickEdit}
          />
        )}
      </div>
      <hr className='w-full border-gray-500' />
      {/* 나의 역할 */}
      {myRole ?
        <div className='flex flex-col gap-3'>
          <Tag type={TagType.MY_ROLE}></Tag>
          <div className='flex flex-col gap-1'>
            {myRole.map((role, index) => (
              <Role key={index}>{role}</Role>
            ))}
          </div>
        </div>
      : <div className='flex flex-col gap-6'>
          <p className='body-1 text-center text-gray-400'>
            나의 역할이 비어있어요
          </p>
          {/* 일정 종료 전에만 나의 역할 추가 버튼 표시 */}
          {!isFinished && (
            <MediumButton
              text={'나의 역할 추가하기'}
              isOutlined={false}
            ></MediumButton>
          )}
        </div>
      }
      {/* 팀원 역할 */}
      <div
        ref={contentRef}
        className={`flex flex-col gap-6 overflow-hidden transition-all duration-300 ease-in-out ${isOpen ? 'mb-0' : 'mb-[-24px]'}`}
        style={contentRef.current ? { height: `${height}px` } : { height: 0 }}
      >
        {roles.map((memberRole, index) => {
          if (memberRole.memberId === 1) return null;
          return (
            <div key={index} className='flex flex-col gap-3'>
              <Tag type={TagType.MEMBER_ROLE}>{memberRole.name}</Tag>
              {memberRole.task.length > 0 ?
                <div className='flex flex-col gap-1'>
                  {memberRole.task.map((task, index) => (
                    <Role key={index}>{task}</Role>
                  ))}
                </div>
              : <p className='body-1 text-gray-500'>
                  아직 담당 업무를 입력하지 않았어요
                </p>
              }
            </div>
          );
        })}
      </div>
      {/* 팀원 역할 보기 토글 버튼 */}
      <MediumButton
        text={
          <div className='flex items-center gap-2'>
            <Icon
              name={isOpen ? 'chevronUp' : 'chevronDown'}
              color='lime-500'
              className='h-max w-max'
            />
            {isOpen ? '닫기' : '팀원 역할 보기'}
          </div>
        }
        onClick={() => setIsOpen(!isOpen)}
        isOutlined={true}
        disabled={true}
      ></MediumButton>
    </div>
  );
}

function Role({ children }) {
  return <p className='body-1 pl-1 text-gray-100'>{`• ${children}`}</p>;
}

{
  /* <ScheduleCard
        teamName='돈없는 한량들'
        schedule={{ start: '17:00', content: '스케줄 내용' }}
        roles={[
          {
            memberId: 1,
            name: '임세준',
            task: ['데스크 리서치 하기', '설문지 작성하기'],
          },
          {
            memberId: 2,
            name: '백현식',
            task: ['레퍼런스 리서치 하기', '설문지 배포하기'],
          },
          {
            memberId: 3,
            name: '한준호',
            task: [],
          },
          {
            memberId: 4,
            name: '박명규',
            task: ['프레젠테이션 연습하기', '설문지 작성하기'],
          },
        ]}
        isFinished={false}
      /> */
}
