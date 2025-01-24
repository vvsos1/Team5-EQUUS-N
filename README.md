# 팀원 소개

<table>
<tr>
<td align="center"><a href="https://github.com/lsj1137"><img src="https://avatars.githubusercontent.com/u/57708892?v=44" width="100px;" alt=""/><br /><sub><b>임세준</b></sub></a><br />
<td align="center"><a href="https://github.com/vvsos1"><img src="https://avatars.githubusercontent.com/u/26290830?v=4" width="100px;" alt=""/><br /><sub><b>박명규</b></sub></a><br />
<td align="center"><a href="https://github.com/GoGradually"><img src="https://avatars.githubusercontent.com/u/62929862?v=4" width="100px;" alt=""/><br /><sub><b>한준호</b></sub></a><br />
<td align="center"><a href="https://github.com/hammsik"><img src="https://avatars.githubusercontent.com/u/116339092?v=4" width="100px;" alt=""/><br /><sub><b>백현식</b></sub></a><br />
</table>

# 프로젝트 목표

## 공통

- 완성: 기능 요구사항 만족

## 프론트엔드

- 모바일 뷰가 아닌 데스크톱 뷰로 서비스 진입하면, `무신사` st로 가기(모바일뷰 유지)

## 백엔드

- 비기능 요구사항 만족
    - CAP

## 그라운드 룰

- 매일 오전 10시: 데일리 스크럼 30분씩 진행하기.
    - 전날 회고 후 한 내용
    - 오늘 할 내용
    - **서로가 하는 일에 대해 최대한 이해하는 태도**로 임하기
        - 상대방 설명에서 모르는 부분은 즉각 물어보기 (ex. EC2가 뭐예요? 왜 필요함?)
        - 대답하는 사람은 자세하게 설명해주기.
- 개발하다가 예상보다 오래걸린 작업이 있으면, 다 같이 그 문제에 대해서 토의해보기.
- 매일 저녁 6시 50분: 데일리 회고 10분씩 진행하기.
    - 오늘 한 내용.
- 매주 월요일 10시
    - 온보딩 회의
- 의견 충돌 발생 시
    - 처음 결정하는 안건에 대해서
        - 전날에 미리 회의 안건으로 올려두기! (그 안건을 받아들이는 사람들을 위한 배려!)
        - 과반수
        - 과반수 동률 → GPT한테 물어보기.
    - 이미 결정된 의견에 대해서, 바꾸고 싶은게 있다면
        - 설득의 시간: 30분
        - 만장 일치
    - 처음 결정하는 안건
        - 기획에서 나온 요구사항 정의서.
- 식사
    - 4인 식사 기본
    - 메뉴 데일리 스크럼에서 번갈아가며 정하기
    - 제외 메뉴
        - 돈까쓰, 굴, 오이
    - 메뉴 못정하면 → 참맛식당.
- 피드백 문화
    - 아니/하~/진짜 금지. 근데, 진짜로 대화 시작 금지
    - 근거 제시하기
        - 긍정할 때, 좋은 이유 설명해주기.
        - 제안할 때, 근거 제시하기.
        - 부정할 때, 꺼림찍한 이유 논리적으로 설명해주기.
- 매주 월요일에 헌법 개정 논의하기!

## 프론트엔드 그라운드 룰

## 백엔드 그라운드 룰

# 브랜치 전략

- GitHub Flow 전략 사용
    - main: main, release 브랜치
    - dev: 프론트/백엔드 공유용
    - feature: JIRA 의 백로그에 따른 기능 브랜치
        - 기능 + 테스트 함께 개발
        - 기능개발 완료 혹은 퇴근 전에 dev 로 pull request 올리기
            - 본인 제외 아무나 1명이 merge 하기
        
    - 커밋 메세지 구조
        ```
        <type>: <commit prefix> <summary>
        
        <BLANK LINE>
        
        <body>
        ```
    - \<commit prefix\>: [FD-1]
    - \<type\>
        - feat : 새로운 기능 추가
        - fix : 버그 수정
        - docs : 문서 관련
        - style : 스타일 변경 (포매팅 수정, 들여쓰기 추가, …)
        - refactor : 코드 리팩토링
        - test : 테스트 관련 코드
        - build : 빌드 관련 파일 수정
        - ci : CI 설정 파일 수정
        - perf : 성능 개선
        - chore : 그 외 자잘한 수정
    - \<summary\>
        - 변경내용 간결하게 한글로 작성
    - \<body\>
        - 변경내용 자세하게 작성



## 기획 디자인 링크

https://www.figma.com/design/16a1adYjBfzzvMkRe3yJc1/Handoff_EQUUS-N?node-id=1-6&t=5jO9HHm3exQykJKP-1
