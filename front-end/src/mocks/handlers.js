import { http, HttpResponse } from 'msw';

// export const handlers = [
//   http.get('/user', () => {
//     return HttpResponse.json({ name: 'John Maverick' })
//   }),
// ]
import { teams, schedules, members } from './mockData';

const BASE_URL = 'https://api.com';

export const handlers = [
  // 팀 목록 조회
  http.get(`${BASE_URL}/api/team/my-teams`, () => {
    return HttpResponse.json(teams);
  }),

  // 메인 카드 조회
  http.get(`${BASE_URL}/recentSchedule/:teamId`, () => {
    return HttpResponse.json(schedules[0]);
  }),

  // 팀 멤버 조회
  http.get(`${BASE_URL}/api/team/:teamId/members`, (req, res, ctx) => {
    return HttpResponse.json(members);
  }),
];
