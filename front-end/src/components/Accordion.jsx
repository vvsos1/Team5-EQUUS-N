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
    <>
      <header className='flex h-[60px] w-full items-center justify-between px-5'>
        {teamList.length === 0 ?
          <Icon name='logo' />
        : <div className='relative'>
            <button
              popovertarget='popup'
              className='flex items-center gap-0.5'
              onClick={() => onTeamClick(selectedTeamId)}
            >
              <h1 className='header-3 text-white'>
                {teamList.find((team) => team.id === selectedTeamId).name}
              </h1>
              <Icon name='unfoldMore' />
            </button>
            <div
              popover='auto'
              id='popup'
              className='absolute top-full left-0 mt-1 w-max rounded-md bg-gray-800 p-4 opacity-0 transition-all backdrop:bg-gray-900/80 backdrop:backdrop-blur-sm backdrop:backdrop-filter open:opacity-100 starting:open:opacity-0'
            >
              <p className='text-white'>이건 버튼 아래에 자동 정렬되는 팝업!</p>
            </div>
          </div>
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
    </>
  );
}
