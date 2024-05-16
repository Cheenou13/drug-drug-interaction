import React, { useState } from 'react'
import Nav from './components/Nav.jsx'
import InteractionLabel from './components/DrugLabel.jsx'
import RxForm from './components/RxForm.jsx'
import DrugInteractions from './components/results/DrugInteractions.jsx'

function App() {

  const [inputValue, setInputValue] = useState('');
  const [submittedValues, setSubmittedValues] = useState([]);
  const [tempResponse, setTempResponse] = useState([]);
  const handleInputChange = (e) => {
    setInputValue(e.target.value);
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (inputValue.trim() !== '' && submittedValues.length < 5) {
      setSubmittedValues([...submittedValues, inputValue]);
      setInputValue(''); // Clear the input
    }
  };

  const removeValue = (val) => {
    setSubmittedValues(submittedValues.filter((item) => item !== val));
  };

  return (
    <div className='content-container'>
      <Nav></Nav>
      <div className='form-content interax-form-content'>
        <div className='form-container'>
          <InteractionLabel></InteractionLabel>
          <RxForm
            inputValue={inputValue}
            submittedValues={submittedValues}
            handleInputChange={handleInputChange}
            handleSubmit={handleSubmit}
            removeValue={removeValue}
            setTempResponse={setTempResponse}  
            setSubmittedValues={setSubmittedValues}          
          ></RxForm>
          <div className='results'>
            <div className='anchor' id='results'></div>
            <h2><strong>Interactions Found</strong></h2>
            <DrugInteractions tempResponse={tempResponse}></DrugInteractions>
          </div>
        </div>
      </div>
    </div>
  )
}

export default App
