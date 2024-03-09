import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.tsx'
import './index.css'
import { BrowserRouter, Routes, Route } from 'react-router-dom'
import ProductMenu from './routes/ProductMenu/index.tsx'
import ProductAbout from './routes/ProductAbout/index.tsx'
import ProductContact from './routes/ProductContact/index.tsx'


ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
    <BrowserRouter>
      <Routes>
        <Route path='/' element={<App />} >
          <Route path='menu' element={<ProductMenu />} />
          <Route path='about' element={<ProductAbout />} />
          <Route path='contact' element={<ProductContact />} />
        </Route>
        <Route path="*" element={<p> Não tem nada aqui </p>} ></Route>
      </Routes>
    </BrowserRouter>
  </React.StrictMode>,
)
