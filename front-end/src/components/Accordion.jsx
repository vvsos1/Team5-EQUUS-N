import TextButton, { TextButtonType } from './buttons/TextButton';
import Icon from './Icon';

/**
 * 아코디언 컴포넌트... 그냥 네비바 같은디..?
 * @param {object} props
 * @param {number} props.selectedTeamId - 선택된 팀 ID
 * @param {array} props.teamList - 팀 목록
 * @param {function} props.onTeamClick - 팀 클릭 시 호출되는 함수
 * @param {boolean} props.isAlarmRead - 알람 읽음 여부
 * @returns
 */
export default function Accordion({
  selectedTeamId,
  teamList,
  onTeamClick,
  isAlarmRead,
}) {
  // TODO: 알람 읽음 여부 API 연동.. (여기서 상태 관리할지, 부모가 넘겨줄지... 일단 부모가 넘겨주는 걸로)

  return (
    <header className='relative flex h-[60px] w-full items-center justify-between px-5'>
      {teamList.length === 0 ?
        <Icon name='logo' />
      : <details className='z-0' onClick={() => onTeamClick(selectedTeamId)}>
          <summary className='header-3 flex list-none items-center gap-0.5 text-white'>
            {teamList.find((team) => team.id === selectedTeamId).name}
            <Icon name='unfoldMore' />
          </summary>
          <div className='rounded-400 absolute top-full flex w-[353px] flex-col divide-y-1 divide-gray-600 bg-gray-800 transition-all duration-300'>
            <div className='fixed inset-0 -z-10 bg-black/60'></div>
            {teamList.map((team) => (
              <TextButton
                key={team.id}
                type={
                  team.id === selectedTeamId ?
                    TextButtonType.CHECK
                  : TextButtonType.DEFAULT
                }
                onClick={() => onTeamClick(team.id)}
              >
                {team.name}
              </TextButton>
            ))}
          </div>
        </details>
      }
      <div className='flex gap-4'>
        {teamList.length > 0 && (
          <button
            onClick={() => {
              console.log('알람 페이지로 이동');
              r.current.showModal();
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
