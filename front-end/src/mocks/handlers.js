import { http, HttpResponse } from 'msw';
import { teams, schedules, members, notifications } from './mockData';

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
  http.get(`${BASE_URL}/api/team/:teamId/members`, () => {
    return HttpResponse.json(members);
  }),

  // 알람 조회
  http.get(`${BASE_URL}/api/notification`, () => {
    return HttpResponse.json(notifications);
  }),
];
