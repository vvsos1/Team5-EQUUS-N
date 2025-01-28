import Icon from './Icon';
import ProfileImage from './ProfileImage';
import Tag, { TagType } from './Tag';

export default function MemberElement({ member, isLeader }) {
  return (
    <li
      className={`rounded-400 flex h-fit w-full items-center bg-gray-800 px-5 py-4`}
    >
      <div className='h-11 w-11'>
        <ProfileImage
          iconName={`@animals/${member.iconName}`}
          color={member.color}
        />
      </div>
      <div className='w-3' />
      <div className='flex flex-1 flex-col gap-1'>
        <div className='flex items-center gap-3'>
          <p className='subtitle-1 text-gray-100'>{member.name}</p>
          {isLeader ?
            <Tag type={TagType.TEAM_LEADER} />
          : null}
        </div>
        <p className='caption-1 text-gray-300'>{member.email}</p>
      </div>
      {!isLeader ?
        <div className='flex gap-2'>
          <div
            className='flex h-9 w-9 cursor-pointer items-center justify-center rounded-full bg-gray-600 p-1.5'
            onClick={() => {}} // 팀장 권한 부여
          >
            <Icon name={'crown'} color={'var(--color-gray-200)'} />
          </div>
          <div
            className='flex h-9 w-9 cursor-pointer items-center justify-center rounded-full bg-gray-600 p-1.5'
            onClick={() => {}} // 추방
          >
            <Icon name={'logout'} color={'var(--color-gray-200)'} />
          </div>
        </div>
      : null}
    </li>
  );
}

// const memberList = [
//   {
//     name: '한준호',
//     email: 'john.doe@example.com',
//     iconName: 'panda',
//     color: '#62BFCA',
//     isLeader: true,
//   },
//   {
//     name: '박명규',
//     email: 'john.doe@example.com',
//     iconName: 'penguin',
//     color: '#F6D480',
//     isLeader: false,
//   },
//   {
//     name: '임세준',
//     email: 'john.doe@example.com',
//     iconName: 'rooster',
//     color: '#F17858',
//     isLeader: false,
//   },
//   {
//     name: '백현식',
//     email: 'john.doe@example.com',
//     iconName: 'lion',
//     color: '#58AECB',
//     isLeader: false,
//   },
// ];

// {memberList.map((member, index) => (
//     <MemberElement key={index} member={member} isLeader={member.isLeader} />
//   ))}
