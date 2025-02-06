import Accordion from '../../components/Accordion';
import MainCard2 from '../../components/MainCard2';
import StickyWrapper from '../../components/wrappers/StickyWrapper';
import MainCard from './components/MainCard';

export default function MainPage() {
  return (
    <div className='flex size-full flex-col'>
      <StickyWrapper>
        <Accordion
          isMainPage={false}
          selectedTeamId={1}
          teamList={[]}
          onTeamClick={() => {}}
        />
      </StickyWrapper>
      <div className='h-4' />
      <MainCard
        recentSchedule={{
          name: '동해물과',
          start: '2022-02-05T00:00:00',
          end: '2025-02-05T00:23:00',
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
      <div className='h-8' />
      <MainCard2
        teamMates={[
          {
            name: '한준호',
            iconName: 'panda',
            color: '#90C18A',
          },
          {
            name: '박명규',
            iconName: 'penguin',
            color: '#AFD1DC',
          },
          {
            name: '백현식',
            iconName: 'whale',
            color: '#F28796',
          },
          {
            name: '임세준',
            iconName: 'rooster',
            color: '#62BFCA',
          },
        ]}
      />
    </div>
  );
}
