const DrugInteractions = ({tempResponse}) => {
    const displayOpt = tempResponse.length > 0 ? 'block' : 'none';
    for (let i = 0; i < tempResponse.length; ++i){
        console.log("drug_A: ", tempResponse[i]['data']['drug_A']);
        console.log("drug_B: ", tempResponse[i]['data']['drug_B']);
        console.log("serity: ", tempResponse[i]['data']['severity']);
        console.log("interaction: ", tempResponse[i]['data']['interaction']);
        
    }
        
    return (
        <>
            <div className="drug-interactions" style={{display:displayOpt}}>
                {tempResponse.map((interaction, index) => (
                    <div className="interactions-box"
                         key={index}
                    >
                        <div className="interactions-row main-row">
                            <div className="interactions-col subject">
                                <h5><a className="drug-link" href="#">{interaction.data.drug_A}</a></h5>
                            </div>
                            <div className="interactions-col interacts">
                                <div className="severity-major drug-icon-1 icon-interacts">
                                    <div className="flex">
                                        <svg fill="#741d5c" version="1.1" id="Capa_1" xmlns="http://www.w3.org/2000/svg" xmlnsXlink="http://www.w3.org/1999/xlink" 
                                            viewBox="0 0 571.815 571.815" xmlSpace="preserve" stroke="#741d5c" transform="rotate(0)">
                                            <g id="SVGRepo_bgCarrier" strokeWidth="0"></g><g id="SVGRepo_tracerCarrier" strokeLinecap="round" strokeLinejoin="round"></g>
                                            <g id="SVGRepo_iconCarrier"> <g> <g> 
                                            <path d="M117.518,296.042l333.161,272.132c8.286,6.646,12.062,3.941,8.43-6.04l-88.442-260.049 
                                            c-3.63-9.981-3.596-26.156,0.076-36.123l88.29-256.26c3.672-9.966-0.101-12.702-8.431-6.11L117.594,272.07 C109.265,278.661,109.231,289.395,117.518,296.042z">
                                            </path> </g> </g> </g>
                                        </svg>
                                        <svg fill="#741d5c" version="1.1" id="Capa_1" xmlns="http://www.w3.org/2000/svg" xmlnsXlink="http://www.w3.org/1999/xlink" 
                                            viewBox="0 0 571.815 571.815" xmlSpace="preserve" stroke="#741d5c" transform="rotate(180)">
                                            <g id="SVGRepo_bgCarrier" strokeWidth="0"></g><g id="SVGRepo_tracerCarrier" strokeLinecap="round" strokeLinejoin="round"></g>
                                            <g id="SVGRepo_iconCarrier"> <g> <g> 
                                            <path d="M117.518,296.042l333.161,272.132c8.286,6.646,12.062,3.941,8.43-6.04l-88.442-260.049 
                                                c-3.63-9.981-3.596-26.156,0.076-36.123l88.29-256.26c3.672-9.966-0.101-12.702-8.431-6.11L117.594,272.07 C109.265,278.661,109.231,289.395,117.518,296.042z">
                                            </path> </g> </g> </g>
                                        </svg>
                                    </div>
                                </div>
                            </div>
                            <div className="interactions-col affected">
                                <h5><a className="drug-link" href="#">{interaction.data.drug_B}</a></h5>
                            </div>
                            <div className="interactions-col severity">
                                <div className="interactions-header">
                                    Severity
                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-question-circle mb-[0.1em]" viewBox="0 0 16 16">
                                        <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14m0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16"/>
                                        <path d="M5.255 5.786a.237.237 0 0 0 .241.247h.825c.138 0 .248-.113.266-.25.09-.656.54-1.134 1.342-1.134.686 0 1.314.343 1.314 1.168 0 .635-.374.927-.965 1.371-.673.489-1.206 1.06-1.168 1.987l.003.217a.25.25 0 0 0 .25.246h.811a.25.25 0 0 0 .25-.25v-.105c0-.718.273-.927 1.01-1.486.609-.463 1.244-.977 1.244-2.056 0-1.511-1.276-2.241-2.673-2.241-1.267 0-2.655.59-2.75 2.286m1.557 5.763c0 .533.425.927 1.01.927.609 0 1.028-.394 1.028-.927 0-.552-.42-.94-1.029-.94-.584 0-1.009.388-1.009.94"/>
                                    </svg>
                                </div>
                                <span className="severity-badge severity-major">{interaction.data.severity}</span>
                            </div>
                            <div className="interactions-col description">
                                <div className="interactions-header">Description</div>
                                <p>{interaction.data.interaction}</p>
                                <div className="tooltip">{interaction.data.interaction}</div>
                            </div>
                        </div>
                    </div>
                ))}
            </div>
        </>
    )
}

export default DrugInteractions;