import { http, HttpResponse } from 'msw';
import { teams, schedules, members, notifications } from './mockData';

const BASE_URL = 'https://api.com';

export const handlers = [
  // 팀 목록 조회
  http.get(`${BASE_URL}/api/team/my-teams`, () => {
    return HttpResponse.json(teams);
  }),

  // 메인 카드 조회
  http.get(`${BASE_URL}/api/team/:teamId/schedule`, () => {
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

  // 알람 읽음 처리
  http.post(
    `${BASE_URL}/api/notification/mark-as-read`,
    async ({ request }) => {
      const responseData = await request.json();
      console.log('서버가 잘 받았따리: ', responseData);
      return HttpResponse.json(responseData, { status: 201 });
    },
  ),

  // 피드백 요청
  http.post(
    `${BASE_URL}/api/feedbacks/frequent/request`,
    async ({ request }) => {
      const data = await request.json();
      console.log(data);

      // 2초 딜레이 추가
      await new Promise((resolve) => setTimeout(resolve, 1000));

      return HttpResponse.json(data, { status: 201 });
    },
  ),

  // 회고
  http.post(`${BASE_URL}/api/retrospect`, async ({ request }) => {
    const data = await request.json();
    console.log(data);

    // 2초 딜레이 추가
    await new Promise((resolve) => setTimeout(resolve, 1000));

    return HttpResponse.json(data, { status: 201 });
  }),

  // 피드백 키워드 조회
  http.get(`${BASE_URL}/api/feedbacks/favorite`, () => {
    return HttpResponse.json([
      {
        스타일: [
          '완곡하게',
          '솔직하게',
          '칭찬과 함께',
          '가볍게',
          '간단하게',
          '신중하게',
          '유머러스한',
          '구체적인',
        ],
      },
      {
        내용: [
          '명확한',
          '현실적인',
          '대안을 제시하는',
          '핵심적인',
          '발전적인',
          '이상적인',
          '색다른',
          '논리적인',
        ],
      },
    ]);
  }),
];
