
const RxForm = () => {

    return (
        <>
            <div className="form-row interax-form-row">
                <form className="name-search-form" id="drug-interaction" action="/ddi#results" acceptCharset="UTF-8" method="post">
                    <input type="hidden" name="authenticity_token" value={"ddi-12345"} autoComplete="off" ></input>
                    <div className="drug-search-box">
                        <h6 className='interax-header'>Add drug to check for DDI</h6>
                        <div className="drug-search"></div>
                    </div>
                </form>
            </div>
        </>
    )
}

export default RxForm;