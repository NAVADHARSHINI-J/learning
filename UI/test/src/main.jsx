
import { BrowserRouter } from 'react-router'
import App from './App.jsx'
import { createRoot } from 'react-dom/client'

createRoot(document.getElementById('root')).render(
  <BrowserRouter>
    <App />
  </BrowserRouter>
)
