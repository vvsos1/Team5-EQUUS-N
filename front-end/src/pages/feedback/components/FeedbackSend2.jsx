import { useState } from 'react';
import { useFeedbackKeyword } from '../../../api/useFeedback';
import KeywordButton from '../../../components/buttons/KeywordButton';
import { showToast } from '../../../utility/handleToast';
import FooterWrapper from '../../../components/wrappers/FooterWrapper';
import LargeButton from '../../../components/buttons/LargeButton';
import { useLoaderData, useLocation, useNavigate } from 'react-router-dom';

export default function FeedbackSend2() {
  const navigate = useNavigate();
  const state = useLocation().state;

  const { data } = useFeedbackKeyword();

  const [selectedKeywords, setSelectedKeywords] = useState([]);

  const onKeywordButtonClick = (keyword) => {
    selectedKeywords.includes(keyword) ?
      setSelectedKeywords([
        ...selectedKeywords.filter((item) => item !== keyword),
      ])
    : selectedKeywords.length < 5 ?
      setSelectedKeywords([...selectedKeywords, keyword])
    : showToast('키워드는 5개까지 선택 가능해요');
  };

  return (
    <div className='flex size-full flex-col gap-8'>
      <h1 className='header-2 text-gray-0 mt-3 whitespace-pre-line'>
        {'보낼 피드백 키워드를\n선택해 주세요'}
      </h1>
      <ul className='scrollbar-hidden flex w-full gap-8 overflow-x-auto p-1 whitespace-nowrap'>
        {data &&
          data.map((item, index) => {
            return (
              <KeywordColumn
                key={index}
                title={item.title}
                keywords={item.keywords}
                selectedKeywords={selectedKeywords}
                onClick={onKeywordButtonClick}
              />
            );
          })}
      </ul>
      <FooterWrapper>
        <LargeButton
          isOutlined={false}
          text='다음'
          disabled={selectedKeywords.length === 0}
          onClick={() =>
            selectedKeywords.length > 0 &&
            navigate('../3', {
              state: { ...state, objectiveFeedbacks: selectedKeywords },
            })
          }
        />
      </FooterWrapper>
    </div>
  );
}

const KeywordColumn = ({ title, keywords, selectedKeywords, onClick }) => {
  return (
    <li className='inline-block w-80'>
      <h2 className='subtitle-1 text-gray-0 mb-3'>{title}</h2>
      <div className='flex flex-col gap-2'>
        {keywords.map((keyword, index) => (
          <KeywordButton
            key={index}
            isActive={selectedKeywords.includes(keyword)}
            onClick={() => onClick(keyword)}
          >
            {keyword}
          </KeywordButton>
        ))}
      </div>
    </li>
  );
};
