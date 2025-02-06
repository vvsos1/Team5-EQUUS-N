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
import './components/slider.css';

export default function MainPage() {
  const [selectedTeamId, setSelectedTeamId] = useState(1);

  const { data: teamsData } = useMyTeams();
  const { data: recentScheduleData } = useMainCard(selectedTeamId);
  const { data: matesData } = useMainCard2(selectedTeamId);
  const { data: notificationsData } = useNotification();

  // TODO: 로딩 중 혹은 에러 발생 시 처리

  return (
    <div className='flex w-full flex-col'>
      <StickyWrapper className='px-5'>
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
        <Slider {...sliderSettings} className='my-4'>
          {notificationsData.map((_, index) => (
            <div className='px-[6px]' key={index}>
              <Notification type='NEW' />
            </div>
          ))}
        </Slider>
      )}
      <div className='h-2' />
      {recentScheduleData && (
        <MainCard recentSchedule={recentScheduleData} className='' />
      )}
      <div className='h-8' />
      {matesData && <MainCard2 teamMates={matesData} className='' />}
      <div className='h-8' />
    </div>
  );
}

const sliderSettings = {
  dots: true,
  arrows: false,
  infinite: false,
  speed: 500,
  slidesToShow: 1,
  slidesToScroll: 1,
  centerMode: true,
  centerPadding: '14px',
};
