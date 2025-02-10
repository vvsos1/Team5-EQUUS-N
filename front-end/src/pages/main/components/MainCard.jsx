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
 * @param {number} props.scheduleDifferece - D-day
 * @param {function} props.onClickMainButton - 메인 버튼 클릭 이벤트
 * @param {function} props.onClickSubButton - 서브 버튼 클릭 이벤트
 * @param {function} props.onClickChevronButton - 체크론 버튼 클릭 이벤트
 * @returns {ReactElement}
 */
export default function MainCard({
  isInTeam,
  recentSchedule,
  scheduleDifferece,
  onClickMainButton,
  onClickSubButton,
  onClickChevronButton,
}) {
  const contentRef = useRef(null);
  const [isOpen, setIsOpen] = useState(false);
  const [height, setHeight] = useState(0);

  // 스케줄 컨텐츠 높이 계산
  useEffect(() => {
    if (contentRef.current) {
      setHeight(isOpen ? contentRef.current.scrollHeight : 0);
    }
  }, [isOpen]);

  if (!isInTeam) {
    // 팀에 속하지 않은 경우 무조건 팀 만들어야 함
    return (
      <MainCardFrame>
        <p className='body-1 mt-11 mb-10 text-center text-gray-300'>
          팀 스페이스가 비어있어요
        </p>
        <MediumButton
          text={'팀 스페이스 생성하기'}
          isOutlined={false}
          onClick={onClickMainButton}
        />
      </MainCardFrame>
    );
  }

  // 마지막 일정이 끝난지 24시간이 넘은 경우 -> response 자체가 없음
  if (!scheduleDifferece) {
    return (
      <MainCardFrame>
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
      </MainCardFrame>
    );
  }

  // 마지막 일정이 끝난 후 24시간이 안된 경우: response 있음 && timeDiff가 0보다 작거나 같음
  if (scheduleDifferece <= 0) {
    return (
      <MainCardFrame>
        <div className='flex flex-col items-center justify-center'>
          <p className='body-1 mt-6 mb-1 text-gray-300'>일정 종료</p>
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
    // 다음 일정
    return (
      <MainCardFrame>
        {renderTitle(recentSchedule, scheduleDifferece)}
        <hr className='my-6 w-full border-gray-500' />
        {renderMyRole(recentSchedule, onClickMainButton)}
        {renderTeamRole(recentSchedule, contentRef, height)}
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
        />
      </MainCardFrame>
    );
  }
}

function MainCardFrame({ children }) {
  return (
    <div className={`rounded-400 relative mx-5 flex flex-col bg-gray-800 p-4`}>
      {children}
    </div>
  );
}

function renderTitle(recentSchedule, scheduleDifferece) {
  return (
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
  );
}

function renderMyRole(recentSchedule, onButtonClick) {
  return recentSchedule.roles.find((role) => role.memberId === 1) ?
      <div className='flex flex-col gap-3'>
        <Tag type={TagType.MY_ROLE} />
        <ul className='flex list-disc flex-col gap-1 pl-6'>
          {recentSchedule.roles
            .find((role) => role.memberId === 1)
            .task.map((role, index) => (
              <li key={index} className='body-1 pl-1 text-gray-100 last:mb-3'>
                {role}
              </li>
            ))}
        </ul>
      </div>
    : <div className='mb-3 flex flex-col gap-6'>
        <p className='body-1 text-center text-gray-400'>
          나의 역할이 비어있어요
        </p>
        <MediumButton
          text={'나의 역할 추가하기'}
          isOutlined={false}
          onClick={onButtonClick}
        />
      </div>;
}

function renderTeamRole(recentSchedule, contentRef, currentHeight) {
  return (
    <ul
      ref={contentRef}
      className={`flex flex-col overflow-hidden transition-all duration-300 ease-in-out`}
      style={
        contentRef.current ? { height: `${currentHeight}px` } : { height: 0 }
      }
    >
      {recentSchedule.roles.map((role, index) => {
        if (role.memberId === 1) return null;
        return (
          <li key={index} className='flex flex-col gap-3 first:mt-3'>
            <Tag type={TagType.MEMBER_ROLE}>{role.name}</Tag>
            {role.task.length > 0 ?
              <ul className='flex list-disc flex-col gap-1 pl-6'>
                {role.task.map((task, index) => (
                  <li
                    key={index}
                    className='body-1 pl-1 text-gray-100 last:mb-6'
                  >
                    {task}
                  </li>
                ))}
              </ul>
            : <p className='body-1 text-gray-500'>
                아직 담당 업무를 입력하지 않았어요
              </p>
            }
          </li>
        );
      })}
    </ul>
  );
}
