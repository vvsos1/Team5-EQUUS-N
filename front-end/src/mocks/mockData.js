export const teams = [
  {
    id: 1,
    name: 'Team A',
  },
  {
    id: 2,
    name: 'Team B',
  },
  {
    id: 3,
    name: 'Team C',
  },
  {
    id: 4,
    name: 'Team D',
  },
];

export const schedules = [
  {
    name: 'schedule 1',
    startTime: '2025-02-06T05:30:39.318Z',
    endTime: '2025-02-06T06:30:39.318Z',
    roles: [
      {
        memberId: 1,
        name: '백현식',
        task: ['task 1', 'task 2', 'task 3'],
      },
      {
        memberId: 2,
        name: '양준호',
        task: ['task 4', 'task 5', 'task 6'],
      },
      {
        memberId: 3,
        name: '김민수',
        task: ['task 7', 'task 8'],
      },
    ],
  },
  {
    name: 'schedule 2',
    startTime: '2025-02-06T05:30:39.318Z',
    endTime: '2025-02-06T19:30:39.318Z',
    roles: [
      {
        memberId: 1,
        task: ['task 1', 'task 2', 'task 3'],
      },
      {
        memberId: 2,
        task: ['task 4', 'task 5', 'task 6'],
      },
      {
        memberId: 3,
        task: ['task 7', 'task 8'],
      },
    ],
  },
  {
    name: 'schedule 3',
    startTime: '2025-02-06T05:30:39.318Z',
    endTime: '2025-02-06T19:30:39.318Z',
    roles: [
      {
        memberId: 1,
        task: ['task 1', 'task 2', 'task 3'],
      },
      {
        memberId: 2,
        task: ['task 4', 'task 5', 'task 6'],
      },
      {
        memberId: 3,
        task: ['task 7', 'task 8'],
      },
    ],
  },
  {
    name: 'schedule 4',
    startTime: '2025-02-06T05:30:39.318Z',
    endTime: '2025-02-06T19:30:39.318Z',
    roles: [
      {
        memberId: 1,
        task: ['task 1', 'task 2', 'task 3'],
      },
      {
        memberId: 2,
        task: ['task 4', 'task 5', 'task 6'],
      },
      {
        memberId: 3,
        task: ['task 7', 'task 8'],
      },
    ],
  },
];

export const members = [
  {
    id: 1,
    name: '백현식',
    iconName: 'panda',
    color: '#90C18A',
  },
  {
    id: 2,
    name: '양준호',
    iconName: 'penguin',
    color: '#AFD1DC',
  },
  {
    id: 3,
    name: '김민수',
    iconName: 'whale',
    color: '#F28796',
  },
  {
    id: 4,
    name: '한준호',
    iconName: 'rooster',
    color: '#62BFCA',
  },
];

export const notifications = [
  {
    notificationId: 9007199254740991,
    receiverId: 9007199254740991,
    createdAt: '2025-02-06T07:36:22.094Z',
    read: false,
    type: 'feedbackReceive',
    senderName: '백현식',
    teamName: '에쿠스N',
  },
  {
    notificationId: 4543252345,
    receiverId: 9007199254740991,
    createdAt: '2025-02-06T07:36:22.094Z',
    read: false,
    type: 'feedbackReportCreate',
    teamName: '소프티어 5조',
    receiverName: '백현식',
  },
  {
    notificationId: 14512341234,
    receiverId: 9007199254740991,
    createdAt: '2025-02-06T07:36:22.094Z',
    read: false,
    type: 'frequentFeedbackRequest',
    senderName: '임세준',
    teamId: 9007199254740991,
  },
  {
    notificationId: 243524352345,
    receiverId: 9007199254740991,
    createdAt: '2025-02-06T07:36:22.094Z',
    read: false,
    type: 'frequentFeedbackRequest',
    senderName: '한준호',
    teamId: 9007199254740991,
  },
  {
    notificationId: 141461324,
    receiverId: 9007199254740991,
    createdAt: '2025-02-06T07:36:22.094Z',
    read: false,
    type: 'frequentFeedbackRequest',
    senderName: '박명규',
    teamId: 9007199254740991,
  },
  {
    notificationId: 132435146,
    receiverId: 9007199254740991,
    createdAt: '2025-02-06T07:36:22.094Z',
    read: false,
    type: 'heartReaction',
    senderName: '양준호',
    teamName: '소프티어 5조',
  },
  {
    notificationId: 125141234312,
    receiverId: 9007199254740991,
    createdAt: '2025-02-06T07:36:22.094Z',
    read: false,
    type: 'regularFeedbackRequest',
    scheduleName: '소프티어 5조 프로젝트 중간발표',
    scheduleId: 9007199254740991,
    teamId: 9007199254740991,
  },
  {
    notificationId: 356245245,
    receiverId: 9007199254740991,
    createdAt: '2025-02-06T07:36:22.094Z',
    read: false,
    type: 'scheduleCreate',
    teamName: '소프티어 5조',
    scheduleDate: '2025-02-06T07:36:22.094Z',
    teamId: 9007199254740991,
  },
  {
    notificationId: 2462634646,
    receiverId: 9007199254740991,
    createdAt: '2025-02-06T07:36:22.094Z',
    read: false,
    type: 'teamLeaderChange',
    teamName: '소프티어 5조',
    teamId: 9007199254740991,
  },
  {
    notificationId: 23457521,
    receiverId: 9007199254740991,
    createdAt: '2025-02-06T07:36:22.094Z',
    read: false,
    type: 'unreadFeedbackExist',
    senderName: '백현식',
    teamName: '에쿠스N',
    teamId: 9007199254740991,
  },
];
