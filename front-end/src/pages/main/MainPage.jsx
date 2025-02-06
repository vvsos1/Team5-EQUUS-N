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
import Slider from 'react-slick';
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';

export default function MainPage() {
  const [selectedTeamId, setSelectedTeamId] = useState(1);

  const { data: teamsData } = useMyTeams();
  const { data: recentScheduleData } = useMainCard(selectedTeamId);
  const { data: matesData } = useMainCard2(selectedTeamId);
  const { data: notificationsData } = useNotification();

  // TODO: 로딩 중 혹은 에러 발생 시 처리

  const settings = {
    dots: true,
    arrows: false,
    infinite: true,
    speed: 500,
    slidesToShow: 1,
    slidesToScroll: 1,
    centerMode: true,
    centerPadding: '30px',
  };
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
        <div className='flex w-screen -translate-x-5 py-4'>
          <Slider {...settings} className='w-screen'>
            {notificationsData.map((notification, index) => (
              <div className='w-screen px-5'>
                <Notification type='NEW' key={index} />
              </div>
            ))}
          </Slider>
        </div>
      )}
      <div className='h-4' />
      {recentScheduleData && <MainCard recentSchedule={recentScheduleData} />}
      <div className='h-8' />
      {matesData && <MainCard2 teamMates={matesData} />}
    </div>
  );
}
