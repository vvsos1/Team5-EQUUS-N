import { useNavigate } from 'react-router-dom';
import { calTimePassed } from '../../../utility/time';

export const alarmType = Object.freeze({
  FEEDBACK_RECEIVED: 'feedbackReceive', // 피드백 받음
  HEART_RECEIVED: 'heartReaction', // 보낸 피드백 하트 받음
  FREQUENT_FEEDBACK_REQUESTED: 'frequentFeedbackRequest', // 수시피드백 작성 요청
  REPORT_RECEIVED: 'feedbackReportCreate', // 피드백 리포트 생성됨
  NEED_CHECK_FEEDBACK: 'unreadFeedbackExist', // 확인하지 않은 피드백 있음
  CHANGE_TEAM_LEADER: 'teamLeaderChange', // 팀장 권한 받음
  SCHEDULE_ADDED: 'scheduleCreate', // 일정 추가됨
  REGULAR_FEEDBACK_REQUESTED: 'regularFeedbackRequest', // 정기피드백 작성 요청
});

/**
 * 알림 컴포넌트
 * @param {object} props
 * @param {string} props.type - 알림 타입
 * @param {object} props.data - 알림 데이터
 * @returns {JSX.Element} - 알림 컴포넌트
 */
export default function Alarm({ type, data }) {
  const navigate = useNavigate();
  const { title, content, image, imageAlt, handleFunction } =
    filterDataWithType({ data, type });

  return (
    <button
      className='w-full text-start'
      onClick={() => handleFunction(navigate, data)}
    >
      <div className='flex w-full gap-5 border-b border-b-gray-800 py-4'>
        <div className='flex aspect-square h-10 w-10 items-center justify-center rounded-full bg-gray-800 p-2'>
          <img src={image} alt={imageAlt} width={28} height={28} />
        </div>
        <div className='caption-1 flex flex-col gap-1.5 text-gray-400'>
          <div className='flex flex-col gap-1'>
            <p className='body-2 text-gray-100'>{title}</p>
            <p className='whitespace-pre-line text-gray-100'>{content}</p>
          </div>
          {calTimePassed(data.createdAt)}
        </div>
      </div>
    </button>
  );
}

const filterDataWithType = ({ data, type }) => {
  let title = '';
  let content = '';
  let image = '';
  let imageAlt = '';
  let handleFunction = () => {};

  switch (type) {
    // data.senderName, data.teamName
    case alarmType.FEEDBACK_RECEIVED:
      title = '새로운 피드백이 도착했어요';
      content = `${data.senderName}님(${data.teamName})이 피드백을 보냈어요.`;
      image = '/src/assets/images/mail-received.png';
      imageAlt = 'mail';
      handleFunction = (navigate) => handleFeedbackReceived(navigate);
      break;
    // data.senderName, data.teamName
    case alarmType.HEART_RECEIVED:
      title = '내가 보낸 피드백이 도움됐어요';
      content = `${data.senderName}님(${data.teamName})이 내가 보낸 피드백에 공감을 눌렀어요.`;
      image = '/src/assets/images/heart-green.png';
      imageAlt = 'heart';
      handleFunction = (navigate) => handleFeedbackReceived(navigate);
      break;
    // data.senderName
    case alarmType.FREQUENT_FEEDBACK_REQUESTED:
      title = `${data.senderName} 님이 피드백을 요청했어요`;
      content = `요청받은 내용을 확인하고 피드백을 보내주세요.`;
      image = '/src/assets/images/pray.png';
      imageAlt = 'pray';
      handleFunction = (navigate, data) =>
        handleFreqFeedbackReq(navigate, data);
      break;
    // data.teamName, data.receiverName
    case alarmType.REPORT_RECEIVED:
      title = '피드백 리포트가 도착했어요';
      content = `${data.teamName} 프로젝트 잘 마무리 하셨나요?
      ${data.receiverName} 님이 받은 피드백을 정리했어요.`;
      image = '/src/assets/images/folder.png';
      imageAlt = 'folder';
      handleFunction = (navigate) => handleReportCreate(navigate);
      break;
    // data.senderName, data.teamName
    case alarmType.NEED_CHECK_FEEDBACK:
      title = '확인하지 않은 피드백이 있어요';
      content = `${data.senderName}님(${data.teamName})이 보낸 피드백을 확인해 주세요.`;
      image = '/src/assets/images/check-bg-black.png';
      imageAlt = 'check';
      handleFunction = (navigate) => handleFeedbackReceived(navigate);
      break;
    // data.teamName
    case alarmType.CHANGE_TEAM_LEADER:
      title = `${data.teamName}의 팀장 권한을 받았어요`;
      content = `${data.teamName}의 새로운 팀장이 되었습니다!
      팀을 이끌 준비가 되셨나요?`;
      image = '/src/assets/images/crown.png';
      imageAlt = 'crown';
      handleFunction = (navigate) => handleGoMain(navigate);
      break;
    // data.teamName
    case alarmType.SCHEDULE_ADDED:
      title = `${data.teamName}의 새로운 일정이 추가됐어요`;
      content = `추가된 일정을 확인하고 나의 역할을 추가해 주세요.`;
      image = '/src/assets/images/calendar.png';
      imageAlt = 'calendar';
      handleFunction = (navigate) => handleGoMain(navigate);
      break;
    // data.scheduleName
    case alarmType.REGULAR_FEEDBACK_REQUESTED:
      title = '피드백을 작성해주세요';
      content = `${data.scheduleName} 피드백을 작성해주세요.`;
      image = '/src/assets/images/pencil.png';
      imageAlt = 'write';
      handleFunction = (navigate, data) => handleRegFeedbackReq(navigate, data);
      break;
    default:
      break;
  }

  return { title, content, image, imageAlt, handleFunction };
};

export const handleFeedbackReceived = (navigate) => {
  navigate('/feedback/received');
};

export const handleHeartReaction = (navigate) => {
  navigate('/feedback/sent');
};

export const handleFreqFeedbackReq = (navigate, data) => {
  navigate(`/feedback/send/frequent`, {
    state: {
      isRegular: false,
      teamId: data.teamId, // receiver 정보 추가
    },
  });
};

export const handleReportCreate = (navigate) => {
  navigate('/mypage/report');
};

export const handleGoMain = (navigate) => {
  navigate('/main');
};

export const handleRegFeedbackReq = (navigate, data) => {
  navigate('/feedback/send', {
    state: {
      scheduleId: data.scheduleId,
      isRegular: true,
    },
  });
};
