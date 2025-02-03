import React from 'react';
import { BrowserRouter, Route, Routes } from 'react-router';
import './index.css';
import Layout from './Layout';
import FeedbackRequest from './pages/feedback/FeedbackRequest';

export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route element={<Layout />}>
          <Route
            path='/'
            element={<div className='size-full bg-amber-100' />}
          />
          <Route
            path='home'
            element={<div className='size-full bg-cyan-100' />}
          />
          <Route path='feedback'>
            <Route path='request' element={<FeedbackRequest />} />
          </Route>
        </Route>
      </Routes>
    </BrowserRouter>
  );
}
