import Icon from './components/Icon';
import Tag, { TagType } from './components/tag';

function App2() {
  return (
    <div className='flex h-dvh w-dvw flex-col items-center justify-center gap-2 bg-gray-800'>
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
    </div>
  );
}

export default App2;
