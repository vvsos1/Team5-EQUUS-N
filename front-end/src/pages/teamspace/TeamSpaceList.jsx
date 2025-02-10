import { useState } from 'react';
import { useMyTeams } from '../../api/useMainPage';
import NavBar2 from '../../components/NavBar2';
import StickyWrapper from '../../components/wrappers/StickyWrapper';
import { useNavigate } from 'react-router-dom';
import TeamElement from './components/TeamElement';
import { checkIsFinished } from '../../utility/time';

export default function TeamSpaceList() {
  const navigate = useNavigate();
  const [showEndedTeams, setShowEndedTeams] = useState(false);
  const { data: teams, isLoading } = useMyTeams();

  if (isLoading) {
    return <div>Loading...</div>;
  }

  return (
    <div className='flex flex-col'>
      <StickyWrapper>
        <NavBar2
          canPop={true}
          title='팀 스페이스 관리'
          onClickClose={() => {
            navigate(-1);
          }}
        />
      </StickyWrapper>
      <div className='button-2 my-6 flex justify-end gap-2 text-gray-400'>
        <button onClick={() => setShowEndedTeams(false)}>
          <p className={`${showEndedTeams ? '' : 'text-lime-500'}`}>진행중</p>
        </button>
        <p>•</p>
        <button onClick={() => setShowEndedTeams(true)}>
          <p className={`${showEndedTeams ? 'text-lime-500' : ''}`}>종료됨</p>
        </button>
      </div>
      <ul className='flex flex-col gap-4'>
        {teams &&
          teams
            .filter((team) => {
              if (showEndedTeams) {
                return checkIsFinished(team.endDate);
              }
              return !checkIsFinished(team.endDate);
            })
            .map((team) => (
              <button
                onClick={() => navigate(`/teamspace/manage/${team.id}`)}
                key={team.id}
              >
                <TeamElement
                  teamId={team.id}
                  teamName={team.name}
                  startDate={team.startDate}
                  endDate={team.endDate}
                  teamMembers={team.teamMembers}
                  isEnded={showEndedTeams}
                />
              </button>
            ))}
      </ul>
    </div>
  );
}
