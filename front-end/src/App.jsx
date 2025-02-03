import React from 'react';
import { BrowserRouter, Route, Routes } from 'react-router';
import './index.css';
import Layout from './Layout';

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
        </Route>
      </Routes>
    </BrowserRouter>
  );
}
