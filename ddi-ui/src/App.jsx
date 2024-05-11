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
      <h1>Hello world React and vite</h1>
      <p>Hello Chee 4x</p>
    </div>
  )
}

export default App
