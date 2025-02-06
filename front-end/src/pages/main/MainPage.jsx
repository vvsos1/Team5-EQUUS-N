import { useState } from 'react';
import {
  useMainCard,
  useMainCard2,
  useMyTeams,
  useNotification,
} from '../../api/useMainPage';
import Accordion from '../../components/Accordion';
import MainCard2 from '../../components/MainCard2';
import StickyWrapper from '../../components/wrappers/StickyWrapper';
import MainCard from './components/MainCard';
import Notification from './components/Notification';

export default function MainPage() {
  const [selectedTeamId, setSelectedTeamId] = useState(1);

  const { data: teamsData } = useMyTeams();
  const { data: recentScheduleData } = useMainCard(selectedTeamId);
  const { data: matesData } = useMainCard2(selectedTeamId);
  const { data: notificationsData } = useNotification();

  // TODO: 로딩 중 혹은 에러 발생 시 처리

  return (
    <div className='flex w-full flex-col'>
      <StickyWrapper>
        {teamsData && (
          <Accordion
            isMainPage={false}
            selectedTeamId={selectedTeamId}
            teamList={teamsData}
            onTeamClick={setSelectedTeamId}
          />
        )}
      </StickyWrapper>
      {notificationsData && (
        <div className='flex w-screen -translate-x-5 justify-center overflow-x-auto overflow-y-hidden px-5 py-4'>
          {notificationsData.map((notification, index) => (
            <Notification type='NEW' />
          ))}
        </div>
      )}
      <div className='h-4' />
      {recentScheduleData && <MainCard recentSchedule={recentScheduleData} />}
      <div className='h-8' />
      {matesData && <MainCard2 teamMates={matesData} />}
    </div>
  );
}
