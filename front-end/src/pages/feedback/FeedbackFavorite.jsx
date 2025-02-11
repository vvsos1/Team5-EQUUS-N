import { useLocation } from 'react-router-dom';
import NavBar from '../auth/components/NavBar';
import Tag, { TagType } from '../../components/Tag';
import KeywordButton from '../../components/buttons/KeywordButton';
import { useFeedbackFavorite } from '../../api/useFeedback';
import { useEffect, useState } from 'react';
import FooterWrapper from '../../components/wrappers/FooterWrapper';
import LargeButton from '../../components/buttons/LargeButton';
import { use } from 'react';

export default function FeedbackFavorite() {
  const location = useLocation();
  const process = new URLSearchParams(location.search).get('process');

  const [selectedStyle, setSelectedStyle] = useState([]);
  const [selectedContent, setSelectedContent] = useState([]);

  const { data } = useFeedbackFavorite();

  // mutate의 onSuccess에 라우팅 메서드 전달
  // const mutation = use회원가입(navigate('/main'));

  const onKeywordButtonClick = (isStyle, keyword) => {
    const keywords = isStyle ? selectedStyle : selectedContent;
    const setKeywords = isStyle ? setSelectedStyle : setSelectedContent;
    keywords.find((k) => k == keyword) ?
      setKeywords([...keywords.filter((item) => item !== keyword)])
    : keywords.length < 2 && setKeywords([...keywords, keyword]);
  };

  return (
    <div className='flex size-full flex-col'>
      {process === 'signup' && (
        <h1 className='header-2 text-gray-0 pt-10 pb-3 whitespace-pre-line'>
          {'선호하는 피드백 유형을\n선택해 주세요'}
        </h1>
      )}
      <p className='body-1 text-gray-200'>
        카테고리별 최대 2개까지 선택해 주세요
      </p>
      {data && (
        <div className='mt-10 flex flex-col'>
          <h2 className='subtitle-1 text-gray-0 mb-3'>스타일</h2>
          <div className='flex flex-wrap gap-2'>
            {data[0]['스타일'].map((keyword, index) => (
              <KeywordButton
                type={TagType.KEYWORD}
                key={index}
                isActive={selectedStyle.includes(keyword)}
                onClick={() => onKeywordButtonClick(true, keyword)}
              >
                {keyword}
              </KeywordButton>
            ))}
          </div>
          <div className='h-8' />
          <h2 className='subtitle-1 text-gray-0 mb-3'>내용</h2>
          <div className='flex flex-wrap gap-2'>
            {data[1]['내용'].map((keyword, index) => (
              <KeywordButton
                type={TagType.KEYWORD}
                key={index}
                isActive={selectedContent.includes(keyword)}
                onClick={() => onKeywordButtonClick(false, keyword)}
              >
                {keyword}
              </KeywordButton>
            ))}
          </div>
        </div>
      )}
      <FooterWrapper>
        <LargeButton
          isOutlined={false}
          text={'완료'} // 로딩 중일 때 버튼 텍스트 변경... 추후 수정 필요
          disabled={selectedStyle.length === 0 && selectedContent.length === 0}
          onClick={() => {
            mutation.mutate({
              email: location.state.email,
              password: location.state.password,
              name: location.state,
              profileImage: location.state.profileImage,
              feedbackPreference: [...selectedStyle, ...selectedContent],
            });
          }}
        />
      </FooterWrapper>
    </div>
  );
}
