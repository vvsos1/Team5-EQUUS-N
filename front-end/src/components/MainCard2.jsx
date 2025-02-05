import MediumButton from './buttons/MediumButton';
import { ProfileImageWithText } from './ProfileImage';

/**
 * 메인 카드 2 컴포넌트
 * @param {object} props
 * @param {object[]} props.teamMates - 팀원 목록
 * @param {string} props.teamMates[].name - 팀원 이름
 * @param {string} props.teamMates[].iconName - 팀원 아이콘 이름
 * @param {string} props.teamMates[].color - 팀원 아이콘 색상
 * @returns {JSX.Element} - 메인 카드 2 컴포넌트
 */
export default function MainCard2({ teamMates }) {
  return (
    <div className='rounded-400 h-fit w-full bg-gray-800 p-4'>
      <p className='pl-1 text-gray-100'>피드백 주고받기</p>
      <div className='mx-2 my-5 grid grid-cols-4 gap-x-4 gap-y-4'>
        {teamMates.map((mate, index) => {
          return (
            <ProfileImageWithText
              key={index}
              text={mate.name}
              iconName={`@animals/${mate.iconName}`}
              color={mate.color}
              onClick={() => alert(`${mate.name} 클릭`)}
            />
          );
        })}
        {teamMates.length < 4 && (
          <ProfileImageWithText text='팀원초대' onClick={() => {}} />
        )}
      </div>
      <MediumButton text='피드백 보관함' onClick={() => {}} disabled={true} />
    </div>
  );
}

// const teamMates = [
//   {
//     name: '한준호',
//     iconName: 'Panda',
//     color: '#90C18A',
//   },
//   {
//     name: '박명규',
//     iconName: 'Penguin',
//     color: '#AFD1DC',
//   },
//   {
//     name: '백현식',
//     iconName: 'Whale',
//     color: '#F28796',
//   },
//   {
//     name: '임세준',
//     iconName: 'Rooster',
//     color: '#62BFCA',
//   },
// ];
