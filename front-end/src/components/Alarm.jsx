import { calTimePassed } from '../utility/time';

export const alarmType = Object.freeze({
  FEEDBACK_RECEIVED: 'FEEDBACK_RECEIVED', // 피드백 받음
  HEART_RECEIVED: 'HEART_RECEIVED', // 보낸 피드백 하트 받음
  FEEDBACK_REQUESTED: 'FEEDBACK_REQUESTED', // 피드백 작성 요청
  REPORT_RECEIVED: 'REPORT_RECEIVED', // 피드백 리포트 생성됨
  NEED_CHECK_FEEDBACK: 'NEED_CHECK_FEEDBACK', // 확인하지 않은 피드백 있음
  CHANGE_TEAM_LEADER: 'CHANGE_TEAM_LEADER', // 팀장 권한 받음
  SCHEDULE_ADDED: 'SCHEDULE_ADDED', // 일정 추가됨
});

/**
 * 알림 컴포넌트
 * @param {object} props
 * @param {string} props.type - 알림 타입
 * @param {object} props.data - 알림 데이터
 * @returns {JSX.Element} - 알림 컴포넌트
 */
export default function Alarm({ type, data }) {
  let title = '';
  let content = '';
  let image = '';
  let imageAlt = '';

  switch (type) {
    // data.sender, data.teamName
    case alarmType.FEEDBACK_RECEIVED:
      title = '새로운 피드백이 도착했어요';
      content = `${data.sender}님(${data.teamName})이 피드백을 보냈어요.`;
      image = 'src/assets/images/mail-received.png';
      imageAlt = 'mail';
      break;
    // data.receiver, data.teamName
    case alarmType.HEART_RECEIVED:
      title = '내가 보낸 피드백이 도움됐어요';
      content = `${data.receiver}님(${data.teamName})이 내가 보낸 피드백에 공감을 눌렀어요.`;
      image = 'src/assets/images/heart-green.png';
      imageAlt = 'heart';
      break;
    // data.schedule
    case alarmType.FEEDBACK_REQUESTED:
      title = '피드백을 작성해주세요';
      content = `${data.schedule} 피드백을 작성해주세요.`;
      image = 'src/assets/images/pencil.png';
      imageAlt = 'write';
      break;
    // data.teamName, data.receiver
    case alarmType.REPORT_RECEIVED:
      title = '피드백 리포트가 도착했어요';
      content = `${data.teamName} 프로젝트 잘 마무리 하셨나요?
      ${data.receiver} 님이 받은 피드백을 정리했어요.`;
      image = 'src/assets/images/folder.png';
      imageAlt = 'folder';
      break;
    // data.sender, data.teamName
    case alarmType.NEED_CHECK_FEEDBACK:
      title = '확인하지 않은 피드백이 있어요';
      content = `${data.sender}님(${data.teamName})이 보낸 피드백을 확인해 주세요.`;
      image = 'src/assets/images/check-bg-black.png';
      imageAlt = 'check';
      break;
    // data.teamName
    case alarmType.CHANGE_TEAM_LEADER:
      title = `${data.teamName}의 팀장 권한을 받았어요`;
      content = `${data.teamName}의 새로운 팀장이 되었습니다!
      팀을 이끌 준비가 되셨나요?`;
      image = 'src/assets/images/crown.png';
      imageAlt = 'crown';
      break;
    // data.teamName
    case alarmType.SCHEDULE_ADDED:
      title = `${data.teamName}의 새로운 일정이 추가됐어요`;
      content = `추가된 일정을 확인하고 나의 역할을 추가해 주세요.`;
      image = 'src/assets/images/calendar.png';
      imageAlt = 'calendar';
      break;
    default:
      break;
  }

  return (
    <div className='w-full px-4'>
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
    </div>
  );
}

// const data = [
//   {
//     type: alarmType.FEEDBACK_RECEIVED,
//     sender: '김철수',
//     teamName: '팀 이름',
//     createdAt: Date.now() - 1000 * 60 * 60 * 24 * 3,
//   },
//   {
//     type: alarmType.HEART_RECEIVED,
//     receiver: '김철수',
//     teamName: '팀 이름',
//     createdAt: Date.now() - 1000 * 60 * 60 * 24 * 2,
//   },
//   {
//     type: alarmType.FEEDBACK_REQUESTED,
//     schedule: '4주차 리서치 과제',
//     createdAt: Date.now() - 1000 * 60 * 60 * 24 * 1,
//   },
//   {
//     type: alarmType.REPORT_RECEIVED,
//     teamName: '소프티어 5조',
//     receiver: '김철수',
//     createdAt: Date.now() - 1000 * 60 * 60 * 24 * 0,
//   },
//   {
//     type: alarmType.NEED_CHECK_FEEDBACK,
//     sender: '김철수',
//     teamName: '소프티어 5조',
//     createdAt: Date.now() - 1000 * 60 * 60 * 24 * 0,
//   },
//   {
//     type: alarmType.CHANGE_TEAM_LEADER,
//     teamName: '소프티어 5조',
//     createdAt: Date.now() - 1000 * 60 * 60 * 24 * 0,
//   },
//   {
//     type: alarmType.SCHEDULE_ADDED,
//     teamName: '소프티어 5조',
//     createdAt: Date.now() - 1000 * 60 * 60 * 24 * 0,
//   },
// ];

// {data.map((item, index) => (
//   <Alarm key={index} type={item.type} data={item} />
// ))}
