import { useCallback, useEffect, useState } from 'react';
import {
  useMainCard,
  useMainCard2,
  useMyTeams,
  useNotification,
} from '../../api/useMainPage';
import Accordion from '../../components/Accordion';
import MainCard2 from '../../components/MainCard2';
import StickyWrapper from '../../components/wrappers/StickyWrapper';
import MainCard, { cardType } from './components/MainCard';
import Notification from './components/Notification';
import Slider from 'react-slick';
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';
import '../../slider.css';
import { filterNotifications } from '../../utility/handleNotification';
import ScheduleAction, {
  ScheduleActionType,
} from '../calendar/components/ScheduleAction';
import TodoAdd from '../calendar/components/TodoAdd';
import { getScheduleTimeDiff } from '../../utility/time';
import { useNavigate } from 'react-router-dom';
import { useTeam } from '../../useTeam';

export default function MainPage() {
  const [banners, setBanners] = useState();
  const [mainCardType, setMainCardType] = useState(cardType.ADD_TEAM);
  const [timeDiff, setTimeDiff] = useState();
  const [isTodoAddOpen, setIsTodoAddOpen] = useState(false);
  const [isScheduleOpen, setIsScheduleOpen] = useState(false);

  const { data: teamsData } = useMyTeams();
  const { teams, selectedTeam, selectTeam, setTeams } = useTeam();
  const { data: recentScheduleData } = useMainCard(selectedTeam);
  const { data: matesData } = useMainCard2(selectedTeam);
  const { data: notificationsData, markAsRead } = useNotification(selectedTeam);

  const navigate = useNavigate();

  // TODO: 로딩 중 혹은 에러 발생 시 처리

  useEffect(() => {
    if (teamsData) {
      setTeams(teamsData);
      if (teamsData && teamsData.length === 0) {
        setMainCardType(cardType.ADD_TEAM);
      }
    }
  }, [teamsData]);

  useEffect(() => {
    if (notificationsData) {
      setBanners(filterNotifications(notificationsData));
    }
  }, [notificationsData]);

  useEffect(() => {
    if (recentScheduleData) {
      const scheduleDifferece = getScheduleTimeDiff(recentScheduleData);
      if (scheduleDifferece <= 0) {
        setMainCardType(cardType.END_SCHEDULE);
      } else {
        setMainCardType(cardType.DEFUALT);
      }
      setTimeDiff(scheduleDifferece);
    } else {
      setMainCardType(cardType.ADD_TEAM);
    }
  }, [recentScheduleData]);

  // 렌더링때마다 새로운 함수 생성 방지
  const getOnMainButtonClick = useCallback((type) => {
    switch (type) {
      case cardType.ADD_TEAM:
        return () => navigate('/teamspace/make');
      case cardType.ADD_SCHEDULE:
        return () => setIsScheduleOpen(!isScheduleOpen);
      case cardType.END_SCHEDULE:
        return () => console.log('피드백 작성하기 화면으로 이동');
      default:
        return () => setIsTodoAddOpen(!isTodoAddOpen);
    }
  }, []);

  useEffect(() => {
    console.log('selectedTeamId: ', selectedTeam);
    console.log('type: ', mainCardType);
  });

  return (
    <div className='flex w-full flex-col'>
      <StickyWrapper className='px-5'>
        {teams && (
          <Accordion
            isMainPage={true}
            selectedTeamId={selectedTeam}
            teamList={teams}
            onTeamClick={selectTeam}
            onClickLastButton={() => navigate('/teamspace/make')}
          />
        )}
      </StickyWrapper>
      {banners && (
        <Slider {...sliderSettings} className='my-4'>
          {banners.notifications.map((banner, index) => (
            <div className='px-[6px]' key={index}>
              <Notification
                notification={banner}
                feedbackRequestNotiIds={banners.feedbackRequestNotiIds}
                onClick={() => console.log('노티 클릭')}
                onClose={markAsRead}
              />
            </div>
          ))}
        </Slider>
      )}
      <div className='h-2' />
      <MainCard
        type={mainCardType}
        recentSchedule={recentScheduleData}
        scheduleDifferece={timeDiff}
        onClickMainButton={getOnMainButtonClick(mainCardType)}
        onClickSubButton={() => setIsScheduleOpen(!isScheduleOpen)}
        onClickChevronButton={() => navigate('/calendar')}
      />
      <div className='h-8' />
      {matesData && <MainCard2 teamMates={matesData} />}
      <div className='h-8' />
      {recentScheduleData && (
        <ScheduleAction
          type={ScheduleActionType.ADD}
          isOpen={isScheduleOpen}
          onSubmit={() => {
            setIsScheduleOpen(!isScheduleOpen);
            // TODO: 일정 조회
          }}
          onClose={() => {
            setIsScheduleOpen(!isScheduleOpen);
          }}
          selectedDateFromParent={new Date()}
          selectedSchedule={recentScheduleData}
        />
      )}
      {recentScheduleData && (
        <TodoAdd
          isOpen={isTodoAddOpen}
          onSubmit={() => {
            setIsTodoAddOpen(!isTodoAddOpen);
            // TODO: 할일 조회
          }}
          onClose={() => {
            setIsTodoAddOpen(!isTodoAddOpen);
          }}
          selectedSchedule={recentScheduleData}
        />
      )}
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
