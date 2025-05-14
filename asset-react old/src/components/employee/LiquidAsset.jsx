import "./LiquidAsset.css"
import Sidebar from './Sidebar'
function LiquidAsset() {

    return (
        <div>

            <div className="container-fluid">
                <div className="row">
                    {/* <!-- Sidebar --> */}
                   < Sidebar/>
                    {/* <!-- Main Content --> */}
                    <div className="col-md-10 p-4">
                        <div className="card">
                            <div className="card-header secondary-bg text-white d-flex justify-content-between align-items-center">
                                Liquid Assets
                            </div>
                            <div className="card-body">
                                <div className="asset-grid">
                                    {/* <!-- Cash Reserves --> */}
                                    <div className="card asset-card">
                                        <div className="card-body">
                                            <h5 className="card-title">Cash Reserves</h5>
                                            <p className="card-text">Company Liquid Cash Funds</p>
                                            <span className="badge bg-info">Available: $500,000</span>
                                            <div className="asset-actions">
                                                <a href="liquid-asset-details.html?id=cash-reserves" className="btn btn-primary btn-sm">
                                                    <i className="bi bi-eye me-2"></i>View Details
                                                </a>
                                                <button className="btn btn-outline-success btn-sm" data-bs-toggle="modal" data-bs-target="#liquidAssetRequestModal" data-asset-type="Cash Reserves">
                                                    <i className="bi bi-plus-circle me-2"></i>Request Asset
                                                </button>
                                            </div>
                                        </div>
                                    </div>

                                    {/* <!-- Short-Term Investments --> */}
                                    <div className="card asset-card">
                                        <div className="card-body">
                                            <h5 className="card-title">Short-Term Investments</h5>
                                            <p className="card-text">Marketable Securities</p>
                                            <span className="badge bg-info">Available: $250,000</span>
                                            <div className="asset-actions">
                                                <a href="liquid-asset-details.html?id=short-term-investments" className="btn btn-primary btn-sm">
                                                    <i className="bi bi-eye me-2"></i>View Details
                                                </a>
                                                <button className="btn btn-outline-success btn-sm" data-bs-toggle="modal" data-bs-target="#liquidAssetRequestModal" data-asset-type="Short-Term Investments">
                                                    <i className="bi bi-plus-circle me-2"></i>Request Asset
                                                </button>
                                            </div>
                                        </div>
                                    </div>

                                    {/* <!-- Money Market Funds --> */}
                                    <div className="card asset-card">
                                        <div className="card-body">
                                            <h5 className="card-title">Money Market Funds</h5>
                                            <p className="card-text">High Liquidity Funds</p>
                                            <span className="badge bg-info">Available: $150,000</span>
                                            <div className="asset-actions">
                                                <a href="liquid-asset-details.html?id=money-market-funds" className="btn btn-primary btn-sm">
                                                    <i className="bi bi-eye me-2"></i>View Details
                                                </a>
                                                <button className="btn btn-outline-success btn-sm" data-bs-toggle="modal" data-bs-target="#liquidAssetRequestModal" data-asset-type="Money Market Funds">
                                                    <i className="bi bi-plus-circle me-2"></i>Request Asset
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            {/* <!-- Liquid Asset Request Modal (unchanged from previous version) --> */}
            <div className="modal fade" id="liquidAssetRequestModal" tabindex="-1">
                <div className="modal-dialog">
                    <div className="modal-content">
                        <div className="modal-header">
                            <h5 className="modal-title">Liquid Asset Request</h5>
                            <button type="button" className="btn-close" data-bs-dismiss="modal"></button>
                        </div>
                        <div className="modal-body">
                            <form id="liquidAssetRequestForm">
                                <div className="mb-3">
                                    <label className="form-label">Asset Type</label>
                                    <select className="form-select" id="assetTypeSelect" required>
                                        <option value="">Select Liquid Asset Type</option>
                                        <option>Cash Reserves</option>
                                        <option>Short-Term Investments</option>
                                        <option>Money Market Funds</option>
                                    </select>
                                </div>
                                <div className="mb-3">
                                    <label className="form-label">Amount</label>
                                    <input type="number" className="form-control" placeholder="Enter amount" required/>
                                </div>
                                <div className="mb-3">
                                    <label className="form-label">Purpose</label>
                                    <textarea className="form-control" rows="3" placeholder="Explain the purpose of the liquid asset request" required></textarea>
                                </div>
                                <button type="submit" className="btn btn-primary">Submit Request</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default LiquidAsset;