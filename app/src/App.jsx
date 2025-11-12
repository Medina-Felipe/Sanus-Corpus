// src/App.jsx
import React from 'react'
import { Routes, Route } from 'react-router-dom' // ❌ NO Router aquí
import HomePage from './Pages/HomePage'
import ProductPage from './Pages/ProductPage'
import SearchPage from './Pages/Search.Page'

function App() {
  return (
    <div className="App">
      <Routes> {/* ✅ Solo Routes, NO Router */}
        <Route path="/" element={<HomePage />} />
        <Route path="/product/:id" element={<ProductPage />} />
        <Route path="/search" element={<SearchPage />} />
      </Routes>
    </div>
  )
}

export default App