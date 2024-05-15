import React, {useState} from "react";

const SearchAction = ({inputValue}) => {
    let _url = ""
    const handleClick = () => {
        for (let i = 0; i < inputValue.length; ++ i){
            for (let j = i+1; j < inputValue.length; ++ j){
                _url =  `http://localhost:8080/api/drug-interactions?drugA=${inputValue[i]}&drugB=${inputValue[j]}`
                console.log("endPoint Search: ", _url);
            }
        }

        console.log("button is clicked. ", inputValue)
    }
    return (
        <>
            <div className="search-actions">
                <button name="button" type="button" 
                        className="btn-1 btn-pink-filled-1 mr-auto px-4 hover:bg-[#d00993] transition duration-150 ease-in-out shadow hover:shadow-lg"
                        onClick={handleClick}
                        >Check Interactions</button>
                <div className="right-buttons">
                    <a className="search-clear btn-2 btn-blue btn-sm ml-2 mb-2 transition duration-150 ease-in-out shadow hover:shadow-lg hover:bg-[#00b0f2] hover:text-[#fff]" href="#">Clear</a>
                </div>
            </div>

        </>
    )
}

export default SearchAction;