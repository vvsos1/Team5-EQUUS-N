import Tag from '../../../components/Tag';
import { TagType } from '../../../components/Tag';
import Icon from '../../../components/Icon';
import MediumButton from '../../../components/buttons/MediumButton';
import { useEffect, useRef, useState } from 'react';

/**
 * 메인 카드 컴포넌트
 * @param {object} props
 * @param {boolean} props.isInTeam - 팀에 속해있는지 여부
 * @param {object} props.recentSchedule - 최신 스케줄
 * @param {function} props.onClickMainButton - 메인 버튼 클릭 이벤트
 * @param {function} props.onClickSubButton - 서브 버튼 클릭 이벤트
 * @param {function} props.onClickChevronButton - 체크론 버튼 클릭 이벤트
 * @returns {ReactElement}
 */
export default function MainCard({
  isInTeam = true,
  recentSchedule,
  onClickMainButton,
  onClickSubButton,
  onClickChevronButton,
}) {
  if (!isInTeam) {
    // 팀에 속하지 않은 경우 무조건 팀 만들어야 함
    return (
      <MainCardFrame>
        <>
          <p className='body-1 mt-11 mb-10 text-center text-gray-300'>
            팀 스페이스가 비어있어요
          </p>
          <MediumButton
            text={'팀 스페이스 생성하기'}
            isOutlined={false}
            onClick={onClickMainButton}
          />
        </>
      </MainCardFrame>
    );
  }

  // 마지막 일정이 끝난지 24시간이 넘은 경우 -> response 자체가 없음
  if (!recentSchedule) {
    return (
      <MainCardFrame>
        <>
          <p className='body-1 mt-11 mb-10 text-center text-gray-300'>
            다음 일정이 비어있어요
          </p>
          <MediumButton
            text={'일정 추가하기'}
            isOutlined={false}
            onClick={onClickMainButton}
          />
          <button onClick={onClickChevronButton}>
            <Icon name='chevronRight' className='absolute top-4 right-4' />
          </button>
        </>
      </MainCardFrame>
    );
  }

  const contentRef = useRef(null);
  const [isOpen, setIsOpen] = useState(false);
  const [height, setHeight] = useState(0);

  const scheduleDifferece = getScheduleDifferece(recentSchedule);

  // 스케줄 컨텐츠 높이 계산
  useEffect(() => {
    if (contentRef.current) {
      setHeight(isOpen ? contentRef.current.scrollHeight : 0);
    }
  }, [isOpen]);

  if (-1 < scheduleDifferece && scheduleDifferece <= 0) {
    console.log(scheduleDifferece);
    return (
      <MainCardFrame>
        <div className='flex flex-col items-center justify-center'>
          <p className='body-1 mt-6 mb-1 text-gray-300'>
            {'일정 종료'}
            <span className='body-4 ml-2 text-lime-200'>{`D+${scheduleDifferece}`}</span>
          </p>
          <h1 className='header-1 mb-7 text-gray-400'>{recentSchedule.name}</h1>
          <MediumButton
            text={'피드백 작성하기'}
            isOutlined={false}
            onClick={onClickMainButton}
          />
          <div className='h-3' />
          <MediumButton
            text={'다음 일정 추가하기'}
            isOutlined={true}
            disabled={true}
            onClick={onClickSubButton}
          />
        </div>
      </MainCardFrame>
    );
  } else {
    console.log(scheduleDifferece);
    return (
      <MainCardFrame>
        <div className='flex flex-col items-center justify-center pt-6'>
          <p className='body-1 text-gray-300'>
            {'다음 일정까지'}
            <span className='body-4 ml-2 text-lime-200'>{`D-${scheduleDifferece}`}</span>
          </p>
          <h1 className='header-1 my-2 text-gray-100'>{recentSchedule.name}</h1>
          <Tag
            type={TagType.TEAM_SCHEDULE}
            children={{ date: '12일 목요일', time: '17:00' }}
          />
        </div>
        <hr className='my-6 w-full border-gray-500' />
        {/* 나의 역할 */}
        {recentSchedule.roles.find((role) => role.memberId === 1) ?
          <div className='flex flex-col gap-3'>
            <Tag type={TagType.MY_ROLE} />
            <div className='flex flex-col gap-1'>
              {recentSchedule.roles
                .find((role) => role.memberId === 1)
                .task.map((role, index) => (
                  <li
                    key={index}
                    className='body-1 pl-2 text-gray-100 last:mb-3'
                  >
                    {role}
                  </li>
                ))}
            </div>
          </div>
        : <div className='mb-3 flex flex-col gap-6'>
            <p className='body-1 text-center text-gray-400'>
              나의 역할이 비어있어요
            </p>
            {/* 일정 종료 전에만 나의 역할 추가 버튼 표시 */}
            <MediumButton text={'나의 역할 추가하기'} isOutlined={false} />
          </div>
        }
        {/* 팀원 역할 */}
        <div
          ref={contentRef}
          className={`flex flex-col overflow-hidden transition-all duration-300 ease-in-out`}
          style={contentRef.current ? { height: `${height}px` } : { height: 0 }}
        >
          {recentSchedule.roles.map((role, index) => {
            if (role.memberId === 1) return null;
            return (
              <div key={index} className='flex flex-col gap-3 first:mt-3'>
                <Tag type={TagType.MEMBER_ROLE}>{role.name}</Tag>
                {role.task.length > 0 ?
                  <div className='flex flex-col gap-1'>
                    {role.task.map((task, index) => (
                      <li
                        key={index}
                        className='body-1 pl-1 text-gray-100 last:mb-6'
                      >
                        {task}
                      </li>
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
                className={isOpen ? 'rotate-180' : null}
                name={'chevronDown'}
              />
              {isOpen ? '닫기' : '팀원 역할 보기'}
            </div>
          }
          onClick={() => setIsOpen(!isOpen)}
          isOutlined={true}
          disabled={true}
        ></MediumButton>
      </MainCardFrame>
    );
  }
}

function MainCardFrame({ children }) {
  return (
    <div className='rounded-400 relative flex flex-col bg-gray-800 p-4'>
      {children}
    </div>
  );
}

function getScheduleDifferece(recentSchedule) {
  const millisecondsDiff =
    new Date(recentSchedule.end) - new Date(new Date().toISOString());

  // 밀리초를 하루(day)로 변환: 1000 * 60 * 60 * 24 = 86400000
  return Math.ceil(millisecondsDiff / 86400000);
}
