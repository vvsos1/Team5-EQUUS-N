export const verifySignupResponse = {
  message: 'string',
};

export const signupResponse = {
  id: 9007199254740991,
  email: 'string',
  message: 'string',
};

export const loginResponse = {
  message: 'string',
  userId: 9007199254740991,
  email: 'string',
};

export const teams2 = [
  {
    id: 1,
    name: '소프티어 5조',
  },
  {
    id: 2,
    name: '에쿠스 N',
  },
  {
    id: 3,
    name: '협곡의 전사들',
  },
  {
    id: 4,
    name: '한박백임',
  },
];

export const schedules2 = [
  {
    teamId: 1,
    teamName: '소프티어 5조',
    scheduleInfo: {
      startTime: '2025-02-05T17:00:00.000Z',
      endTime: '2025-02-05T18:00:00.000Z',
      scheduleName: '스케줄 내용1',
    },
    todos: [
      // {
      //   memberId: 1,
      //   name: '임세준',
      //   task: ['데스크 리서치 하기', '설문지 작성하기'],
      // },
      {
        memberId: 2,
        name: '백현식',
        task: ['레퍼런스 리서치 하기', '설문지 배포하기'],
      },
      {
        memberId: 3,
        name: '한준호',
        task: [],
      },
      {
        memberId: 4,
        name: '박명규',
        task: ['프레젠테이션 연습하기', '설문지 작성하기'],
      },
    ],
  },
  {
    teamId: 2,
    teamName: '에쿠스 N',
    scheduleInfo: {
      startTime: '2025-02-04T17:00:00.000Z',
      endTime: '2025-02-04T18:00:00.000Z',
      scheduleName: '스케줄 내용2',
    },
    todos: [
      {
        memberId: 1,
        name: '임세준',
        task: ['데스크 리서치 하기', '설문지 작성하기'],
      },
      {
        memberId: 2,
        name: '백현식',
        task: ['레퍼런스 리서치 하기', '설문지 배포하기'],
      },
      {
        memberId: 3,
        name: '한준호',
        task: [],
      },
      {
        memberId: 4,
        name: '박명규',
        task: ['프레젠테이션 연습하기', '설문지 작성하기'],
      },
    ],
  },
  {
    teamId: 3,
    teamName: '협곡의 전사들',
    scheduleInfo: {
      startTime: '2025-02-07T17:00:00.000Z',
      endTime: '2025-02-08T18:00:00.000Z',
      scheduleName: '스케줄 내용3',
    },
    todos: [
      {
        memberId: 1,
        name: '임세준',
        task: ['데스크 리서치 하기', '설문지 작성하기'],
      },
      {
        memberId: 2,
        name: '백현식',
        task: ['레퍼런스 리서치 하기', '설문지 배포하기'],
      },
      {
        memberId: 3,
        name: '한준호',
        task: [],
      },
      {
        memberId: 4,
        name: '박명규',
        task: ['프레젠테이션 연습하기', '설문지 작성하기'],
      },
    ],
  },
  {
    teamId: 4,
    teamName: '한박백임',
    scheduleInfo: {
      startTime: '2025-02-06T17:00:00.000Z',
      endTime: '2025-02-06T19:00:00.000Z',
      scheduleName: '스케줄 내용4',
    },
    todos: [
      {
        memberId: 1,
        name: '임세준',
        task: ['데스크 리서치 하기', '설문지 작성하기'],
      },
      {
        memberId: 2,
        name: '백현식',
        task: ['레퍼런스 리서치 하기', '설문지 배포하기'],
      },
      {
        memberId: 3,
        name: '한준호',
        task: [],
      },
      {
        memberId: 4,
        name: '박명규',
        task: ['프레젠테이션 연습하기', '설문지 작성하기'],
      },
    ],
  },
];

export const createScheduleResponse = {
  message: 'string',
};

export const updateScheduleResponse = {
  message: 'string',
};

