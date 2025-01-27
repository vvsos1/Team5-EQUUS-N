import Icon from './Icon';
import ProfileImage from './ProfileImage';

export default function MemberElement({ member, isLeader }) {
  return (
    <li
      className={`flex w-full h-fit items-center bg-gray-800 py-4 px-5 rounded-400`}
      style={{ width: '353px' }}
    >
      <div className=' h-11 w-11'>
        <ProfileImage iconName={`@animals/${member.iconName}`} color={member.color} />
      </div>
      <div className='w-3' />
      <div className='flex flex-1 flex-col gap-1'>
        <div className='flex gap-3 items-center'>
          <p className='subtitle-1 text-gray-100'>{member.name}</p>
          {isLeader ? <p className='caption-1 text-gray-300'>팀장 태그</p> : null}
        </div>
        <p className='caption-1 text-gray-300'>{member.email}</p>
      </div>
      {!isLeader ? (
        <div className='flex gap-2 '>
          <div
            className='w-9 h-9 bg-gray-600 rounded-full flex items-center justify-center p-1.5 cursor-pointer'
            onClick={() => {}} // 팀장 권한 부여
          >
            <Icon name={'crown'} color={'var(--color-gray-200)'} />
          </div>
          <div
            className='w-9 h-9 bg-gray-600 rounded-full flex items-center justify-center p-1.5 cursor-pointer'
            onClick={() => {}} // 추방
          >
            <Icon name={'logout'} color={'var(--color-gray-200)'} />
          </div>
        </div>
      ) : null}
    </li>
  );
}

// const memberList = [
//     {
//       name: 'John Doe',
//       email: 'john.doe@example.com',
//       iconName: 'Panda',
//       color: '#BBE2EA',
//       isLeader: true,
//     },
//     {
//       name: 'John Doe',
//       email: 'john.doe@example.com',
//       iconName: 'Turtle',
//       color: '#BBE2EA',
//       isLeader: false,
//     },
//     {
//       name: 'John Doe',
//       email: 'john.doe@example.com',
//       iconName: 'Frog',
//       color: '#BBE2EA',
//       isLeader: false,
//     },
//     {
//       name: 'John Doe',
//       email: 'john.doe@example.com',
//       iconName: 'Shark',
//       color: '#BBE2EA',
//       isLeader: false,
//     },
//   ];

// {memberList.map((member, index) => (
//     <MemberElement key={index} member={member} isLeader={member.isLeader} />
//   ))}
