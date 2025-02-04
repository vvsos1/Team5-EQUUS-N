import React from 'react';
import { BrowserRouter, Route, Routes } from 'react-router';
import './index.css';
import Layout from './Layout';
import FeedbackRequest from './pages/feedback/FeedbackRequest';
import Splash from './pages/auth/Splash';
import SignIn from './pages/auth/SignIn';
import SignUp from './pages/auth/SignUp';

export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route element={<Layout />}>
          <Route path='/' element={<Splash />} />
          <Route
            path='home'
            element={<div className='size-full bg-cyan-100' />}
          />
          <Route path='feedback'>
            <Route path='request' element={<FeedbackRequest />} />
          </Route>
          <Route path='signin' element={<SignIn />} />
          <Route path='signup' element={<SignUp />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}
