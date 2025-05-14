import './LiquidAsset.css';

function LiquidAssetRequest() {
    return (
        <div>
            <div className="container-fluid">
                <div className="row">
                    {/* Sidebar */}
                    <div className="col-md-2 sidebar">
                        <div className="text-center text-white mb-4">
                            <h3>HexaAssets</h3>
                        </div>
                        <ul className="nav flex-column">
                            <li className="nav-item">
                                <a className="nav-link" href="manager">
                                    <i className="bi bi-speedometer2 me-2"></i>Dashboard
                                </a>
                            </li>
                            <li className="nav-item">
                                <a className="nav-link" href="/dashassetpage">
                                    <i className="bi bi-cash-stack me-2"></i>Liquid Assets
                                </a>
                            </li>
                            <li className="nav-item">
                                <a className="nav-link active" href="/dashassetreq">
                                    <i className="bi bi-plus-circle me-2"></i>Liquid Asset Request
                                </a>
                            </li>
                            <li className="nav-item">
                                <a className="nav-link" href="/dashassetall">
                                    <i className="bi bi-clipboard-check me-2"></i>Liquid Asset Allocation
                                </a>
                            </li>
                            <li className="nav-item">
                                <a className="nav-link" href="/dashassetunall">
                                    <i className="bi bi-archive me-2"></i>Unallocated Liquid Assets
                                </a>
                            </li>
                        </ul>
                    </div>

                    <div className="col-md-10 p-4">
                        <div className="card">
                            <div className="card-header">
                                <span>Liquid Asset Requests</span>
                                <form className="d-flex" style={{ maxWidth: "300px" }} method="get">
                                    <input type="text" className="form-control form-control-sm" name="search"
                                        placeholder="Search requests..." />
                                    <button type="submit" className="btn btn-sm btn-outline-light">
                                        <i className="bi bi-search"></i>
                                    </button>
                                </form>
                            </div>
                            <div className="card-body">
                                <div className="table-responsive">
                                    <table className="table table-striped">
                                        <thead>
                                            <tr>
                                                <th>Employee</th>
                                                <th>Asset Type</th>
                                                <th>Amount</th>
                                                <th>Purpose</th>
                                                <th>Status</th>
                                                <th>Actions</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td>John Doe</td>
                                                <td>Cash Reserves</td>
                                                <td>$50,000</td>
                                                <td>Project Funding</td>
                                                <td><span className="badge bg-warning">Pending</span></td>
                                                <td className="action-buttons">
                                                    <form method="post" style={{ display: 'inline' }}>
                                                        <input type="hidden" name="employee" value="John Doe" />
                                                        <input type="hidden" name="action" value="approve" />
                                                        <button type="submit"
                                                            className="btn btn-sm btn-success me-1">Approve</button>
                                                    </form>
                                                    <form method="post" style={{ display: 'inline' }}>
                                                        <input type="hidden" name="employee" value="John Doe" />
                                                        <input type="hidden" name="action" value="reject" />
                                                        <button type="submit" className="btn btn-sm btn-danger me-1">Reject</button>
                                                    </form>
                                                    <a href="#viewAssets-JohnDoe" className="btn btn-sm btn-info me-1">
                                                        <i className="bi bi-eye"></i>
                                                    </a>
                                                    <a href="#editRequest-JohnDoe" className="btn btn-sm btn-warning me-1">
                                                        <i className="bi bi-pencil"></i>
                                                    </a>
                                                    <a href="#deleteConfirm-JohnDoe" className="btn btn-sm btn-danger">
                                                        <i className="bi bi-trash"></i>
                                                    </a>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>Jane Smith</td>
                                                <td>Money Market Funds</td>
                                                <td>$25,000</td>
                                                <td>Maintainance Budget</td>
                                                <td><span className="badge bg-warning">Pending</span></td>
                                                <td className="action-buttons">
                                                    <form method="post" style={{ display: 'inline' }}>
                                                        <input type="hidden" name="employee" value="Jane Smith" />
                                                        <input type="hidden" name="action" value="approve" />
                                                        <button type="submit"
                                                            className="btn btn-sm btn-success me-1">Approve</button>
                                                    </form>
                                                    <form method="post" style={{ display: 'inline' }}>
                                                        <input type="hidden" name="employee" value="Jane Smith" />
                                                        <input type="hidden" name="action" value="reject" />
                                                        <button type="submit" className="btn btn-sm btn-danger me-1">Reject</button>
                                                    </form>
                                                    <a href="#viewAssets-JaneSmith" className="btn btn-sm btn-info me-1">
                                                        <i className="bi bi-eye"></i>
                                                    </a>
                                                    <a href="#editRequest-JaneSmith" className="btn btn-sm btn-warning me-1">
                                                        <i className="bi bi-pencil"></i>
                                                    </a>
                                                    <a href="#deleteConfirm-JaneSmith" className="btn btn-sm btn-danger">
                                                        <i className="bi bi-trash"></i>
                                                    </a>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>Mike Johnson</td>
                                                <td>Charges</td>
                                                <td>$100,000</td>
                                                <td>Flight Charges</td>
                                                <td><span className="badge bg-success">Approved</span></td>
                                                <td className="action-buttons">
                                                    <a href="#viewAssets-MikeJohnson" className="btn btn-sm btn-info me-1">
                                                        <i className="bi bi-eye"></i>
                                                    </a>
                                                    <a href="#editRequest-MikeJohnson" className="btn btn-sm btn-warning me-1">
                                                        <i className="bi bi-pencil"></i>
                                                    </a>
                                                    <a href="#deleteConfirm-MikeJohnson" className="btn btn-sm btn-danger">
                                                        <i className="bi bi-trash"></i>
                                                    </a>
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            {/* Modal for viewing John Doe's assets */}
            <div className="modal" id="viewAssets-JohnDoe">
                <div className="modal-dialog modal-lg">
                    <div className="modal-content">
                        <div className="modal-header bg-secondary text-white">
                            <h5 className="modal-title">Employee Assets</h5>
                            <a href="#" className="close-modal">&times;</a>
                        </div>
                        <div className="modal-body">
                            <h6 className="mb-3">Employee: <span>John Doe</span></h6>
                            <table className="table table-striped">
                                <thead>
                                    <tr>
                                        <th>Asset ID</th>
                                        <th>Asset Type</th>
                                        <th>Amount</th>
                                        <th>Allocation Date</th>
                                        <th>Purpose</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td>LA001</td>
                                        <td>Cash Reserves</td>
                                        <td>$30,000</td>
                                        <td>2025-01-15</td>
                                        <td>Marketing Campaign</td>
                                    </tr>
                                    <tr>
                                        <td>LA002</td>
                                        <td>Short-term Securities</td>
                                        <td>$45,000</td>
                                        <td>2025-02-20</td>
                                        <td>Equipment Purchase</td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                        <div className="modal-footer">
                            <a href="#" className="btn btn-secondary">Close</a>
                        </div>
                    </div>
                </div>
            </div>

            {/* Modal for viewing Jane Smith's assets */}
            <div className="modal" id="viewAssets-JaneSmith">
                <div className="modal-dialog modal-lg">
                    <div className="modal-content">
                        <div className="modal-header bg-secondary text-white">
                            <h5 className="modal-title">Employee Assets</h5>
                            <a href="#" className="close-modal">&times;</a>
                        </div>
                        <div className="modal-body">
                            <h6 className="mb-3">Employee: <span>Jane Smith</span></h6>
                            <table className="table table-striped">
                                <thead>
                                    <tr>
                                        <th>Asset ID</th>
                                        <th>Asset Type</th>
                                        <th>Amount</th>
                                        <th>Allocation Date</th>
                                        <th>Purpose</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td>LA003</td>
                                        <td>Treasury Bills</td>
                                        <td>$20,000</td>
                                        <td>2025-01-10</td>
                                        <td>Office Renovation</td>
                                    </tr>
                                    <tr>
                                        <td>LA004</td>
                                        <td>Money Market Funds</td>
                                        <td>$15,000</td>
                                        <td>2025-03-05</td>
                                        <td>Training Program</td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                        <div className="modal-footer">
                            <a href="#" className="btn btn-secondary">Close</a>
                        </div>
                    </div>
                </div>
            </div>

            {/* Modal for viewing Mike Johnson's assets */}
            <div className="modal" id="viewAssets-MikeJohnson">
                <div className="modal-dialog modal-lg">
                    <div className="modal-content">
                        <div className="modal-header bg-secondary text-white">
                            <h5 className="modal-title">Employee Assets</h5>
                            <a href="#" className="close-modal">&times;</a>
                        </div>
                        <div className="modal-body">
                            <h6 className="mb-3">Employee: <span>Mike Johnson</span></h6>
                            <table className="table table-striped">
                                <thead>
                                    <tr>
                                        <th>Asset ID</th>
                                        <th>Asset Type</th>
                                        <th>Amount</th>
                                        <th>Allocation Date</th>
                                        <th>Purpose</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td>LA005</td>
                                        <td>Charges</td>
                                        <td>$100,000</td>
                                        <td>2025-04-01</td>
                                        <td>Flight Charges</td>
                                    </tr>
                                    <tr>
                                        <td>LA006</td>
                                        <td>Cash Reserves</td>
                                        <td>$75,000</td>
                                        <td>2025-02-28</td>
                                        <td>Conference Expenses</td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                        <div className="modal-footer">
                            <a href="#" className="btn btn-secondary">Close</a>
                        </div>
                    </div>
                </div>
            </div>

            {/* Delete Confirmation Modal for John Doe */}
            <div className="modal" id="deleteConfirm-JohnDoe">
                <div className="modal-dialog">
                    <div className="modal-content">
                        <div className="modal-header bg-danger text-white">
                            <h5 className="modal-title">Confirm Delete</h5>
                            <a href="#" className="close-modal">&times;</a>
                        </div>
                        <div className="modal-body">
                            <p>Are you sure you want to delete this request for <strong>John Doe</strong>?</p>
                            <p>This action cannot be undone.</p>
                        </div>
                        <div className="modal-footer">
                            <a href="#" className="btn btn-secondary">Cancel</a>
                            <form method="post" style={{ display: 'inline' }}>
                                <input type="hidden" name="employee" value="John Doe" />
                                <input type="hidden" name="action" value="delete" />
                                <button type="submit" className="btn btn-danger">Delete Request</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>

            {/* Delete Confirmation Modal for Jane Smith */}
            <div className="modal" id="deleteConfirm-JaneSmith">
                <div className="modal-dialog">
                    <div className="modal-content">
                        <div className="modal-header bg-danger text-white">
                            <h5 className="modal-title">Confirm Delete</h5>
                            <a href="#" className="close-modal">&times;</a>
                        </div>
                        <div className="modal-body">
                            <p>Are you sure you want to delete this request for <strong>Jane Smith</strong>?</p>
                            <p>This action cannot be undone.</p>
                        </div>
                        <div className="modal-footer">
                            <a href="#" className="btn btn-secondary">Cancel</a>
                            <form method="post" style={{ display: 'inline' }}>
                                <input type="hidden" name="employee" value="Jane Smith" />
                                <input type="hidden" name="action" value="delete" />
                                <button type="submit" className="btn btn-danger">Delete Request</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>

            {/* Delete Confirmation Modal for Mike Johnson */}
            <div className="modal" id="deleteConfirm-MikeJohnson">
                <div className="modal-dialog">
                    <div className="modal-content">
                        <div className="modal-header bg-danger text-white">
                            <h5 className="modal-title">Confirm Delete</h5>
                            <a href="#" className="close-modal">&times;</a>
                        </div>
                        <div className="modal-body">
                            <p>Are you sure you want to delete this request for <strong>Mike Johnson</strong>?</p>
                            <p>This action cannot be undone.</p>
                        </div>
                        <div className="modal-footer">
                            <a href="#" className="btn btn-secondary">Cancel</a>
                            <form method="post" style={{ display: 'inline' }}>
                                <input type="hidden" name="employee" value="Mike Johnson" />
                                <input type="hidden" name="action" value="delete" />
                                <button type="submit" className="btn btn-danger">Delete Request</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>

            {/* Edit Request Modal for John Doe */}
            <div className="modal" id="editRequest-JohnDoe">
                <div className="modal-dialog">
                    <div className="modal-content">
                        <div className="modal-header bg-warning text-dark">
                            <h5 className="modal-title">Edit Request</h5>
                            <a href="#" className="close-modal">&times;</a>
                        </div>
                        <div className="modal-body">
                            <form method="post" action="">
                                <div className="mb-3">
                                    <label htmlFor="editEmployee" className="form-label">Employee</label>
                                    <input type="text" className="form-control" id="editEmployee" name="employee" value="John Doe"
                                        readOnly />
                                </div>
                                <div className="mb-3">
                                    <label htmlFor="editAssetType" className="form-label">Asset Type</label>
                                    <select className="form-select" id="editAssetType" name="assetType" defaultValue="Cash Reserves">
                                        <option value="Cash Reserves">Cash Reserves</option>
                                        <option value="Money Market Funds">Money Market Funds</option>
                                        <option value="Treasury Bills">Treasury Bills</option>
                                        <option value="Short-term Securities">Short-term Securities</option>
                                        <option value="Charges">Charges</option>
                                    </select>
                                </div>
                                <div className="mb-3">
                                    <label htmlFor="editAmount" className="form-label">Amount</label>
                                    <div className="input-group">
                                        <span className="input-group-text">$</span>
                                        <input type="text" className="form-control" id="editAmount" name="amount" defaultValue="50000" />
                                    </div>
                                </div>
                                <div className="mb-3">
                                    <label htmlFor="editPurpose" className="form-label">Purpose</label>
                                    <input type="text" className="form-control" id="editPurpose" name="purpose"
                                        defaultValue="Project Funding" />
                                </div>
                                <div className="modal-footer">
                                    <a href="#" className="btn btn-secondary">Cancel</a>
                                    <button type="submit" name="action" value="update" className="btn btn-primary">Save
                                        Changes</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>

            {/* Edit Request Modal for Jane Smith */}
            <div className="modal" id="editRequest-JaneSmith">
                <div className="modal-dialog">
                    <div className="modal-content">
                        <div className="modal-header bg-warning text-dark">
                            <h5 className="modal-title">Edit Request</h5>
                            <a href="#" className="close-modal">&times;</a>
                        </div>
                        <div className="modal-body">
                            <form method="post" action="">
                                <div className="mb-3">
                                    <label htmlFor="editEmployee2" className="form-label">Employee</label>
                                    <input type="text" className="form-control" id="editEmployee2" name="employee"
                                        value="Jane Smith" readOnly />
                                </div>
                                <div className="mb-3">
                                    <label htmlFor="editAssetType2" className="form-label">Asset Type</label>
                                    <select className="form-select" id="editAssetType2" name="assetType" defaultValue="Money Market Funds">
                                        <option value="Cash Reserves">Cash Reserves</option>
                                        <option value="Money Market Funds">Money Market Funds</option>
                                        <option value="Treasury Bills">Treasury Bills</option>
                                        <option value="Short-term Securities">Short-term Securities</option>
                                        <option value="Charges">Charges</option>
                                    </select>
                                </div>
                                <div className="mb-3">
                                    <label htmlFor="editAmount2" className="form-label">Amount</label>
                                    <div className="input-group">
                                        <span className="input-group-text">$</span>
                                        <input type="text" className="form-control" id="editAmount2" name="amount" defaultValue="25000" />
                                    </div>
                                </div>
                                <div className="mb-3">
                                    <label htmlFor="editPurpose2" className="form-label">Purpose</label>
                                    <input type="text" className="form-control" id="editPurpose2" name="purpose"
                                        defaultValue="Maintainance Budget" />
                                </div>
                                <div className="modal-footer">
                                    <a href="#" className="btn btn-secondary">Cancel</a>
                                    <button type="submit" name="action" value="update" className="btn btn-primary">Save
                                        Changes</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>

            {/* Edit Request Modal for Mike Johnson */}
            <div className="modal" id="editRequest-MikeJohnson">
                <div className="modal-dialog">
                    <div className="modal-content">
                        <div className="modal-header bg-warning text-dark">
                            <h5 className="modal-title">Edit Request</h5>
                            <a href="#" className="close-modal">&times;</a>
                        </div>
                        <div className="modal-body">
                            <form method="post" action="">
                                <div className="mb-3">
                                    <label htmlFor="editEmployee3" className="form-label">Employee</label>
                                    <input type="text" className="form-control" id="editEmployee3" name="employee"
                                        value="Mike Johnson" readOnly />
                                </div>
                                <div className="mb-3">
                                    <label htmlFor="editAssetType3" className="form-label">Asset Type</label>
                                    <select className="form-select" id="editAssetType3" name="assetType" defaultValue="Charges">
                                        <option value="Cash Reserves">Cash Reserves</option>
                                        <option value="Money Market Funds">Money Market Funds</option>
                                        <option value="Treasury Bills">Treasury Bills</option>
                                        <option value="Short-term Securities">Short-term Securities</option>
                                        <option value="Charges">Charges</option>
                                    </select>
                                </div>
                                <div className="mb-3">
                                    <label htmlFor="editAmount3" className="form-label">Amount</label>
                                    <div className="input-group">
                                        <span className="input-group-text">$</span>
                                        <input type="text" className="form-control" id="editAmount3" name="amount" defaultValue="100000" />
                                    </div>
                                </div>
                                <div className="mb-3">
                                    <label htmlFor="editPurpose3" className="form-label">Purpose</label>
                                    <input type="text" className="form-control" id="editPurpose3" name="purpose"
                                        defaultValue="Flight Charges" />
                                </div>
                                <div className="modal-footer">
                                    <a href="#" className="btn btn-secondary">Cancel</a>
                                    <button type="submit" name="action" value="update" className="btn btn-primary">Save
                                        Changes</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default LiquidAssetRequest;