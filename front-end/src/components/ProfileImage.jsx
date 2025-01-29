import Icon from './Icon';

const iconNames = [
  'Bear',
  'Dog Face',
  'Fish',
  'Fox',
  'Frog',
  'Hamster',
  'Koala',
  'Lady Beetle',
  'Lion',
  'Monkey Face',
  'Mouse Face',
  'Octopus',
  'Orangutan',
  'Panda',
  'Parrot',
  'Penguin',
  'Pig Face',
  'Polar Bear',
  'Rabbit Face',
  'Rooster',
  'Shark',
  'Skunk',
  'Spouting Whale',
  'Swan',
  'Turtle',
  'Whale',
  'Wolf',
];
const bgColors = [
  '#90C18A',
  '#8CA562',
  '#CAE2B9',
  '#A6BD69',
  '#F6D480',
  '#F6BF77',
  '#E4D5BB',

  '#F5B387',
  '#F3A067',
  '#F17858',
  '#F28796',
  '#DE9CB8',
  '#E1B1CC',
  '#F39EB6',

  '#CEB3FB',
  '#949BCC',
  '#7EABD9',
  '#58AECB',
  '#A3C8E9',
  '#AFD1DC',
  '#C7F1ED',

  '#BBE2EA',
  '#A1D8D4',
  '#62BFCA',
  '#B3AD9D',
  '#CEE877',
  '#B3DBF3',
  '#FC5B31',
];

export function ProfileImageWithText({ text, iconName, color, onClick }) {
  return (
    <div
      className='flex cursor-pointer flex-col items-center gap-2'
      onClick={onClick}
    >
      <div className='h-14 w-14'>
        <ProfileImage iconName={iconName} color={color} isButton={true} />
      </div>
      <p className='caption-1 text-white'>{text}</p>
    </div>
  );
}

export default function ProfileImage({ iconName, color, isButton }) {
  // iconName이 없을 때 기본 아이콘 표시
  if (!iconName) {
    return (
      <div className='flex aspect-square items-center justify-center rounded-full bg-gray-700 p-4.5'>
        <Icon name='plusM' />
      </div>
    );
  }

  // dots 아이콘 처리
  if (iconName === 'dots') {
    return (
      <div className='flex aspect-square h-8 w-8 items-center justify-center rounded-full bg-gray-200 p-1'>
        <Icon
          name='dots'
          className={'flex items-center justify-center'}
          color={'var(--color-gray-800)'}
        />
      </div>
    );
  }

  // 동물 아이콘 처리
  if (iconName.includes('@animals')) {
    return (
      <div className='relative h-full w-full overflow-hidden rounded-full'>
        <div
          className={`h-full ${isButton ? 'p-2.5' : 'p-1.5'}`}
          style={{ backgroundColor: color }}
        >
          <Icon name={iconName} />
        </div>
        {isButton && (
          <div className='absolute inset-0 flex items-center justify-center bg-gray-700/80 opacity-0 backdrop-blur-xs transition-opacity hover:opacity-100'>
            <div className='h-6 w-6'>
              <Icon name='send' />
            </div>
          </div>
        )}
      </div>
    );
  }

  // 기타 아이콘 처리
  return (
    <div className='flex aspect-square items-center justify-center rounded-full bg-gray-700 p-2.5'>
      <Icon name={iconName} />
    </div>
  );
}

export function getRandomProfile() {
  const randomIconName =
    iconNames[Math.floor(Math.random() * iconNames.length)];
  const randomColor = bgColors[Math.floor(Math.random() * bgColors.length)];
  return { randomIconName, randomColor };
}

// 미리 짜둔 아이콘 변경 관련 코드
// const [iconName, setIconName] = useState('Whale');
// const [color, setColor] = useState('red');

// const handleChangeProfileImage = () => {
//   const { randomIconName, randomColor } = getRandomProfile();
//   setIconName(randomIconName);
//   setColor(randomColor);
// };

// <ProfileImage iconName={iconName} color={color} />
