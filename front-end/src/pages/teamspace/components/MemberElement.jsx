import { useKickMember, useSetLeader } from '../../../api/useTeamspace';
import MediumButton from '../../../components/buttons/MediumButton';
import Icon from '../../../components/Icon';
import Modal from '../../../components/modals/Modal';
import ProfileImage from '../../../components/ProfileImage';
import Tag, { TagType } from '../../../components/Tag';
import { hideModal, showModal } from '../../../utility/handleModal';

/**
 * 팀원 요소 컴포넌트
 * @param {object} props
 * @param {object} props.member - 팀원 정보
 * @param {string} props.member.name - 팀원 이름
 * @param {string} props.member.iconName - 팀원 아이콘 이름
 * @param {string} props.member.color - 팀원 아이콘 색상
 * @param {boolean} props.isLeader - 팀장 여부
 * @returns {JSX.Element} - 팀원 요소 컴포넌트
 */
export default function MemberElement({ teamId, member, leaderId, iamLeader }) {
  const { mutate: setLeader } = useSetLeader(teamId);
  const { mutate: kickMember } = useKickMember(teamId);

  const changeLeaderModal = (
    <Modal
      type='SINGLE'
      content={`팀장을 ${member.name} 님으로 변경할까요?`}
      mainButton={
        <MediumButton
          text='확인'
          isOutlined={false}
          onClick={() => {
            setLeader(member.id);
            hideModal();
          }}
        />
      }
    />
  );

  const kickMemberModal = (
    <Modal
      type='SINGLE'
      content={`${member.name} 님을 팀에서 제외할까요?`}
      mainButton={
        <MediumButton
          text='확인'
          isOutlined={false}
          onClick={() => {
            kickMember(member.id);
            hideModal();
          }}
        />
      }
    />
  );

  return (
    <li
      className={`rounded-400 flex h-fit w-full items-center bg-gray-800 px-5 py-4`}
    >
      <div className='h-11 w-11'>
        <ProfileImage
          iconName={`@animals/${member.profileImage?.image}`}
          color={
            member.profileImage?.backgroundColor ?? 'var(--color-gray-200)'
          }
        />
      </div>
      <div className='w-3' />
      <div className='flex flex-1 flex-col gap-2'>
        <div className='flex items-center gap-3'>
          <p className='subtitle-1 text-gray-100'>{member.name}</p>
          {member.id == leaderId ?
            <Tag type={TagType.TEAM_LEADER} />
          : null}
        </div>
        <p className='caption-1 overflow-ellipsis text-gray-300'>
          {member.email}
        </p>
      </div>
      {iamLeader && member.id != leaderId ?
        <div className='flex gap-2'>
          <div
            className='flex h-9 w-9 cursor-pointer items-center justify-center rounded-full bg-gray-600 p-1.5'
            onClick={() => {
              showModal(changeLeaderModal);
            }} // 팀장 권한 부여
          >
            <Icon name={'crown'} color={'var(--color-gray-200)'} />
          </div>
          <div
            className='flex h-9 w-9 cursor-pointer items-center justify-center rounded-full bg-gray-600 p-1.5'
            onClick={() => {
              showModal(kickMemberModal);
            }} // 추방
          >
            <Icon name={'logout'} color={'var(--color-gray-200)'} />
          </div>
        </div>
      : null}
    </li>
  );
}
