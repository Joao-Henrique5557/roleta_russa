import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './styles/global/index.css'
import AutenticationLogin from './pages/authentication/Login.jsx'

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <AutenticationLogin />
  </StrictMode>,
)
