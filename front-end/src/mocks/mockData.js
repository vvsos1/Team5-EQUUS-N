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
    senderName: 'string',
    teamName: 'string',
  },
  {
    notificationId: 9007199254740991,
    receiverId: 9007199254740991,
    createdAt: '2025-02-06T07:36:22.094Z',
    read: false,
    type: 'feedbackReportCreate',
    teamName: 'string',
    receiverName: '백현식',
  },
  {
    notificationId: 9007199254740991,
    receiverId: 9007199254740991,
    createdAt: '2025-02-06T07:36:22.094Z',
    read: false,
    type: 'frequentFeedbackRequest',
    senderName: '백현식',
    teamId: 9007199254740991,
  },
  {
    notificationId: 9007199254740991,
    receiverId: 9007199254740991,
    createdAt: '2025-02-06T07:36:22.094Z',
    read: false,
    type: 'frequentFeedbackRequest',
    senderName: 'string',
    teamId: 9007199254740991,
  },
  {
    notificationId: 9007199254740991,
    receiverId: 9007199254740991,
    createdAt: '2025-02-06T07:36:22.094Z',
    read: false,
    type: 'frequentFeedbackRequest',
    senderName: 'string',
    teamId: 9007199254740991,
  },
  {
    notificationId: 9007199254740991,
    receiverId: 9007199254740991,
    createdAt: '2025-02-06T07:36:22.094Z',
    read: false,
    type: 'heartReaction',
    senderName: 'string',
    teamName: 'string',
  },
  {
    notificationId: 9007199254740991,
    receiverId: 9007199254740991,
    createdAt: '2025-02-06T07:36:22.094Z',
    read: false,
    type: 'regularFeedbackRequest',
    scheduleName: 'string',
    scheduleId: 9007199254740991,
    teamId: 9007199254740991,
  },
  {
    notificationId: 9007199254740991,
    receiverId: 9007199254740991,
    createdAt: '2025-02-06T07:36:22.094Z',
    read: false,
    type: 'scheduleCreate',
    teamName: 'string',
    scheduleDate: '2025-02-06T07:36:22.094Z',
    teamId: 9007199254740991,
  },
  {
    notificationId: 9007199254740991,
    receiverId: 9007199254740991,
    createdAt: '2025-02-06T07:36:22.094Z',
    read: false,
    type: 'teamLeaderChange',
    teamName: 'string',
    teamId: 9007199254740991,
  },
  {
    notificationId: 9007199254740991,
    receiverId: 9007199254740991,
    createdAt: '2025-02-06T07:36:22.094Z',
    read: false,
    type: 'unreadFeedbackExist',
    senderName: 'string',
    teamName: 'string',
    teamId: 9007199254740991,
  },
];
