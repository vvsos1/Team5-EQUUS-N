import { useEffect, useRef, useState } from 'react';
import TextButton, { TextButtonType } from './buttons/TextButton';
import Icon from './Icon';

/**
 * 아코디언 컴포넌트... 그냥 네비바 같은디..?
 * @param {object} props
 * @param {boolean} props.isMainPage - 메인 페이지 여부
 * @param {number} props.selectedTeamId - 선택된 팀 ID
 * @param {array} props.teamList - 팀 목록
 * @param {function} props.onTeamClick - 팀 클릭 시 호출되는 함수
 * @returns
 */
export default function Accordion({
  isMainPage,
  selectedTeamId,
  teamList,
  onTeamClick,
}) {
  const [isAlarmRead, setIsAlarmRead] = useState(true);
  const detailsRef = useRef(null);

  useEffect(() => {
    if (isMainPage) {
      // 만약 메인페이지면 알람 api get 요청 후 set
    }
  }, [isMainPage]);

  return (
    <header className='relative flex h-[60px] w-full items-center justify-between px-5'>
      {teamList.length === 0 ?
        <Icon name='logo' />
      : <details ref={detailsRef} className='group z-0'>
          <summary className='header-3 flex list-none items-center gap-0.5 text-white'>
            {teamList.find((team) => team.id === selectedTeamId).name}
            <Icon
              name='unfoldMore'
              className='transition group-open:rotate-180'
            />
          </summary>
          <div className='rounded-400 absolute top-full flex w-[353px] flex-col divide-y-1 divide-gray-600 bg-gray-800 px-5 transition-all duration-300'>
            <div
              className='fixed inset-0 -z-10 bg-black/60'
              onClick={() => (detailsRef.current.open = false)}
            />
            {teamList.map((team) => (
              <TextButton
                key={team.id}
                type={
                  team.id === selectedTeamId ?
                    TextButtonType.CHECK
                  : TextButtonType.DEFAULT
                }
                onClick={() => {
                  onTeamClick(team.id);
                  detailsRef.current.open = false;
                }}
              >
                {team.name}
              </TextButton>
            ))}
            <TextButton type={TextButtonType.PLUS} onClick={() => {}}>
              {isMainPage ? '팀 추가하기' : '전체 일정 보기'}
            </TextButton>
          </div>
        </details>
      }
      <div className='flex gap-4 divide-gray-600'>
        {teamList.length > 0 && (
          <button
            onClick={() => {
              console.log('알람 페이지로 이동');
            }}
          >
            <Icon name={isAlarmRead ? 'bellOn' : 'bellOff'} />
          </button>
        )}
        <button onClick={() => console.log('마이페이지로 이동')}>
          <Icon name='hamburger' />
        </button>
      </div>
    </header>
  );
}
