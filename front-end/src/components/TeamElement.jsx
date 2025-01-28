import ProfileImage from './ProfileImage';

export default function TeamElement({
  teamName,
  startDate,
  endDate,
  teamMembers,
  isDeleted,
}) {
  const imageGap = 20;
  const zIndexGap = 10;
  const maxShow = 6;
  return (
    <li
      className={`rounded-400 flex h-fit w-full flex-col items-center justify-between gap-4 bg-gray-800 p-5 ${
        isDeleted ? 'opacity-60' : ''
      }`}
      // style={{ width: '353px' }}
    >
      <div className='relative flex w-full justify-between'>
        <p className='subtitle-1 relative flex-1 text-gray-100'>{teamName}</p>
        {teamMembers.map((member, index) => {
          if (index <= maxShow) {
            return (
              <div
                key={index}
                className='absolute top-0'
                style={{
                  right: index === maxShow ? `0px` : `${index * imageGap}px`,
                  zIndex:
                    index === maxShow ?
                      Math.floor(zIndexGap / 2)
                    : index * zIndexGap,
                }}
              >
                {index === maxShow ?
                  <div className='h-8 w-8'>
                    <ProfileImage
                      iconName={'dots'}
                      color={'var(--color-gray-200)'}
                    />
                  </div>
                : <div className='h-8 w-8'>
                    <ProfileImage
                      iconName={`@animals/${member.iconName}`}
                      color={member.color}
                    />
                  </div>
                }
              </div>
            );
          } else {
            return null;
          }
        })}
      </div>
      <div className='caption-1 flex w-full justify-between text-gray-300'>
        <p>
          {startDate} ~ {endDate}
        </p>
        <p>{teamMembers.length}명</p>
      </div>
    </li>
  );
}

// 미리 짜둔 예시 코드
/* <TeamElement
          teamName='팀 이름'
          startDate='2025-01-01'
          endDate='2025-01-01'
          teamMembers={[
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
            // {
            //   name: '임세준',
            //   iconName: 'rooster',
            //   color: '#62BFCA',
            // },
            // {
            //   name: '한준호',
            //   iconName: 'panda',
            //   color: '#90C18A',
            // },
            // {
            //   name: '박명규',
            //   iconName: 'penguin',
            //   color: '#AFD1DC',
            // },
            // {
            //   name: '임세준',
            //   iconName: 'rooster',
            //   color: '#62BFCA',
            // },
            // {
            //   name: '한준호',
            //   iconName: 'panda',
            //   color: '#90C18A',
            // },
          ]}
        /> */
