import { useParams } from 'react-router-dom';
import { useEffect, useRef, useState } from 'react';
import NavBar2 from '../../components/NavBar2';
import StickyWrapper from '../../components/wrappers/StickyWrapper';
import { DropdownSmall } from '../../components/Dropdown';
import Icon from '../../components/Icon';
import FeedBack, { FeedBackType } from '../feedback/components/FeedBack';
import { useGetSelfFeedback } from '../../api/useMyPage';

export default function SelfFeedback() {
  const { userId } = useParams();
  const [feedbacks, setFeedbacks] = useState([]);
  const [selectedTeam, setSelectedTeam] = useState('전체 보기');
  const [onlyLiked, setOnlyLiked] = useState(false);
  const [sortBy, setSortBy] = useState('createdAt:desc');
  const [loadedPage, setLoadedPage] = useState(0);
  const scrollRef = useRef(null);

  const {
    data: retrospect,
    isLoading,
    refetch,
  } = useGetSelfFeedback(userId, {
    teamId: selectedTeam === '전체 보기' ? null : selectedTeam,
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
    if (!retrospect) return;
    setFeedbacks((prev) => [...prev, ...(retrospect?.content ?? [])]);
  }, [retrospect]);

  useEffect(() => {
    const container = scrollRef.current;
    container.scrollTo(0, 0);
    setLoadedPage(0);
    setFeedbacks([]);
    refetch();
  }, [selectedTeam, onlyLiked, sortBy]);

  console.log(feedbacks);

  return (
    <div className='flex h-full flex-col overflow-y-auto' ref={scrollRef}>
      <StickyWrapper>
        <NavBar2 canPop={true} canClose={false} title='받은 피드백' />
        <div className='flex justify-between gap-4 border-b border-gray-700 py-5'>
          <DropdownSmall
            triggerText={selectedTeam}
            setTriggerText={setSelectedTeam}
            items={[]}
          />
          <div className='button-2 flex items-center gap-2 text-gray-100'>
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
          {feedbacks.map((feedback, index) => {
            return (
              <li key={index}>
                <FeedBack feedbackType={FeedBackType.SELF} data={feedback} />
              </li>
            );
          })}
        </ul>
      )}
    </div>
  );
}
