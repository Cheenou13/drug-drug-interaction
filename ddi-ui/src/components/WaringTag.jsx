const WarningTag = () => {
    return (
        <>
            <div className="warning" id="disclaimer">
                <div className="drug-icon icon-waring-dark">
                    <svg xmlns="http://www.w3.org/2000/svg" 
                         fill="#ea6771" 
                         viewBox="0 0 24 24" 
                         strokeWidth="1.5" 
                         stroke="#fff" 
                         className="icon w-6 h-6" 
                         title="warning-dark">
                        <path strokeLinecap="round" 
                              strokeLinejoin="round" 
                              d="M12 9v3.75m-9.303 3.376c-.866 1.5.217 3.374 1.948 3.374h14.71c1.73 0 2.813-1.874 1.948-3.374L13.949 3.378c-.866-1.5-3.032-1.5-3.898 0L2.697 16.126ZM12 15.75h.007v.008H12v-.008Z" />
                    </svg>
                </div>
                <strong>Warning: </strong>
                <p className="ml-1">If no interactions are found between two drugs, it does not necessarily mean that no interactions exist. Always consult with a healthcare professional.</p>
                
            </div>
        </>
    )
}

export default WarningTag;