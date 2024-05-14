import { useState } from 'react'
// import reactLogo from './assets/react.svg'
// import viteLogo from '/vite.svg'
import './components/Nav.jsx'
import Nav from './components/Nav.jsx'
import InteractionLabel from './components/DrugLabel.jsx'
import RxForm from './components/RxForm.jsx'
import DrugInteractions from './components/results/DrugInteractions.jsx'

function App() {
  const [count, setCount] = useState(0)

  return (
    <div className='content-container'>
      <Nav></Nav>
      <div className='form-content interax-form-content'>
        <div className='form-container'>
          <InteractionLabel></InteractionLabel>
          <RxForm></RxForm>
          <div className='results'>
            <div className='anchor' id='results'></div>
            <h2><strong>Interactions Found</strong></h2>
            <DrugInteractions></DrugInteractions>
            <DrugInteractions></DrugInteractions>
          </div>
        </div>
      </div>
    </div>
  )
}

export default App
