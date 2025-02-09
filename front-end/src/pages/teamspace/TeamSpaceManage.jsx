import { useParams } from 'react-router-dom';
import NavBar2 from '../../components/NavBar2';
import Tag, { TagType } from '../../components/Tag';
import StickyWrapper from '../../components/wrappers/StickyWrapper';
import { useTeam } from '../../api/useTeamspace';
import { useEffect, useState } from 'react';
import Icon from '../../components/Icon';
import MemberElement from './components/MemberElement';
import { showToast } from '../../utility/handleToast';

export default function TeamSpaceManage() {
  const { teamId } = useParams();
  const [iamLeader, setIamLeader] = useState(false);
  const { data: team } = useTeam(teamId);

  useEffect(() => {
    if (team) {
      if (team?.teamResponse?.leader?.id === 52345513) {
        setIamLeader(true);
      }
    }
  }, [team]);

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
      <div className='mt-6 mb-8 flex justify-between'>
        <div className='header-1 flex items-center gap-2 text-gray-100'>
          <h1 className='text-gray-100'>{team?.teamResponse?.name}</h1>
          {iamLeader && (
            <button>
              <Icon name='edit' />
            </button>
          )}
        </div>
        <button
          onClick={() => {
            navigator.clipboard.writeText(
              `${window.location.origin}/teamspace/${teamId}`,
            );
            showToast('초대링크 복사 완료');
          }}
        >
          <Tag type={TagType.TEAM_NAME}>초대링크 복사</Tag>
        </button>
      </div>
      <ul className='flex flex-col gap-4'>
        {team?.members.map((member) => (
          <MemberElement
            key={member.id}
            member={member}
            leaderId={team?.teamResponse?.leader?.id}
            iamLeader={iamLeader}
          />
        ))}
      </ul>
    </div>
  );
}
