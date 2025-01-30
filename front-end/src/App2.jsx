import Accordion from './components/Accordion';
import AiButton from './components/buttons/AiButton';
import KeywordButton from './components/buttons/KeywordButton';
import TextButton, { TextButtonType } from './components/buttons/TextButton';
import Icon from './components/Icon';
import Tag, { TagType } from './components/Tag';

function App2() {
  return (
    <div className='flex h-dvh w-dvw flex-col items-center justify-center gap-2 bg-gray-600'>
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

      <article className='flex w-60 flex-col bg-gray-800'>
        <TextButton type={TextButtonType.DEFAULT} onClick={() => {}}>
          소프티어 5조
        </TextButton>
        <TextButton type={TextButtonType.PLUS} onClick={() => {}}>
          소프티어 5조
        </TextButton>
        <TextButton type={TextButtonType.CHECK} onClick={() => {}}>
          소프티어 5조
        </TextButton>
      </article>

      <KeywordButton isActive={true} onClick={() => {}}>
        완곡하게
      </KeywordButton>
      <KeywordButton isActive={false} onClick={() => {}}>
        대안을 제시하는
      </KeywordButton>

      <div className='flex w-[393px] flex-col divide-y-1 bg-gray-900'>
        <Accordion
          selectedTeamId={1}
          teamList={[
            { id: 1, name: '프론트엔드팀' },
            { id: 2, name: '백엔드팀' },
            { id: 3, name: 'QA팀' },
          ]}
          onTeamClick={() => {}}
          isAlarmRead={true}
        />
        <Accordion
          selectedTeamId={1}
          teamList={[
            { id: 1, name: '프론트엔드팀' },
            { id: 2, name: '백엔드팀' },
            { id: 3, name: 'QA팀' },
          ]}
          onTeamClick={() => {}}
          isAlarmRead={false}
        />
        <Accordion
          selectedTeamId={1}
          teamList={[]}
          onTeamClick={() => {}}
          isAlarmRead={true}
        />
      </div>
    </div>
  );
}

export default App2;
