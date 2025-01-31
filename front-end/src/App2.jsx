import Accordion from './components/Accordion';
import AiButton from './components/buttons/AiButton';
import KeywordButton from './components/buttons/KeywordButton';
import MediumButton from './components/buttons/MediumButton';
import Modal, { ModalType } from './components/modals/Modal';
import ProfileImage from './components/ProfileImage';
import Tag, { TagType } from './components/Tag';
import ProgressBar from './pages/feedback/components/ProgressBar';
import ReportKeywords from './pages/mypage/components/ReportKeywords';
import ReportResults from './pages/mypage/components/ReportResults';
import { hideModal, showModal } from './utility/handleModal';

function App2() {
  return (
    <div className='flex h-dvh w-dvw flex-col flex-wrap items-center justify-center gap-2 bg-gray-600'>
      <Tag type={TagType.TEAM_LEADER} />
      <Tag type={TagType.MY_ROLE} />
      <Tag type={TagType.MEMBER_ROLE}>백현식</Tag>
      <Tag type={TagType.TEAM_NAME}>프론트엔드팀</Tag>
      <Tag type={TagType.KEYWORD}>직설적인 말투</Tag>
      <Tag type={TagType.NUMBER}>1123</Tag>
      <Tag
        type={TagType.TEAM_SCHEDULE}
        children={{ date: '12일 목요일', time: '17:00' }}
      />

      <AiButton isActive={false} onClick={() => {}}>
        적용하기
      </AiButton>
      <AiButton isActive={true} onClick={() => {}}>
        재생성하기
      </AiButton>

      <KeywordButton isActive={true} onClick={() => {}}>
        완곡하게
      </KeywordButton>
      <KeywordButton isActive={false} onClick={() => {}}>
        대안을 제시하는
      </KeywordButton>

      <hr className='h-20' />
      <KeywordButton
        isActive={true}
        onClick={() => {
          showModal(
            <Modal
              type={ModalType.DOUBLE}
              content='정말 로그아웃 하시겠어요?'
              mainButton={
                <MediumButton
                  text='로그아웃'
                  onClick={() => {}}
                  isOutlined={false}
                  disabled={false}
                />
              }
              subButton={
                <MediumButton
                  text='아니요'
                  isOutlined={true}
                  disabled={true}
                  onClick={() => {}}
                />
              }
            />,
          );
        }}
      >
        로그아웃 모달 예시
      </KeywordButton>
      <KeywordButton
        isActive={true}
        onClick={() => {
          showModal(
            <Modal
              type={ModalType.SINGLE}
              content='초대링크 복사 완료!'
              mainButton={
                <MediumButton
                  text='닫기'
                  onClick={() => {
                    hideModal();
                  }}
                  isOutlined={false}
                  disabled={false}
                />
              }
            />,
          );
        }}
      >
        초대링크 모달 예시
      </KeywordButton>
      <KeywordButton
        isActive={true}
        onClick={() => {
          showModal(
            <Modal
              type={ModalType.SINGLE_DELETE}
              content='팀장을 카리나님으로 변경할까요?'
              mainButton={
                <MediumButton
                  text='확인'
                  onClick={() => {
                    hideModal();
                  }}
                  isOutlined={false}
                  disabled={false}
                />
              }
            />,
          );
        }}
      >
        초대링크 모달 예시
      </KeywordButton>
      <KeywordButton
        isActive={true}
        onClick={() =>
          showModal(
            <Modal
              type={ModalType.PROFILE}
              profileImage={
                <div className='size-[62px]'>
                  <ProfileImage iconName='@animals/frog' color='#AFD1DC' />
                </div>
              }
              content='양준호님에게'
              mainButton={
                <MediumButton
                  text='피드백 보내기'
                  onClick={() => {}}
                  isOutlined={false}
                  disabled={false}
                />
              }
              subButton={
                <MediumButton
                  text='피드백 요청하기'
                  onClick={() => {}}
                  isOutlined={true}
                  disabled={false}
                />
              }
            />,
          )
        }
      >
        프로필 모달 예시
      </KeywordButton>
      <KeywordButton
        isActive={true}
        onClick={() => {
          showModal(
            <Modal
              type={ModalType.EXIT}
              content='탈퇴를 진행하기 전에 꼭 확인해주세요'
              mainButton={
                <MediumButton
                  text='탈퇴하기'
                  onClick={() => {}}
                  isOutlined={true}
                  disabled={true}
                />
              }
              // subButton={
              //   <MediumButton
              //     text='아니요'
              //     onClick={() => {}}
              //     isOutlined={true}
              //     disabled={true}
              //   />
              // }
            />,
          );
        }}
      >
        탈퇴 모달 예시
      </KeywordButton>

      <div className='flex h-[852px] w-[393px] flex-col gap-10 bg-gray-900 px-5'>
        <Accordion
          selectedTeamId={1}
          teamList={[
            { id: 1, name: '프론트엔드팀' },
            { id: 2, name: '백엔드팀' },
            { id: 3, name: 'QA팀' },
          ]}
          onTeamClick={() => {}}
          isMainPage={true}
        />
        <ReportKeywords
          reports={[
            { keyword: '직설적인 말투', count: 1123, isPositive: true },
            {
              keyword: '완곡하게 완곡하게 완곡하게 완곡하게 완곡하게',
              count: 123,
              isPositive: false,
            },
            { keyword: '대안을 제시하는', count: 12, isPositive: true },
            { keyword: '비판적인', count: 1, isPositive: false },
          ]}
        />
        <ReportResults
          results={[
            {
              title: '커뮤니케이션',
              goodCount: 23,
              badCount: 65,
            },
            {
              title: '협업 태도',
              goodCount: 35,
              badCount: 5,
            },
            {
              title: '결과물과 업무',
              goodCount: 34,
              badCount: 53,
            },
          ]}
        />
      </div>
      <div className='flex h-[852px] w-[393px] flex-col bg-gray-900 px-5'>
        <Accordion teamList={[]} isMainPage={true} />
      </div>
      <div className='flex h-[852px] w-[393px] flex-col bg-gray-900 px-5'>
        <ProgressBar currentStep={2} totalStep={3} />
        <Accordion
          selectedTeamId={2}
          teamList={[
            { id: 1, name: '프론트엔드팀' },
            { id: 2, name: '백엔드팀' },
            { id: 3, name: 'QA팀' },
          ]}
          isMainPage={false}
        />
      </div>
    </div>
  );
}

export default App2;
