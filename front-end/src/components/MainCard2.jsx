import MediumButton from './buttons/MediumButton';
import { ProfileImageWithText } from './ProfileImage';

// teamMates: [{name: string, iconName: string, color: string}]
export default function MainCard2({ teamMates }) {
  return (
    <div className='w-fit h-fit bg-gray-800 rounded-400 p-4'>
      <p className='pl-1 text-gray-100'>피드백 주고받기</p>
      <div className='grid grid-cols-4 my-5 gap-y-4 gap-x-4 mx-2'>
        {teamMates.map((mate, index) => {
          return (
            <ProfileImageWithText
              key={index}
              text={mate.name}
              iconName={mate.iconName}
              color={mate.color}
            />
          );
        })}
        {teamMates.length < 4 && <ProfileImageWithText text='팀원초대' onClick={() => {}} />}
      </div>
      <MediumButton text='피드백 보관함' onClick={() => {}} />
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
