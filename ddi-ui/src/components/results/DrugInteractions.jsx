const DrugInteractions = (props) => {
    const interactionsBoxStyling = {

    }
    return (
        <>
            <div className="drug-interactions">
                <div className="interactions-box">
                    <div className="interactions-row main-row">
                        <div className="interactions-col subject">
                            <h5><a className="drug-link" href="#">Isotretinoin</a></h5>
                        </div>
                        <div className="interactions-col interacts">
                            <div className="severity-major drug-icon-1 icon-interacts">
                                <div className="flex">
                                    <svg fill="#ea6771" version="1.1" id="Capa_1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" 
                                        viewBox="0 0 571.815 571.815" xml:space="preserve" stroke="#ea6771" transform="rotate(0)">
                                        <g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g>
                                        <g id="SVGRepo_iconCarrier"> <g> <g> 
                                        <path d="M117.518,296.042l333.161,272.132c8.286,6.646,12.062,3.941,8.43-6.04l-88.442-260.049 
                                        c-3.63-9.981-3.596-26.156,0.076-36.123l88.29-256.26c3.672-9.966-0.101-12.702-8.431-6.11L117.594,272.07 C109.265,278.661,109.231,289.395,117.518,296.042z">
                                        </path> </g> </g> </g>
                                    </svg>
                                    <svg fill="#ea6771" version="1.1" id="Capa_1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" 
                                        viewBox="0 0 571.815 571.815" xml:space="preserve" stroke="#ea6771" transform="rotate(180)">
                                        <g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g>
                                        <g id="SVGRepo_iconCarrier"> <g> <g> 
                                        <path d="M117.518,296.042l333.161,272.132c8.286,6.646,12.062,3.941,8.43-6.04l-88.442-260.049 
                                            c-3.63-9.981-3.596-26.156,0.076-36.123l88.29-256.26c3.672-9.966-0.101-12.702-8.431-6.11L117.594,272.07 C109.265,278.661,109.231,289.395,117.518,296.042z">
                                        </path> </g> </g> </g>
                                    </svg>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </>
    )
}

export default DrugInteractions;