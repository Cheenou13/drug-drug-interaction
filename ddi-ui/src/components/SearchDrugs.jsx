import React from 'react';

const SearchDrugs = ({ inputValue, removeValue }) => {
  const displayOpt = inputValue.length > 0 ? 'block' : 'none';
  return (
    <>
      <div className="" data-max="5" data-min="2" id="drug-product-list" style={{ display: displayOpt }}>
        {inputValue.slice(0, 5).map((val, index) => (
          <div className="drug-product btn btn-md btn-label" key={index}>
            <span className="bg-[#f7f7f7] rounded-lg">{val}</span>
            <div className="icon-circle drugbank-icon icon-minus justify-center items-center text-center" 
                 style={{ display: 'flex' }} 
                 onClick={() => removeValue(val)}
            >
              <span style={{ display: 'block', backgroundColor: '#fff', height: '2px', position: 'relative', width: '55%', borderRadius: '50%' }}></span>
            </div>
          </div>
        ))}
      </div>
    </>
  );
};

export default SearchDrugs;
