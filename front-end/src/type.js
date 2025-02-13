/**
 * @typedef {Object} Schedule - 일정 정보
 * @property {number} scheduleId - 일정 ID
 * @property {string} scheduleName - 일정 이름
 * @property {string} startTime - 시작 시간
 * @property {string} endTime - 종료 시간
 * @property {number} teamId - 팀 ID
 * @property {string} teamName - 팀 이름
 * @property {number} leaderId - 팀장 ID
 * @property {number} ownerId - 소유자 ID
 * @property {Object[]} scheduleMemberNestedDtoList - 팀원 리스트
 * @property {number} scheduleMemberNestedDtoList.memberId - 팀원 ID
 * @property {string} scheduleMemberNestedDtoList.memberName - 팀원 이름
 * @property {Object[]} scheduleMemberNestedDtoList.todoList - 할 일 리스트
 * @property {string} scheduleMemberNestedDtoList.todoList.todo - 할 일
 */

/**
 * @typedef {Object} Member - 팀원 정보
 * @property {number} id - 팀원 ID
 * @property {string} name - 팀원 이름
 * @property {string} email - 팀원 이메일
 * @property {Object} profileImage - 프로필 이미지 정보
 * @property {string} profileImage.backgroundColor - 프로필 이미지 배경색
 * @property {string} profileImage.image - 프로필 이미지 이름
 */
