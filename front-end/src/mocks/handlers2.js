import { http, HttpResponse } from 'msw';

// export const handlers = [
//   http.get('/user', () => {
//     return HttpResponse.json({ name: 'John Maverick' })
//   }),
// ]
import {
  verifySignupResponse,
  signupResponse,
  loginResponse,
  teams2,
  createScheduleResponse,
  updateScheduleResponse,
  schedules2,
} from './mockData2';

const BASE_URL = 'https://api.com';

export const handlers2 = [
  // 인증
  http.get(`${BASE_URL}/api/auth/send-signup-verification-email`, () => {
    return HttpResponse.json(verifySignupResponse);
  }),

  // 회원가입
  http.post(`${BASE_URL}/api/auth/signup`, () => {
    return HttpResponse.json(signupResponse);
  }),

  // 로그인
  http.post(`${BASE_URL}/api/auth/login`, async ({ request }) => {
    const { email, password } = await request.json();
    console.log(email, password);

    // 로그인 성공 시 응답 형식 지정
    if (email === '1@n.com' && password === 'qpqp1010!') {
      return HttpResponse.json(loginResponse, { status: 201 });
    }

    // 로그인 실패 시 응답 형식 지정
    return HttpResponse.json(
      {
        message: '로그인 실패: 잘못된 자격 증명',
      },
      { status: 401 },
    );
  }),

  // 팀 목록 조회
  http.get(`${BASE_URL}/api/team/my-teams2`, () => {
    return HttpResponse.json(teams2);
  }),

  // 일정 조회
  http.get(`${BASE_URL}/api/schedules`, () => {
    return HttpResponse.json(schedules2);
  }),

  // 팀 일정 조회
  http.get(`${BASE_URL}/api/team/:teamId/schedules`, () => {
    return HttpResponse.json(schedules);
  }),

  // 일정 생성
  http.post(`${BASE_URL}/api/team/:teamId/schedule/create`, () => {
    return HttpResponse.json(createScheduleResponse);
  }),

  // 일정 수정
  http.put(`${BASE_URL}/api/team/:teamId/schedule/:scheduleId`, () => {
    return HttpResponse.json(updateScheduleResponse);
  }),

  // 일정 삭제
  // http.delete(`${BASE_URL}/api/team/:teamId/schedule/:scheduleId`, () => {
  //   return HttpResponse.json(deleteScheduleResponse);
  // }),
];
