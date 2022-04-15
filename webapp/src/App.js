import React from 'react'
import { Routes, Route } from 'react-router-dom'
import { Home } from './pages/Home'
import  Header  from './components/Header'
import Footer from './components/Footer';
import { Error } from './pages/Error'
import { AuthProvider } from './components/auth'
import { Login } from './pages/Login'
import { Upload } from './pages/Upload'
import { Register } from './pages/Register'
import { RequireAuth } from './components/RequireAuth'
import Table from './pages/Table';
function App() {
  return (
    <AuthProvider>
      <Header />
      <Routes>
        <Route path='/' element={<Home />} />
        <Route path='/login' element={<Login />} />
        <Route path='/table' element={<Table />} />
        <Route path='/register' element={<Register />} />
        <Route
          path='/upload'
          element={
            <RequireAuth>
              <Upload />
            </RequireAuth>
          }
        />
        <Route path='*' element={<Error />} />
      </Routes>
      <Footer/>
    </AuthProvider>
  )
}

export default App