export const feedbackReceivedResponse = {
  page: 1073741824,
  hasNext: true,
  content: [
    {
      feedbackId: 134124,
      isAnonymous: true,
      sender: {
        name: '임세준',
        backgroundColor: '#F6D480',
        image: 'dog_face',
      },
      objectiveFeedbacks: [
        '명확하게 적기',
        '해결점 위주',
        '항상 긍정적인 태도',
        '직설적인 말투',
        '사용자 중심 디자인',
      ],
      subjectiveFeedback:
        '매번 핵심이 되는 포인트를 잘 잡아주시고 논리적으로 설득을 잘 하시는 것 같아요. ',
      teamName: '협곡의전사들',
      liked: true,
      createdAt: '2025-02-07T15:06:25.273Z',
    },
    {
      feedbackId: 41234,
      isAnonymous: false,
      sender: {
        name: '임세준',
        backgroundColor: '#F6D480',
        image: 'dog_face',
      },
      objectiveFeedbacks: [
        '명확하게 적기',
        '항상 긍정적인 태도',
        '직설적인 말투',
        '사용자 중심 디자인',
      ],
      subjectiveFeedback:
        '매번 핵심이 되는 포인트를 잘 잡아주시고 논리적으로 설득을 잘 하시는 것 같아요. ',
      teamName: '협곡의전사들',
      liked: true,
      createdAt: '2025-02-07T15:06:25.273Z',
    },
    {
      feedbackId: 2452435,
      isAnonymous: false,
      sender: {
        name: '임세준',
        backgroundColor: '#F6D480',
        image: 'dog_face',
      },
      objectiveFeedbacks: ['명확하게 적기', '친절한 설명'],
      subjectiveFeedback:
        '매번 핵심이 되는 포인트를 잘 잡아주시고 논리적으로 설득을 잘 하시는 것 같아요. ',
      teamName: '협곡의전사들',
      liked: true,
      createdAt: '2025-02-07T15:06:25.273Z',
    },
    {
      feedbackId: 5461234,
      isAnonymous: true,
      sender: {
        name: '임세준',
        backgroundColor: '#F6D480',
        image: 'dog_face',
      },
      objectiveFeedbacks: ['명확하게 적기'],
      subjectiveFeedback:
        '매번 핵심이 되는 포인트를 잘 잡아주시고 논리적으로 설득을 잘 하시는 것 같아요. ',
      teamName: '협곡의전사들',
      liked: true,
      createdAt: '2025-02-07T15:06:25.273Z',
    },
  ],
};

export const feedbackSentResponse = {
  page: 1073741824,
  hasNext: true,
  content: [
    {
      feedbackId: 1341234,
      isAnonymous: false,
      receiver: {
        name: '백현식',
        backgroundColor: '#F6D480',
        image: 'lion',
      },
      objectiveFeedbacks: ['명확하게 적기', '친절한 설명'],
      subjectiveFeedback:
        '매번 핵심이 되는 포인트를 잘 잡아주시고 논리적으로 설득을 잘 하시는 것 같아요. ',
      teamName: '협곡의전사들',
      liked: true,
      createdAt: '2025-02-07T15:07:05.819Z',
    },
    {
      feedbackId: 256243,
      isAnonymous: true,
      receiver: {
        name: '백현식',
        backgroundColor: '#F6D480',
        image: 'lion',
      },
      objectiveFeedbacks: ['명확하게 적기', '친절한 설명'],
      subjectiveFeedback:
        '매번 핵심이 되는 포인트를 잘 잡아주시고 논리적으로 설득을 잘 하시는 것 같아요. ',
      teamName: '협곡의전사들',
      liked: true,
      createdAt: '2025-02-07T15:07:05.819Z',
    },
    {
      feedbackId: 12341324,
      isAnonymous: true,
      receiver: {
        name: '백현식',
        backgroundColor: '#F6D480',
        image: 'lion',
      },
      objectiveFeedbacks: ['명확하게 적기', '친절한 설명'],
      subjectiveFeedback:
        '매번 핵심이 되는 포인트를 잘 잡아주시고 논리적으로 설득을 잘 하시는 것 같아요. ',
      teamName: '협곡의전사들',
      liked: true,
      createdAt: '2025-02-07T15:07:05.819Z',
    },
    {
      feedbackId: 23452,
      isAnonymous: false,
      receiver: {
        name: '백현식',
        backgroundColor: '#F6D480',
        image: 'lion',
      },
      objectiveFeedbacks: ['명확하게 적기', '친절한 설명'],
      subjectiveFeedback:
        '매번 핵심이 되는 포인트를 잘 잡아주시고 논리적으로 설득을 잘 하시는 것 같아요. ',
      teamName: '협곡의전사들',
      liked: true,
      createdAt: '2025-02-07T15:07:05.819Z',
    },
  ],
};
