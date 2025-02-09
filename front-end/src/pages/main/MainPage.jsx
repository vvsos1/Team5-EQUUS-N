import { useEffect, useState } from 'react';
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
import '../../slider.css';
import { filterNotifications } from '../../utility/handleNotification';
import { useNavigate } from 'react-router-dom';
import { hideModal, showModal } from '../../utility/handleModal';
import Modal, { ModalType } from '../../components/modals/Modal';
import ProfileImage from '../../components/ProfileImage';
import MediumButton from '../../components/buttons/MediumButton';

export default function MainPage() {
  const [selectedTeamId, setSelectedTeamId] = useState(1);
  const [banners, setBanners] = useState();

  const { data: teamsData } = useMyTeams();
  const { data: recentScheduleData } = useMainCard(selectedTeamId);
  const { data: matesData } = useMainCard2(selectedTeamId);
  const { data: notificationsData, markAsRead } =
    useNotification(selectedTeamId);

  const navigate = useNavigate();

  // TODO: 로딩 중 혹은 에러 발생 시 처리

  useEffect(() => {
    if (notificationsData) {
      setBanners(filterNotifications(notificationsData));
    }
  }, [notificationsData]);

  return (
    <div className='flex w-full flex-col'>
      <StickyWrapper className='px-5'>
        {teamsData && (
          <Accordion
            isMainPage={true}
            selectedTeamId={selectedTeamId}
            teamList={teamsData}
            onTeamClick={setSelectedTeamId}
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
      {recentScheduleData && (
        <MainCard recentSchedule={recentScheduleData} className='' />
      )}
      <div className='h-8' />
      {matesData && (
        <MainCard2
          teamMates={matesData}
          onClick={(mate) =>
            showModal(
              <Modal
                type={ModalType.PROFILE}
                profileImage={
                  <div className='size-[62px]'>
                    <ProfileImage
                      iconName={`@animals/${mate.iconName}`}
                      color={mate.color}
                    />
                  </div>
                }
                content={
                  mate.id === 1 ? `${mate.name}(나)` : `${mate.name}님에게`
                }
                mainButton={
                  <MediumButton
                    text={mate.id === 1 ? '회고 작성하기' : '피드백 보내기'}
                    onClick={() => {
                      mate.id === 1 ?
                        navigate(`/feedback/self`)
                      : navigate(`/feedback/send`);
                      hideModal();
                    }}
                    isOutlined={false}
                    disabled={false}
                  />
                }
                subButton={
                  mate.id === 1 ?
                    null
                  : <MediumButton
                      text='피드백 요청하기'
                      onClick={() => {
                        navigate(
                          `/feedback/request?receiverId=${mate.id}&receiverName=${mate.name}`,
                        );

                        hideModal();
                      }}
                      isOutlined={true}
                      disabled={false}
                    />
                }
              />,
            )
          }
        />
      )}
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
