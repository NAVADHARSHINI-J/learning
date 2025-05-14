import "./LiquidAssetDetails.css"
import Sidebar from './Sidebar'
function LiquidAssetDetails(){
    return(
        <div>
             <div className="container-fluid">
        <div className="row">
            {/* <!-- Sidebar --> */}
            <Sidebar/>
            {/* <!-- Main Content --> */}
            <div className="col-md-10 p-4">
                <div className="card">
                    <div className="card-header secondary-bg text-white">
                        Liquid Asset Details
                    </div>
                    <div className="card-body">
                        <div className="row">
                            <div className="col-md-6">
                                <h3 id="assetName">Cash Reserves</h3>
                                <hr/>
                                <div className="row mb-3">
                                    <div className="col-6">
                                        <strong>Total Amount:</strong> $500,000
                                    </div>
                                    <div className="col-6">
                                        <strong>Allocation Date:</strong> 2023-01-15
                                    </div>
                                </div>
                                <div className="row mb-3">
                                    <div className="col-6">
                                        <strong>Asset Type:</strong> Liquid Cash
                                    </div>
                                    <div className="col-6">
                                        <strong>Current Status:</strong> Active
                                    </div>
                                </div>
                                <div className="row mb-3">
                                    <div className="col-12">
                                        <strong>Description:</strong>
                                        <p>Liquid cash reserves maintained for immediate operational needs and short-term financial requirements.</p>
                                    </div>
                                </div>
                                <div className="row mb-3">
                                    <div className="col-6">
                                        <strong>Account Type:</strong> Operating Reserves
                                    </div>
                                    <div className="col-6">
                                        <strong>Last Updated:</strong> 2023-06-30
                                    </div>
                                </div>
                                <div className="d-grid">
                                    <a href="liquid-assets.html" className="btn btn-primary">Back to Liquid Assets</a>
                                </div>
                            </div>
                            <div className="col-md-6">
                                <div className="card">
                                    <div className="card-header secondary-bg text-white">
                                        Transaction History
                                    </div>
                                    <div className="card-body">
                                        <table className="table">
                                            <thead>
                                                <tr>
                                                    <th>Date</th>
                                                    <th>Description</th>
                                                    <th>Amount</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <tr>
                                                    <td>2023-06-15</td>
                                                    <td>Quarterly Deposit</td>
                                                    <td>+$100,000</td>
                                                </tr>
                                                <tr>
                                                    <td>2023-05-20</td>
                                                    <td>Operational Expense</td>
                                                    <td>-$50,000</td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

        </div>

    )
}

export default LiquidAssetDetails;