
import SearchDrugs from "./SearchDrugs.jsx";
import SearchAction from "./SearchAction.jsx";
import WarningTag from "./WaringTag.jsx";
import React, { useState } from 'react';

const RxForm = () => {

    const [inputValue, setInputValue] = useState('');
    const [submittedValues, setSubmittedValues] = useState([]);
  
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
        <>
            <div className="form-row interax-form-row">
                <form className="name-search-form" id="drug-interaction" action="/ddi#results" onSubmit={handleSubmit} acceptCharset="UTF-8" method="get">
                    <input type="hidden" name="authenticity_token" value={"ddi-12345"} autoComplete="off" ></input>
                    <div className="drug-search-box">
                        <h6 className='interax-header'>Add drug to check for DDI</h6>
                        <div className="drug-search">
                            <span className="select2 select2-container select2-container--default" dir="ltr" data-select2-id="1">
                                <span className="selection">
                                    <span className="select2-selection select2-selection--multiple" role="combobox" aria-expanded="false" tabIndex='-1' style={{}}>
                                        <ul className="select2-selection__rendered">
                                            <li className="select2-search select2-search--inline">
                                                <input className="select2-search__field" 
                                                        type="search" 
                                                        tabIndex='0' 
                                                        autoComplete="off" 
                                                        autoCorrect="off" 
                                                        autoCapitalize="none" 
                                                        spellCheck="false" 
                                                        role="textbox" 
                                                        aria-autocomplete="list" 
                                                        placeholder="medication name" 
                                                        style={{width: '1100px'}}
                                                        value={inputValue} 
                                                        onChange={handleInputChange}/>
                                            </li>
                                        </ul>
                                    </span>
                                </span>
                            </span>
                        </div>
                    </div>
                    <SearchDrugs inputValue={submittedValues} removeValue={removeValue}></SearchDrugs>
                    <SearchAction inputValue={submittedValues}></SearchAction>
                    <WarningTag></WarningTag>
                </form>
            </div>
        </>
    )
}

export default RxForm;