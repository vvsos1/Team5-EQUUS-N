import { useLocation, useNavigate } from 'react-router-dom';
import {
  useFeedbackFavoriteByUser,
  useFeedbackRefinement,
} from '../../../api/useFeedback';
import Tag, { TagType } from '../../../components/Tag';
import TextArea from '../../../components/TextArea';
import { useTeam } from '../../../useTeam';
import { useReducer, useState } from 'react';
import AiButton from '../../../components/buttons/AiButton';
import FooterWrapper from '../../../components/wrappers/FooterWrapper';
import LargeButton from '../../../components/buttons/LargeButton';
import { transformToBytes } from '../../../utility/inputChecker';

export default function FeedbackSend3() {
  const navigate = useNavigate();
  const state = useLocation().state;

  const { data: favoriteKeywords } = useFeedbackFavoriteByUser();
  const { teams, selectedTeam } = useTeam();
  const mutation = useFeedbackRefinement();

  const [isAnonymous, toggleAnonymous] = useReducer(
    (prev) => !prev,
    teams.find((team) => team.id === selectedTeam).feedbackType === 'ANONYMOUS',
  );
  const [textLength, setTextLength] = useState(0);
  const [textContent, setTextContent] = useState('');
  const [gptContent, setGptContent] = useState(null);

  return (
    <div className='flex size-full flex-col'>
      <h1 className='header-2 text-gray-0 mt-3 whitespace-pre-line'>
        {'자세한 내용을 작성해 보세요!'}
      </h1>
      <p className='body-1 mt-8 mb-2 text-gray-300'>{`${state.receiver.name}님이 원하는 피드백 스타일이에요!`}</p>
      {favoriteKeywords && (
        <div className='mb-5 flex flex-wrap gap-2'>
          {favoriteKeywords.map((keyword, index) => (
            <Tag key={index} type={TagType.KEYWORD}>
              {keyword}
            </Tag>
          ))}
        </div>
      )}
      <TextArea
        textContent={textContent}
        textLength={textLength}
        setTextContent={setTextContent}
        setTextLength={setTextLength}
        isWithGpt={true}
        canToggleAnonymous={true}
        toggleAnonymous={toggleAnonymous}
        isAnonymous={isAnonymous}
      />
      {gptContent !== null && (
        <>
          <div className='h-5' />
          <TextArea
            generatedByGpt={true}
            textContent={gptContent}
            textLength={transformToBytes(gptContent).byteCount}
          />
        </>
      )}
      <div className='h-5' />
      <div className='flex w-full justify-end'>
        {gptContent ?
          <div className='flex items-center gap-2'>
            <AiButton
              isActive={false}
              onClick={() => setTextContent(gptContent)}
            >
              적용하기
            </AiButton>
            <AiButton
              isActive={true}
              onClick={() =>
                mutation.mutate(
                  {
                    receiverId: state.receiver.id,
                    objectiveFeedbacks: state.objectiveFeedback,
                    subjectiveFeedback: textContent,
                  },
                  {
                    onSuccess: (data) => setGptContent(data.subjectiveFeedback),
                  },
                )
              }
            >
              재생성하기
            </AiButton>
          </div>
        : <AiButton
            isActive={true}
            onClick={() =>
              mutation.mutate(
                {
                  receiverId: state.receiver.id,
                  objectiveFeedbacks: state.objectiveFeedback,
                  subjectiveFeedback: textContent,
                },
                {
                  onSuccess: (data) => setGptContent(data.subjectiveFeedback),
                },
              )
            }
          >
            AI 글 다듬기
          </AiButton>
        }
      </div>
      <FooterWrapper>
        <LargeButton
          isOutlined={false}
          text='다음'
          disabled={textContent.length === 0}
          onClick={() => {
            if (textContent.length > 0) {
              textContent.length > 0 && navigate('../../complete?type=SEND');
              // 피드백 보내기 뮤테이션
            }
          }}
        />
      </FooterWrapper>
    </div>
  );
}
