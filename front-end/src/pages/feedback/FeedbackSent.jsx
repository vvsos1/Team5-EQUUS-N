import { useParams } from 'react-router-dom';
import { useFeedbackSent } from '../../api/useFeedback';
import { useEffect, useRef, useState } from 'react';
import NavBar2 from '../../components/NavBar2';
import StickyWrapper from '../../components/wrappers/StickyWrapper';
import { DropdownSmall } from '../../components/Dropdown';
import Icon from '../../components/Icon';
import FeedBack, { FeedBackType } from './components/FeedBack';

export default function FeedbackSent() {
  const { userId } = useParams();
  const [feedbacks, setFeedbacks] = useState([]);
  const [selectedTeam, setSelectedTeam] = useState('전체 보기');
  const [onlyLiked, setOnlyLiked] = useState(false);
  const [sortBy, setSortBy] = useState('createdAt:desc');
  const [loadedPage, setLoadedPage] = useState(0);
  const scrollRef = useRef(null);

  const {
    data: feedbackSent,
    isLoading,
    refetch,
  } = useFeedbackSent(userId, {
    teamId: selectedTeam === '전체 보기' ? null : selectedTeam,
    onlyLiked,
    sortBy,
    page: loadedPage,
  });

  useEffect(() => {
    const container = scrollRef.current;
    function handleScroll() {
      if (
        container.scrollTop + container.clientHeight >=
          container.scrollHeight - 200 &&
        !isLoading
      ) {
        setLoadedPage(loadedPage + 1);
        refetch();
      }
    }
    container.addEventListener('scroll', handleScroll);
    return () => {
      container.removeEventListener('scroll', handleScroll);
    };
  }, [isLoading]);

  useEffect(() => {
    refetch();
  }, [loadedPage, refetch]);

  useEffect(() => {
    if (!feedbackSent) return;
    setFeedbacks((prev) => [...prev, ...(feedbackSent?.content ?? [])]);
  }, [feedbackSent]);

  useEffect(() => {
    const container = scrollRef.current;
    container.scrollTo(0, 0);
    setLoadedPage(0);
    setFeedbacks([]);
    refetch();
  }, [selectedTeam, onlyLiked, sortBy]);

  return (
    <div className='flex h-full flex-col overflow-y-auto' ref={scrollRef}>
      <StickyWrapper>
        <NavBar2 canPop={true} canClose={false} title='보낸 피드백' />
        <div className='flex justify-between gap-4 border-b border-gray-700 py-5'>
          <DropdownSmall
            triggerText={selectedTeam}
            setTriggerText={setSelectedTeam}
            items={[]}
          />
          <div className='button-2 flex items-center gap-2 text-gray-100'>
            <button
              onClick={() => {
                setOnlyLiked(!onlyLiked);
                refetch();
              }}
            >
              <p className={onlyLiked ? 'caption-2 text-lime-500' : ''}>
                도움 준 피드백
              </p>
            </button>
            <p>•</p>
            <button
              className='flex items-center gap-1'
              onClick={() => {
                setSortBy(
                  sortBy === 'createdAt:desc' ? 'createdAt:asc' : (
                    'createdAt:desc'
                  ),
                );
                refetch();
              }}
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
                <FeedBack feedbackType='SEND' data={feedback} />
              </li>
            );
          })}
        </ul>
      )}
    </div>
  );
}
