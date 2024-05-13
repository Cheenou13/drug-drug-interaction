const SearchDrugs = (props) => {
    return (
        <>
            <div className=""data-max="5" data-min="2" id="drug-product-list">
                <div className="drug-product btn btn-md btn-label">
                    <span className="bg-[#f7f7f7]rounded-lg">Doxycycline</span>
                    <div className="icon-circle drugbank-icon icon-minus justify-center items-center text-center" style={{display: "flex"}}>
                        <span style={{ display: 'block', backgroundColor: '#ffff', height: '2px', position: 'relative', width: '55%', borderRadius: '50%'}}></span>
                    </div>
                </div>
                <div className="drug-product btn btn-md btn-label">
                    <span className="bg-[#f7f7f7]rounded-lg">Isotretinoin</span>
                    <div className="icon-circle drugbank-icon icon-minus justify-center items-center text-center" style={{display: "flex"}}>
                        <span style={{ display: 'block', backgroundColor: '#ffff', height: '2px', position: 'relative', width: '55%', borderRadius: '50%'}}></span>
                    </div>
                </div>
                <div className="drug-product btn btn-md btn-label">
                    <span className="bg-[#f7f7f7]rounded-lg">Calcium carbonate</span>
                    <div className="icon-circle drugbank-icon icon-minus justify-center items-center text-center" style={{display: "flex"}}>
                        <span style={{ display: 'block', backgroundColor: '#ffff', height: '2px', position: 'relative', width: '55%', borderRadius: '50%'}}></span>
                    </div>
                </div>
            </div>

        </>
    )
}

export default SearchDrugs;