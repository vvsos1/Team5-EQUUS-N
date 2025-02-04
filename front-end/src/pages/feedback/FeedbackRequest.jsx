import { useReducer, useState } from 'react';
import NavBar2 from '../../components/NavBar2';
import TextArea from '../../components/TextArea';
import { DropdownLarge, DropdownSmall } from '../../components/Dropdown';
import Notification, { notiType } from '../main/components/notification';
import MainCard from '../main/components/MainCard';

export default function FeedbackRequest() {
  const [triggerText, setTriggerText] = useState('1/24');
  return (
    <div className='flex size-full flex-col'>
      <NavBar2
        canPop={true}
        onClickPop={() => {
          console.log('pop click');
        }}
      />
      <MainCard isInTeam={false} />
      <MainCard />
      <MainCard
        recentSchedule={{
          name: '동해물과',
          start: '2022-12-12T17:00:00',
          end: '2025-02-03T18:41:00',
          roles: [
            {
              memberId: 1,
              task: ['똥싸기', '씻기', '화장실 가기'],
              name: '백현식',
            },
            {
              memberId: 2,
              task: ['밥먹기', '숨쉬기', '공부하기'],
              name: '양준호',
            },
            { memberId: 3, task: ['게임하기', '피드백하기'], name: '김민수' },
          ],
        }}
      />
      <MainCard
        recentSchedule={{
          name: '백두산이',
          start: '2022-12-12T17:00:00',
          end: '2025-02-09T18:00:00',
          roles: [
            {
              memberId: 1,
              task: ['똥싸기', '씻기', '화장실 가기'],
              name: '백현식',
            },
            {
              memberId: 2,
              task: ['밥먹기', '숨쉬기', '공부하기'],
              name: '양준호',
            },
            { memberId: 3, task: ['게임하기', '피드백하기'], name: '김민수' },
          ],
        }}
      />
      <MainCard
        recentSchedule={{
          name: '마르고 닳도록',
          start: '2022-12-12T17:00:00',
          end: '2025-02-06T18:00:00',
          roles: [
            {
              memberId: 3,
              task: ['똥싸기', '씻기', '화장실 가기'],
              name: '백현식',
            },
            {
              memberId: 2,
              task: ['밥먹기', '숨쉬기', '공부하기'],
              name: '양준호',
            },
            { memberId: 3, task: ['게임하기', '피드백하기'], name: '김민수' },
          ],
        }}
      />

      {/* <DropdownSmall
        triggerText={'전체 보기'}
        setTriggerText={() => {}}
        items={['프론트엔드', '백엔드', '데이터', '디자인', '기획']}
      />
      <DropdownLarge
        triggerText={'프론트엔드'}
        setTriggerText={() => {}}
        isFromTime={false}
        items={['프론트엔드', '백엔드', '데이터', '디자인', '기획']}
      />
      <div className='flex w-full gap-4'>
        <DropdownLarge
          triggerText={triggerText}
          setTriggerText={setTriggerText}
          isFromTime={true}
          isTransparent={true}
          itemsComponent={<Notification type={notiType.UNCONFIRM} />}
        />
        <DropdownLarge
          triggerText={triggerText}
          setTriggerText={setTriggerText}
          isFromTime={false}
          isTransparent={true}
          items={['1/25', '1/26', '1/27', '1/28', '1/29']}
        />
      </div> */}
      <h1 className='header-2 text-gray-0 my-6 whitespace-pre-line'>
        {'백현식님에게 요청할\n피드백을 작성해주세요'}
      </h1>
      <TextArea generatedByGpt={true} />
      <TextArea isWithGpt={false} />
      <TextArea isWithGpt={true} canToggleAnonymous={true} />
    </div>
  );
}
