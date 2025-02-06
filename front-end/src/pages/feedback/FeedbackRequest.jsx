import NavBar2 from '../../components/NavBar2';
import StickyWrapper from '../../components/wrappers/StickyWrapper';
import TextArea from '../../components/TextArea';
import LargeButton from '../../components/buttons/LargeButton';
import FooterWrapper from '../../components/wrappers/FooterWrapper';
import MainCard from '../main/components/MainCard';

export default function FeedbackRequest() {
  return (
    <div className='flex size-full flex-col'>
      <StickyWrapper>
        <NavBar2
          canPop={true}
          onClickPop={() => {
            console.log('pop click');
          }}
        />
        <h1 className='header-2 text-gray-0 mt-6 whitespace-pre-line'>
          {'백현식님에게 요청할\n피드백을 작성해주세요'}
        </h1>
      </StickyWrapper>
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
      <TextArea />
      <TextArea />
      <FooterWrapper>
        <LargeButton isOutlined={false} text='보내기' />
      </FooterWrapper>
    </div>
  );
}
