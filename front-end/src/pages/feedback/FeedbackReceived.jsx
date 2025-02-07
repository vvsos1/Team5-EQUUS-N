import { useParams } from 'react-router-dom';
import { useFeedbackReceived } from '../../api/useFeedback';
import { useEffect, useState } from 'react';
import NavBar2 from '../../components/NavBar2';
import StickyWrapper from '../../components/wrappers/StickyWrapper';
import { DropdownSmall } from '../../components/Dropdown';
import Icon from '../../components/Icon';
import FeedBack, { FeedBackType } from './components/FeedBack';

export default function FeedbackReceived() {
  const { memberId } = useParams();
  const [feedbacks, setFeedbacks] = useState([]);
  const [selectedTeam, setSelectedTeam] = useState('전체 보기');
  const [onlyLiked, setOnlyLiked] = useState(false);
  const [sortBy, setSortBy] = useState('createdAt:desc');
  const {
    data: feedbackReceived,
    isLoading,
    isError,
  } = useFeedbackReceived(memberId);

  useEffect(() => {
    if (!feedbackReceived) return;
    console.log(feedbackReceived);
    setFeedbacks(feedbackReceived?.content ?? []);
  }, [feedbackReceived]);

  if (isLoading) {
    return <div>Loading...</div>;
  }

  if (isError) {
    return <div>Error...</div>;
  }

  return (
    <div className='flex flex-col'>
      <StickyWrapper>
        <NavBar2 canPop={true} canClose={false} title='받은 피드백' />
        <div className='flex justify-between gap-4 border-b border-gray-700 py-5'>
          <DropdownSmall
            triggerText={selectedTeam}
            setTriggerText={setSelectedTeam}
            items={[]}
          />
          <div className='button-2 flex items-center gap-2 text-gray-100'>
            <button onClick={() => setOnlyLiked(!onlyLiked)}>
              <p className={onlyLiked ? 'caption-2 text-lime-500' : ''}>
                도움 받은 피드백
              </p>
            </button>
            <p>•</p>
            <button
              className='flex items-center gap-1'
              onClick={() =>
                setSortBy(
                  sortBy === 'createdAt:desc' ? 'createdAt:asc' : (
                    'createdAt:desc'
                  ),
                )
              }
            >
              <p>{sortBy === 'createdAt:desc' ? '최신순' : '과거순'}</p>
              <Icon name='swapVert' />
            </button>
          </div>
        </div>
      </StickyWrapper>
      {feedbacks && (
        <ul>
          {feedbacks.map((feedback) => {
            return (
              <li key={feedback.feedbackId}>
                <FeedBack feedbackType='RECEIVE' data={feedback} />
              </li>
            );
          })}
        </ul>
      )}
    </div>
  );
}
