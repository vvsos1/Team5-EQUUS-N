import { useNavigate, useParams } from 'react-router-dom';
import { useFeedbackSent } from '../../api/useFeedback';
import { useEffect, useRef, useState } from 'react';
import NavBar2 from '../../components/NavBar2';
import StickyWrapper from '../../components/wrappers/StickyWrapper';
import { DropdownSmall } from '../../components/Dropdown';
import Icon from '../../components/Icon';
import FeedBack, { FeedBackType } from './components/FeedBack';
import { useUser } from '../../useUser';
import { useTeam } from '../../useTeam';

export default function FeedbackSent() {
  const navigate = useNavigate();
  const [feedbacks, setFeedbacks] = useState([]);
  const { teams } = useTeam();
  const [selectedTeam, setSelectedTeam] = useState('전체 보기');
  const [onlyLiked, setOnlyLiked] = useState(false);
  const [sortBy, setSortBy] = useState('createdAt:desc');
  const [loadedPage, setLoadedPage] = useState(0);
  const [noMoreData, setNoMoreData] = useState(false);
  const scrollRef = useRef(null);

  const { userId } = useUser();
  const {
    data: feedbackSent,
    isLoading,
    isError,
    error,
    refetch,
  } = useFeedbackSent(userId, {
    teamId:
      selectedTeam === '전체 보기' ? null : (
        teams.find((t) => t.name === selectedTeam)?.id
      ),
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
        if (!noMoreData) {
          setLoadedPage(loadedPage + 1);
        }
      }
    }
    container.addEventListener('scroll', handleScroll);
    return () => {
      container.removeEventListener('scroll', handleScroll);
    };
  }, [isLoading]);

  useEffect(() => {
    if (!noMoreData) {
      refetch();
    }
  }, [loadedPage, refetch]);

  useEffect(() => {
    if (!feedbackSent) return;
    if (!feedbackSent.hasNext) {
      setNoMoreData(true);
    }
    setFeedbacks((prev) => [...prev, ...(feedbackSent?.content ?? [])]);
  }, [feedbackSent]);

  function refreshData() {
    setNoMoreData(false);
    const container = scrollRef.current;
    container.scrollTo(0, 0);
    setLoadedPage(0);
    setFeedbacks([]);
    refetch();
  }

  return (
    <div
      className='scrollbar-hidden flex h-full flex-col overflow-x-hidden overflow-y-auto'
      ref={scrollRef}
    >
      <StickyWrapper>
        <NavBar2
          canPop={true}
          canClose={false}
          title='보낸 피드백'
          onClickPop={() => navigate(-1)}
        />
        <div className='flex justify-between gap-4 border-b border-gray-700 py-5'>
          <DropdownSmall
            triggerText={selectedTeam}
            setTriggerText={(text) => {
              setSelectedTeam(text);
              refreshData();
            }}
            items={teams.map((team) => team.name)}
          />
          <div className='button-2 flex items-center gap-2 text-gray-100'>
            <button
              onClick={() => {
                setOnlyLiked(!onlyLiked);
                refreshData();
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
                refreshData();
              }}
            >
              <p>{sortBy === 'createdAt:desc' ? '최신순' : '과거순'}</p>
              <Icon name='swapVert' />
            </button>
          </div>
        </div>
      </StickyWrapper>
      {feedbacks.length > 0 ?
        <ul>
          {feedbacks.map((feedback) => {
            return (
              <li key={feedback.feedbackId}>
                <FeedBack feedbackType='SEND' data={feedback} />
              </li>
            );
          })}
        </ul>
      : <div className='flex h-full flex-col items-center justify-center gap-4 text-gray-300'>
          <p className='text-5xl'>📭</p>
          <p>보낸 피드백이 없어요</p>
        </div>
      }
    </div>
  );
}
