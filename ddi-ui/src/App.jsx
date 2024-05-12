import { useState } from 'react'
// import reactLogo from './assets/react.svg'
// import viteLogo from '/vite.svg'
import './components/Nav.jsx'
import Nav from './components/Nav.jsx'

function App() {
  const [count, setCount] = useState(0)

  return (
    <div className='content-container'>
      <Nav></Nav>
      <div className='form-content interax-form-content'>

      </div>
    </div>
  )
}

export default App
